export class ThoughtsComponent {

    constructor(componentId) {
        this.thoughtsElement = document.getElementById(componentId);
        this.tiles = document.querySelectorAll('.tile');
        this.thoughtsGrid = document.getElementById('thoughts-grid');
        this.thoughtsManagement = document.getElementById('thoughts-management');
    }

    registerHandlers() {
        this.thoughtsGrid.addEventListener('dragover', e => {
            e.preventDefault();
        });
        this.tiles.forEach(tile => {
            tile.addEventListener('dragstart', event => {
                tile.classList.add('dragging');
            })
            tile.addEventListener('dragend', event => {
                tile.classList.remove('dragging');
                tile.classList.remove('hovered');
            })
            tile.addEventListener('dragleave', event => {
                tile.classList.remove('hovered');
            })
            tile.addEventListener('dragenter', event => {
                tile.classList.add('hovered');
            })
            tile.addEventListener('drop', event => {
                const dragging = document.querySelector('.dragging');
                tile.classList.remove('hovered');
                this.swap(this.thoughtsGrid, dragging, tile);
            })
        }
        );
        this.thoughtsManagement.addEventListener('click', event => {
            this.createThought();
            this.findThoughts()
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

    findThoughts(){
        fetch('http://localhost:7000/api/thoughts')
        .then(response => response.json())
        .then(response=>{
            console.log(response);
            
        })
    }

    createThought() {

    }
}