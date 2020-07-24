import { ThoughtsMetrics } from '../model/thoughts-metrics.js';
import { buildUrl } from '../url-provider.js';

class Router {
    constructor() {
        this.routes = [];
    }

    add(uri, data, content) {
        this.routes.forEach(route => {
            if (route.uri === uri) {
                // throw new Error(`the uri ${route.uri} already exists`);
                return;
            }
        });
        const route = {
            uri,
            data,
            content
        }
        this.routes.push(route);
    }

    getRoute(uri) {
        let routeResult = {};
        this.routes.forEach(route => {
            if (route.uri === uri) {
                routeResult = route;
            }
        });
        return routeResult;
    }
}

export class ThoughtsComponent {
    route = new Router();
    pos1 = 0;
    pos2 = 0;
    pos3 = 0;
    pos4 = 0;
    width = 580;
    height = 450;
    layoutChangeMode = false;
    isHovered = 0;

    constructor(componentId, thoughtsMetricsManager) {
        this.currentTagId = null;
        this.thoughtsMetricsManager = thoughtsMetricsManager;
        this.thoughtsElement = document.getElementById(componentId);
        this.tiles = document.querySelectorAll('.tile');
        this.thoughtsGrid = document.getElementById('thoughts-grid');
        this.newThoughtButton = document.getElementById('new-thought-button');
        this.newTagButton = document.getElementById('new-tag-button');
        this.creationModal = document.getElementById('creation-modal');
        this.closeModal = document.getElementById('close-modal');
        this.resizeGrid = document.getElementById('resize-grid');
        this.tagsForm = document.getElementById('tags-form');
        this.thoughtsForm = document.getElementById('thoughts-form');
        this.thoughtsWrapper = document.getElementById('thoughts-wrapper');
        this.thoughtsGrid = document.getElementById('thoughts-grid');
        this.managementList = document.getElementById('management-list');
        this.managementLabel = document.getElementById('management-label');
        this.changeLayoutItem = document.getElementById('change-layout');
        this.dragGridHandle = document.querySelector('.drag-grid');
    }

    init() {
        this.registerHandlers();
        this.fetchTags();
        this.setupGridPosition();
        window.onpopstate = () => {
            this.thoughtsGrid.innerHTML = this.route.getRoute(window.location.pathname).content;
            this.addTagHandlers(this.route.getRoute(window.location.pathname).data);
            document.querySelector('.drag-grid').addEventListener('mousedown', this.dragMouseDown.bind(this));
        }
    }

    navigate(pathName, contentWithData) {
        window.history.pushState(
            {},
            pathName,
            window.location.origin + pathName
        );
        this.route.add(pathName, contentWithData.data, contentWithData.content);
        this.thoughtsGrid.innerHTML = this.route.getRoute(pathName).content;
    }

    registerHandlers() {
        this.managementLabel.addEventListener('click', this.toggleList.bind(this));
        this.managementList.addEventListener('mouseleave', this.closeList.bind(this));
        this.changeLayoutItem.addEventListener('click', this.changeLayout.bind(this));
        this.thoughtsGrid.addEventListener('dragover', e => {
            e.preventDefault();
        });
        this.newThoughtButton.addEventListener('click', e => {
            this.creationModal.style.display = 'block';
            this.thoughtsForm.style.display = 'block';
        })

        this.newTagButton.addEventListener('click', e => {
            this.creationModal.style.display = 'block';
            this.tagsForm.style.display = 'block';
        })

        this.closeModal.addEventListener('click', e => {
            this.thoughtsForm.style.display = 'none';
            this.tagsForm.style.display = 'none';
            this.creationModal.style.display = 'none';
        })
    }

    setupGridPosition() {
        this.thoughtsMetricsManager.fetchUserPosition()
            .then(res => {
                let metrics = JSON.parse(res);
                this.pos1 = metrics.leftPosition;
                this.pos2 = metrics.topPosition;
                this.pos3 = metrics.rightPosition;
                this.pos4 = metrics.bottomPosition;
                this.width = metrics.width;
                this.height = metrics.height;
                this.setGridCssMetrics(metrics);
            }).catch(err => {
                this.thoughtsGrid.style.margin = '0 auto';
            });
    }

    setGridCssMetrics(metrics) {
        document.documentElement.style.setProperty('--thoughts-grid-width', metrics.width + 'px');
        document.documentElement.style.setProperty('--thoughts-grid-height', metrics.height + 'px');
        document.documentElement.style.setProperty('--thoughts-grid-left', metrics.leftPosition + 'px');
        document.documentElement.style.setProperty('--thoughts-grid-top', metrics.topPosition + 'px');
        document.documentElement.style.setProperty('--thoughts-grid-right', metrics.rightPosition + 'px');
        document.documentElement.style.setProperty('--thoughts-grid-bottom', metrics.bottomPosition + 'px');
    }

    showDragGridHandle() {
        document.querySelector('.drag-grid').classList.add('display-block');
    }

    hideDragGridHandle() {
        document.querySelector('.drag-grid').classList.remove('display-block');
    }

    changeLayout() {
        if (!this.layoutChangeMode) {
            this.addBorder();
            this.showResizeHandle(this.thoughtsGrid);
            this.showDragGridHandle();
            this.absolute(this.thoughtsGrid);
            this.changeLayoutItem.innerText = 'Save layout';
            this.layoutChangeMode = !this.layoutChangeMode;
        } else {
            this.saveLayout();
        }
    }

    absolute(element) {
        element.style.position = 'absolute';
    }

    relative(element) {
        element.style.position = 'relative';
    }

    saveLayout() {
        if (this.isOutOfBounds(this.thoughtsGrid, this.thoughtsGrid.parentElement)) {
            return;
        }
        this.hideResizeHandle(this.thoughtsGrid);
        let thoughtsMetrics = new ThoughtsMetrics();
        let box = this.thoughtsGrid.getBoundingClientRect();
        thoughtsMetrics.leftPosition = box.left;
        thoughtsMetrics.topPosition = this.thoughtsGrid.offsetTop;
        thoughtsMetrics.rightPosition = this.thoughtsGrid.offsetLeft;
        thoughtsMetrics.bottomPosition = box.bottom;
        thoughtsMetrics.width = box.width;
        thoughtsMetrics.height = box.height;
        this.thoughtsMetricsManager.savePosition(thoughtsMetrics)
        this.removeBorder();
        this.hideResizeHandle(this.thoughtsGrid);
        this.hideDragGridHandle()
        this.relative(this.thoughtsGrid);
        this.changeLayoutItem.innerText = 'Change layout';
        this.layoutChangeMode = !this.layoutChangeMode;
    }

    showResizeHandle(element) {
        element.style.resize = 'both';
    }

    hideResizeHandle(element) {
        element.style.resize = 'none';
    }

    toggleList() {
        if (this.isOpened) {
            this.closeList();
        } else {
            this.managementList.classList.add('show-management');
            this.managementList.classList.remove('hide-management');
            this.isOpened = true;
        }
    }

    closeList() {
        this.managementList.classList.add('hide-management');
        this.managementList.classList.remove('show-management');
        this.isOpened = false;
    }

    dragElements() {
        this.dragElement(this.thoughtsGrid);
    }

    swap(container, dragging, hoverElement) {
        const afterDragging = dragging.nextElementSibling;
        container.insertBefore(dragging, hoverElement);
        if (hoverElement != afterDragging) {
            container.insertBefore(hoverElement, afterDragging);
        } else {
            container.insertBefore(hoverElement, dragging);
        }
    }

    setCurrentTagId(tagId) {
        this.currentTagId = tagId;
    }

    addBorder() {
        this.thoughtsWrapper.classList.add('dashed-border');
        this.thoughtsGrid.classList.add('dashed-border');
    }

    removeBorder() {
        this.thoughtsWrapper.classList.remove('dashed-border');
        this.thoughtsGrid.classList.remove('dashed-border');
    }

    outOfBounds() {
        this.thoughtsWrapper.classList.remove('dashed-border');
        this.thoughtsWrapper.classList.add('alert-border');
    }

    inBounds() {
        this.thoughtsWrapper.classList.remove('alert-border');
        this.thoughtsWrapper.classList.add('dashed-border');
    }

    isOutOfBounds(element, parent) {
        let elementBox = element.getBoundingClientRect();
        let parentBox = parent.getBoundingClientRect();
        let topElem = elementBox.top;
        let leftElem = elementBox.left;
        let rightElem = elementBox.right;
        let topPar = parentBox.top;
        let leftPar = parentBox.left;
        let rightPar = parentBox.right;
        return topElem < topPar || leftElem < leftPar || rightElem > rightPar;
    }

    dragElement(element) {
        this.dragGridHandle.addEventListener('mousedown', this.dragMouseDown.bind(this));
    }

    dragMouseDown(e) {
        this.pos3 = e.clientX;
        this.pos4 = e.clientY;
        document.onmouseup = this.closeDragElement.bind(this);
        document.onmousemove = this.elementDrag.bind(this);
    }

    elementDrag(e) {
        if (this.isOutOfBounds(this.thoughtsGrid, this.thoughtsGrid.parentElement)) {
            this.outOfBounds();
        } else {
            this.inBounds();
        }
        this.pos1 = this.pos3 - e.clientX;
        this.pos2 = this.pos4 - e.clientY;
        this.pos3 = e.clientX;
        this.pos4 = e.clientY;
        this.thoughtsGrid.style.top = (this.thoughtsGrid.offsetTop - this.pos2) + "px";
        this.thoughtsGrid.style.left = (this.thoughtsGrid.offsetLeft - this.pos1) + "px";
    }

    closeDragElement() {
        document.onmouseup = null;
        document.onmousemove = null;
    }
    // ---------------------------------rest----x------------------------
    fetchThoughts(tagId) {
        fetch(buildUrl('thought?tag-id=' + tagId))
            .then(res => res.json())
            .then(thoughts => {
                thoughts = JSON.parse(thoughts);
                if (thoughts) {
                    this.navigate('/thoughts/tag', { content: this.drawThoughts(thoughts), data: thoughts });
                    this.addTagHandlers(thoughts);
                    document.querySelector('.drag-grid').addEventListener('mousedown', this.dragMouseDown.bind(this));
                }
            }).catch(err => {
                console.log(err);
                this.noThoughts()
            })
    }

    goToTag(tag) {
        this.fetchThoughts(tag.id);
    }

    noThoughts() {
        this.thoughtsWrapper.innerHTML =
            `<h2 class="text-center">There is no any thoughts</h2>`;
    }

    fetchTags() {
        fetch(buildUrl('tag'))
            .then(res => res.json())
            .then(tags => {
                this.navigate(window.location.pathname, { content: this.drawTags(tags), data: tags });
                this.addTagHandlers(tags);
                document.querySelector('.drag-grid').addEventListener('mousedown', this.dragMouseDown.bind(this));
            });
    }

    drawTags(tags) {
        let tagsPage = '';
        for (let tag of tags) {
            let maxNameLength = 12;
            if (tag.name.length > maxNameLength) {
                tag.name = tag.name.substring(0, maxNameLength) + '...';
            }
            tagsPage = tagsPage +
                `<div class="tile" draggable="true">
                <div><div class="tile-name">${tag.name}</div></div>
                <div class="tile-img-wrapper">
                    <div class="tile-img tag-img"></div>
                </div>
                <div class="bar">
                    <div class="dot-menu">
                        <div class="dot"></div>
                        <div class="dot"></div>
                        <div class="dot"></div>
                    </div>
                    <div class="drag-icon draggable"></div>
                </div>
                </div>`;
        }
        return tagsPage + '<div class="drag-grid"></div>';
    }

    addTagHandlers(tags) {
        document.querySelectorAll('.tile').forEach(tagDiv => {
            tagDiv.addEventListener('dragstart', e => {
                tagDiv.classList.add('dragging');
            })
            tagDiv.addEventListener('dragend', e => {
                tagDiv.classList.remove('dragging');
                tagDiv.classList.remove('hovered');
            })
            tagDiv.addEventListener('dragleave', e => {
                this.isHovered--;
                if (this.isHovered === 0) {
                    tagDiv.classList.remove('hovered');
                }
            })
            tagDiv.addEventListener('dragenter', e => {
                this.isHovered++;
                tagDiv.classList.add('hovered');
            })
            tagDiv.addEventListener('drop', e => {
                this.isHovered = 0;
                const dragging = document.querySelector('.dragging');
                tagDiv.classList.remove('hovered');
                this.swap(this.thoughtsGrid, dragging, tagDiv);
            })
            tagDiv.addEventListener('click', e => {
                let tag = tags.find(tag => tag.name === tagDiv.querySelector('.tile-name').innerText);
                this.goToTag(tag);
            });
        });
        // this.dragElements();
    }

    drawThoughts(thoughts) {
        let thoughtsPage = '';
        if (thoughts && thoughts.length > 0) {
            for (let thought of thoughts) {
                let maxNameLength = 12;
                if (thought.name.length > maxNameLength) {
                    thought.name = thought.name.substring(0, maxNameLength) + '...';
                }
                thoughtsPage = thoughtsPage +
                    `<div class="tile" draggable="true">
            <div><div class="tile-name">${thought.name}</div></div>
            <div class="tile-img-wrapper">
                <div class="tile-img tag-img"></div>
            </div>
            <div class="bar">
                <div class="dot-menu">
                    <div class="dot"></div>
                    <div class="dot"></div>
                    <div class="dot"></div>
                </div>
                <div class="drag-icon draggable"></div>
            </div>
                </div>`;
            }
            return thoughtsPage + '<div class="drag-grid"></div>';
        }
    }
}
