import { ROUTER_INSTANCE } from '../router.js';
import { EVENTS, TAG_SELECTED } from '../events.js'
import { INFORMATION } from '../component/information-component.js';
import * as drawer from '../drawer.js';

export class TileListComponent {

    currentEntity;

    constructor(tileListComponent, tileEntities, thoughtsService, tagService) {
        this.tileListComponent = tileListComponent;
        this.tileEntities = tileEntities;
        this.closeEditor = document.getElementById('close-editor');
        this.editorModal = document.getElementById('editor-modal');
        this.thoughtsService = thoughtsService;
        this.tagService = tagService;
        this.isHovered = 0;
        this.init();
    }

    init() {
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
            this.currentEntity.content = this.editorModal.querySelector('#editor-textarea').value;
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
            this.fetchThoughts(tagOrThought.id);
            EVENTS.emit(TAG_SELECTED, tagOrThought.id)
        } else {
            this.openEditor(tagOrThought);
        }
    }

    openEditor(thought) {
        this.editorModal.classList.remove('display-none');
        this.editorModal.querySelector('#editor-textarea').value = thought.content;
        this.currentEntity = thought;
    }

    saveThought(thought) {
        this.thoughtsService.save(thought)
            .then(() => {
                INFORMATION.show('The Thought has been remembered');
            })
            .catch(err => {
                console.log(err);
            })
    }

    fetchThoughts(tagId) {
        if (tagId) {
            this.thoughtsService.findByTagId(tagId)
                .then(thoughts => {
                    thoughts = thoughts;
                    this.tileEntities = thoughts;
                    if (this.tileEntities.length > 0) {
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
                this.navigate(window.location.pathname, { content: drawer.tagTiles(tags), data: tags }, () => {
                    this.registerHandlers(tags);
                    EVENTS.emit();
                });
            });
    }

    drawBareThoughts(thoughts) {
        if (thoughts && thoughts.length > 0) {
            this.tileListComponent.innerHTML = drawer.thoughtTiles(thoughts);
        } else {
            this.tileListComponent.innerHTML = '<h2 class="no-thoughts-message pad-3">No thoughts found</h2>'
        }
    }

    drawThoughts(thoughts) {
        // if (thoughts && thoughts.length > 0) {
            let thoughtsPage = drawer.thoughtTiles(thoughts);
            return thoughtsPage + '<div class="drag-grid"></div>';
        // }
    }

    noThoughts() {
        window.history.pushState(
            {},
            window.location.pathname + '/no-thoughts',
            window.location.origin + window.location.pathname + '/no-thoughts'
        );
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
        } else {
            ROUTER_INSTANCE.set(pathName, contentWithData.data, contentWithData.content, callback);
        }
        const currentRoute = ROUTER_INSTANCE.getRoute(pathName)
        this.tileListComponent.innerHTML = currentRoute.content;
        currentRoute.callback();
        if (window.location.pathname === '/thoughts') {
            document.querySelector('#new-thought').style.display = 'none';
            document.querySelector('#new-tag').style.display = 'block';
        } else {
            document.querySelector('#new-thought').style.display = 'block';
            document.querySelector('#new-tag').style.display = 'none';
        }
    }
}
