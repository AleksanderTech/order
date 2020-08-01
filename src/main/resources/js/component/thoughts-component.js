import { ThoughtsMetrics } from '../model/thoughts-metrics.js';
import { TileListComponent } from './tile-list-component.js';
import { ROUTER_INSTANCE } from '../router.js';
import { EVENTS, TAG_SELECTED, THOUGHT_CREATED } from '../events.js'
import { Thought } from '../model/thought.js';
import { INFORMATION } from '../component/information-component.js';

export class ThoughtsComponent {
    pos1 = 0;
    pos2 = 0;
    pos3 = 0;
    pos4 = 0;
    width = 580;
    height = 450;
    layoutChangeMode = false;


    constructor(componentId, thoughtsMetricsManager, thoughtsService, tagService) {
        this.currentTagId = null;
        this.thoughtsMetricsManager = thoughtsMetricsManager;
        this.thoughtsService = thoughtsService;
        this.tagService = tagService;
        this.thoughtsElement = document.getElementById(componentId);
        this.tiles = document.querySelectorAll('.tile');
        this.thoughtsGrid = document.getElementById('thoughts-grid');
        this.thoughtsForm = document.getElementById('thoughts-form');
        this.newThoughtButton = document.getElementById('new-thought-button');
        this.newTagButton = document.getElementById('new-tag-button');
        this.thoughtCreationModal = document.getElementById('thought-creation-modal');
        this.tagCreationModal = document.getElementById('tag-creation-modal');
        this.editorModal = document.getElementById('editor-modal');
        this.tagCloseModal = document.getElementById('tag-close-modal');
        this.thoughtCloseModal = document.getElementById('thought-close-modal');
        this.resizeGrid = document.getElementById('resize-grid');
        this.tagsForm = document.getElementById('tags-form');
        this.thoughtsWrapper = document.getElementById('thoughts-wrapper');
        this.thoughtsGrid = document.getElementById('thoughts-grid');
        this.managementList = document.getElementById('management-list');
        this.managementLabel = document.getElementById('management-label');
        this.changeLayoutItem = document.getElementById('change-layout');
        this.dragGridHandle = document.querySelector('.drag-grid');
        this.tileListComponent = new TileListComponent(this.thoughtsGrid, [], this.thoughtsService, this.tagService);
        this.init();
    }

    init() {
        this.registerHandlers();
        this.setupGridPosition();
        this.tileListComponent.fetchTags();
        window.onpopstate = () => {
            this.cleanAfterChangeLayout();
            const route = ROUTER_INSTANCE.getRoute(window.location.pathname);
            this.thoughtsGrid.innerHTML = route.content;
            route.callback();
            if (window.location.pathname === '/thoughts') {
                document.querySelector('#new-thought').style.display = 'none';
                document.querySelector('#new-tag').style.display = 'block';
            } else {
                document.querySelector('#new-thought').style.display = 'block';
                document.querySelector('#new-tag').style.display = 'none';

            }
        }
        EVENTS.subscribe(this);
        if (window.location.pathname === '/thoughts') {
            this.thoughtsElement.querySelector('#new-thought').style.display = 'none';
        }
    }

    update(eventType, data) {
        if (eventType === TAG_SELECTED) {
            this.cleanAfterChangeLayout();
            this.currentTagId = data
        } else {
            document.querySelector('.drag-grid').addEventListener('mousedown', this.dragMouseDown.bind(this));
        }
    }

    registerHandlers() {
        this.managementLabel.addEventListener('click', this.toggleList.bind(this));
        this.managementList.addEventListener('mouseleave', this.closeList.bind(this));
        this.changeLayoutItem.addEventListener('click', this.changeLayout.bind(this));
        this.thoughtsGrid.addEventListener('dragover', e => {
            e.preventDefault();
        });
        this.newThoughtButton.addEventListener('click', e => {
            this.thoughtCreationModal.style.display = 'block';
        });
        this.thoughtsElement.querySelector('#create-thought-button').addEventListener('click', (e) => {
            this.createThought(document.getElementById('create-thought-input').value);
        });
        this.newTagButton.addEventListener('click', e => {
            this.tagCreationModal.style.display = 'block';
        });
        this.thoughtCloseModal.addEventListener('click', e => {
            this.thoughtCreationModal.style.display = 'none';
        });
        this.tagCloseModal.addEventListener('click', e => {
            this.tagCreationModal.style.display = 'none';
        });
    }

    createThought(thoughtName) {
        const thought = new Thought(null, thoughtName, null, this.currentTagId);
        this.thoughtsService.save(thought)
            .then(() => {
                INFORMATION.show('The Thought has been created');
                this.thoughtsService.findByTagId(this.currentTagId)
                    .then(thoughts => {
                        this.tileListComponent.drawBareThoughts(thoughts);
                        this.tileListComponent.registerHandlers(thoughts);
                        EVENTS.emit(THOUGHT_CREATED);
                    }).catch(err => {
                        console.log(err);
                    })

                this.thoughtCreationModal.style.display = 'none';
            })
            .catch(err => {
                this.thoughtCreationModal.style.display = 'none';
                console.log(err);
            });
    }

    setupGridPosition() {
        this.thoughtsMetricsManager.fetchUserPosition()
            .then(res => {
                let metrics = res;
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
        let dragGrid = document.querySelector('.drag-grid');
        if (dragGrid) {
            document.querySelector('.drag-grid').classList.add('display-block');
        }
    }

    hideDragGridHandle() {
        let dragGrid = document.querySelector('.drag-grid');
        if (dragGrid) {
            dragGrid.classList.remove('display-block');
        }
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
        this.cleanAfterChangeLayout();
    }

    cleanAfterChangeLayout() {
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
}
