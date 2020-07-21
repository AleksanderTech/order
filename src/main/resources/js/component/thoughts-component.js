import { element } from "../html-builder.js";

export class ThoughtsComponent {
    pos1 = 0;
    pos2 = 0;
    pos3 = 0;
    pos4 = 0;
    layoutChangeMode = false;
    isHovered = 0;
    ro = new ResizeObserver(entries => {
        for (let entry of entries) {
            entry.target.classList.add('dashed-border');
        }
    });

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
        this.dragMe = document.getElementById('drag-me');
        this.thoughtsWrapper = document.getElementById('thoughts-wrapper');
        this.thoughtsGrid = document.getElementById('thoughts-grid');
        this.managementList = document.getElementById('management-list');
        this.managementLabel = document.getElementById('management-label');
        this.changeLayoutItem = document.getElementById('change-layout');
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
        // this.resizeGrid.addEventListener('change', e => {
        //     // if (checkbox.checked) {
        //     //     resizeObserver.observe(divElem);
        //     // } else {
        //     //     resizeObserver.unobserve(divElem);
        //     // }
        // });
        // this.dragMe.addEventListener('mousedown', e => {
        //     this.pos3 = e.clientX;
        //     this.pos4 = e.clientY;
        //     document.onmouseup = this.closeDragElement;
        //     document.onmousemove = this.elementDrag;
        // });
        // document.getElementById('parent-tag-id-input');
        // document.getElementById('resize-grid').addEventListener('change', e => {
        //     if (document.getElementById('resize-grid').checked) {
        //         this.ro.observe(document.getElementById('thoughts-grid'));
        //         this.addBorder();
        //     } else {
        //         this.ro.unobserve(document.getElementById('thoughts-grid'));
        //         this.removeBorder();
        //     }
        // });
        // document.getElementById('resize-handle').addEventListener('mousedown', e => {

        //     this.addBorder();
        //     console.log('adding');

        //     this.ro.observe(document.getElementById('thoughts-grid'));
        // });
        // document.getElementById('resize-handle').addEventListener('mouseup', e => {

        //     console.log('removing');
        //     this.removeBorder();
        //     this.ro.unobserve(document.getElementById('thoughts-grid'));

        // });
        // document.getElementById('saveBlock').addEventListener('click', e => {
        //     this.save();
        // });
    }

    changeLayout() {
        if (!this.layoutChangeMode) {
            this.addBorder();
            this.showResizeHandle(this.thoughtsGrid);
            this.changeLayoutItem.innerText = 'Save layout';
        } else {
           this.removeBorder();
           this.hideResizeHandle(this.thoughtsGrid);
           this.saveLayout();
           this.changeLayoutItem.innerText = 'Change layout';
        }
        this.layoutChangeMode = !this.layoutChangeMode;

        // let thoughtsMetrics = new ThoughtsMetrics();
        // thoughtsMetrics.leftPosition = 120;
        // thoughtsMetrics.topPosition = 120;
        // thoughtsMetrics.rightPosition = 120;
        // thoughtsMetrics.bottomPosition = 120;
        // thoughtsMetrics.width = 120;
        // thoughtsMetrics.height = 120;
        // console.log(thoughtsMetrics);

        // // thoughtsMetricsManager.savePosition(thoughtsMetrics)
        // thoughtsMetricsManager.fetchUserPosition().then(pos => console.log(pos));
    }
    saveLayout() {
        console.log('save layout success');
        this.hideResizeHandle(this.thoughtsGrid);
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
        element.style.left = this.pos1 + 'px';
        element.style.top = this.pos2 + 'px';

        // this.dragMe.addEventListener('mousedown', this.dragMouseDown);
        // this.dragMe.addEventListener('mousedown', dragMouseDown);
    }

    dragMouseDown(e) {
        // this.addBorder()
        this.pos3 = e.clientX;
        this.pos4 = e.clientY;
        document.onmouseup = this.closeDragElement;
        document.onmousemove = this.elementDrag;
    }

    elementDrag(e) {
        // if (this.isOutOfBounds(this.thoughtsGrid, this.thoughtsGrid.parentElement)) {
        //     this.outOfBounds();
        // } else {
        //     this.inBounds();
        // }
        let thoughtsGrid = document.getElementById('thoughts-grid');
        this.pos1 = this.pos3 - e.clientX;
        this.pos2 = this.pos4 - e.clientY;
        this.pos3 = e.clientX;
        this.pos4 = e.clientY;
        thoughtsGrid.style.top = (thoughtsGrid.offsetTop - this.pos2) + "px";
        thoughtsGrid.style.left = (thoughtsGrid.offsetLeft - this.pos1) + "px";
    }

    closeDragElement() {
        document.onmouseup = null;
        document.onmousemove = null;
        // this.removeBorder();
    }
    // ---------------------------------rest----------------------------
    fetchThoughts() {
        fetch('http://localhost:7000/api/thoughts')
            .then(res => res.json())
            .then(thoughts => {
                this.drawThoughts(thoughts)
            });
    }

    fetchTags() {
        fetch('http://localhost:7000/api/tag')
            .then(res => res.json())
            .then(tags => {
                this.drawTags(tags)
            });
    }
    // class="tile draggable" draggable="true"

    drawTags(tags) {
        for (let tag of tags) {
            let tagDiv = element('div');
            let classAttr = document.createAttribute('class');
            classAttr.value = 'tile';
            tagDiv.setAttributeNode(classAttr);
            let draggableAttr = document.createAttribute('draggable');
            draggableAttr.value = 'true';
            tagDiv.setAttributeNode(draggableAttr);
            let maxNameLength = 12;
            if (tag.name.length > maxNameLength) {
                tag.name = tag.name.substring(0, maxNameLength) + '...';
            }
            tagDiv.innerHTML =
                `
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
          `;
            this.thoughtsGrid.appendChild(tagDiv);
            
        }
        document.querySelectorAll('.tile').forEach(tile => {
            tile.addEventListener('dragstart', e => {
                tile.classList.add('dragging');
            })
            tile.addEventListener('dragend', e => {
                tile.classList.remove('dragging');
                tile.classList.remove('hovered');
            })
            tile.addEventListener('dragleave', e => {
                this.isHovered--;
                console.log(this.isHovered);
                if(this.isHovered === 0){
                    tile.classList.remove('hovered');
                }
                console.log('leaving');
              
            })
            tile.addEventListener('dragenter', e => {
                this.isHovered++;
                console.log('entering');
                tile.classList.add('hovered');
            })
            tile.addEventListener('drop', e => {
                this.isHovered = 0;
                console.log('asdasdasdasd');
                const dragging = document.querySelector('.dragging');
                tile.classList.remove('hovered');
                this.swap(this.thoughtsGrid, dragging, tile);
            })
        });
    }

    drawThoughts(thoughts) {
        for (let thought of thoughts) {
            let maxNameLength = 12;
            if (tag.name.length > maxNameLength) {
                tag.name = tag.name.substring(0, maxNameLength) + '...';
            }
            tagDiv.innerHTML =
                `
            <div class="tile" draggable="true">
                <div><div class="tile-name">${thought.name}</div></div>
                <div class="tile-img-wrapper">
                    <div class="tile-img thought-img"></div>
                </div>
                <div class="bar">
                    <div class="dot-menu">
                        <div class="dot"></div>
                        <div class="dot"></div>
                        <div class="dot"></div>
                    </div>
                    <div class="drag-icon draggable"></div>
                </div>
          `;
            this.thoughtsGrid.appendChild(tagDiv);
        }
    }
}
