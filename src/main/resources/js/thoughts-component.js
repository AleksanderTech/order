export class ThoughtsComponent {

    constructor(componentId) {
        this.thoughtsElement = document.getElementById(componentId);
        this.tiles = document.querySelectorAll('.tile');
        this.thoughtsGrid = document.getElementById('thoughts-grid');
        this.newThoughtButton = document.getElementById('new-thought-button');
        this.newTagButton = document.getElementById('new-tag-button');
        this.creationModal = document.getElementById('creation-modal');
        this.closeModal = document.getElementById('close-modal');
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
        })

        this.newTagButton.addEventListener('click', e => {
            this.creationModal.style.display = 'block';
        })
        this.closeModal.addEventListener('click', e => {
            this.creationModal.style.display = 'none';
        })
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
}