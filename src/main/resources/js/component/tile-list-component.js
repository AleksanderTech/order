import { ROUTER_INSTANCE } from '../router.js';
import { InformationComponent } from './information-component.js';
import { EVENTS } from '../events.js'

export class TileListComponent {

    currentEntity;

    constructor(tileListComponent, tileEntities, thoughtsService, tagService) {
        this.tileListComponent = tileListComponent;
        this.information = new InformationComponent(document.querySelector('.information-component'));
        this.tileEntities = tileEntities;
        this.closeEditor = document.getElementById('close-editor');
        this.editorModal = document.getElementById('editor-modal');
        this.thoughtsService = thoughtsService;
        this.tagService = tagService;
        this.isHovered = 0;
        this.init();
    }

    init(){
        this.registerHandlers(this.tileEntities);
    }

    registerHandlers(thoughtsOrTags) {
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
                if (this.isHovered === 0) {
                    tile.classList.remove('hovered');
                }
            })
            tile.addEventListener('dragenter', e => {
                this.isHovered++;
                tile.classList.add('hovered');
            })
            tile.addEventListener('drop', e => {
                this.isHovered = 0;
                const dragging = document.querySelector('.dragging');
                tile.classList.remove('hovered');
                this.swap(this.tileListComponent, dragging, tile);
            })
            tile.querySelector('.tile-img-wrapper').addEventListener('click', e => {
                let thoughtOrTag = thoughtsOrTags.find(thoughtOrTag => thoughtOrTag.name === tile.querySelector('.tile-name').innerText);
                this.onTileClick(thoughtOrTag);
            });
        });
        document.getElementById('save-thought-button').addEventListener('click', (e) => {
            this.saveThought(this.currentEntity);
        });
        this.closeEditor.addEventListener('click', () => {
            this.editorModal.classList.add('display-none')
        });

        document.getElementById('back-thought-button').addEventListener('click', () => {
            this.editorModal.classList.add('display-none')
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

    onTileClick(tagOrThought) {
        if (tagOrThought.hasOwnProperty('parentTagId')) {
            // if (tagOrThought instanceof Tag) {
            this.fetchThoughts(tagOrThought.id);
        } else {
            this.openEditor(tagOrThought);
        }
    }

    openEditor(thought) {
        document.getElementById('editor-modal').classList.remove('display-none');
        this.currentEntity = thought;
    }

    saveThought(thought) {
        this.thoughtsService.save(thought)
            .then(() => {
                this.information.show('The Thought has been remembered');
            })
            .catch(err => {
                console.log(err);
            })
    }

    fetchThoughts(tagId) {
        if(tagId){
            this.thoughtsService.findByTagId(tagId)
            .then(thoughts => {
                thoughts = thoughts; 
                this.tileEntities = thoughts;
                if (this.tileEntities.length > 0) {
                    console.log('navigating');
                    this.navigate(`/thoughts/${tagId}`, { content: this.drawThoughts(thoughts), data: thoughts }, () => {
                        this.registerHandlers(thoughts);
                        EVENTS.emit();
                    });
                } else {
                    this.noThoughts()
                }
            }).catch(err => {
                console.log(err);
                this.noThoughts()
            })
        }
    }

    fetchTags() {
        this.tagService.findMyTags()
            .then(tags => {
                this.tags = tags;
                console.log(this.tags);
                this.navigate(window.location.pathname, { content: this.drawTags(tags), data: tags }, () => {
                    this.registerHandlers(tags);
                    EVENTS.emit();
                });
            });
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
                    `<div class="tile" data-thought-id=${thought.id}" draggable="true">
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
                </div>`;
            }
            return thoughtsPage + '<div class="drag-grid"></div>';
        }
    }
    drawTags(tags) {
        let tagsPage = '';
        for (let tag of tags) {
            let maxNameLength = 12;
            if (tag.name.length > maxNameLength) {
                tag.name = tag.name.substring(0, maxNameLength) + '...';
            }
            tagsPage = tagsPage +
                `<div class="tile" data-tag-id="${tag.id}" draggable="true">
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

    noThoughts() {
        this.tileListComponent.innerHTML =
            `<h2 class="no-thoughts-message">There is no any thoughts</h2>`;
    }

    navigate(pathName, contentWithData, callback) {
        window.history.pushState(
            {},
            pathName,
            window.location.origin + pathName
        );
        if (ROUTER_INSTANCE.thereIsNo(pathName)) {
            ROUTER_INSTANCE.add(pathName, contentWithData.data, contentWithData.content, callback);
        }
        const currentRoute = ROUTER_INSTANCE.getRoute(pathName)
        this.tileListComponent.innerHTML = currentRoute.content;
        currentRoute.callback();
    }
}
