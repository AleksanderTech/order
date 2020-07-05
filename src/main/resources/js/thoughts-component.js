export class ThoughtsComponent {

    constructor(componentId) {
        this.currentTagId = null;
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
    }

    registerHandlers() {
        this.thoughtsGrid.addEventListener('dragover', e => {
            e.preventDefault();
        });
        this.tiles.forEach(tile => {
            tile.addEventListener('dragstart', e => {
                tile.classList.add('dragging');
            })
            tile.addEventListener('dragend', e => {
                tile.classList.remove('dragging');
                tile.classList.remove('hovered');
            })
            tile.addEventListener('dragleave', e => {
                tile.classList.remove('hovered');
            })
            tile.addEventListener('dragenter', e => {
                tile.classList.add('hovered');
            })
            tile.addEventListener('drop', e => {
                const dragging = document.querySelector('.dragging');
                tile.classList.remove('hovered');
                this.swap(this.thoughtsGrid, dragging, tile);
            })
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
        this.resizeGrid.addEventListener('change', e => {
            // if (checkbox.checked) {
            //     resizeObserver.observe(divElem);
            // } else {
            //     resizeObserver.unobserve(divElem);
            // }
        });
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
}