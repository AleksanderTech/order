import { ROUTER_INSTANCE } from '../router.js';
import { EVENTS, TAG_SELECTED } from '../events.js'
import { INFORMATION } from '../component/information-component.js';
import * as drawer from '../drawer.js';

export class TileListComponent {

    currentEntity;
    currentTagId;

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
            tile.querySelector('.dot-menu').addEventListener('click', () => {
                tile.querySelector('.tile-menu').classList.toggle('display-none');
                tile.querySelector('.menu-item-delete').addEventListener('click', () => {
                    let thoughtOrTag = thoughtsOrTags.find(thoughtOrTag => thoughtOrTag.name === tile.querySelector('.tile-name').innerText);
                    this.delete(thoughtOrTag);
                });
            })
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

    delete(thoughtOrTag) {
        if (thoughtOrTag.hasOwnProperty('parentTagId')) {
            this.tagService.delete(thoughtOrTag.id)
                .then((status) => {
                    if(status === 400){
                        INFORMATION.show('The Tag cannot be deleted until thoughts are assigned');
                    }else{
                        INFORMATION.show('The Tag has been deleted');
                        this.tagService.findMyTags()
                            .then(tags => {
                                this.tags = tags;
                                this.navigate(window.location.pathname, { content: drawer.tagTiles(tags), data: tags }, () => {
                                    this.registerHandlers(tags);
                                    EVENTS.emit();
                                });
                            }).catch(err => {
                                console.log(err);
                            })
                    }
                }).catch((err) => {
                    INFORMATION.show('Error occured');
                    console.log(err);
                })
        } else {
            this.thoughtsService.delete(thoughtOrTag.id)
                .then(() => {
                    INFORMATION.show('The Thought has been deleted');
                    this.thoughtsService.findByTagId(this.currentTagId)
                        .then(thoughts => {
                            this.drawBareThoughts(thoughts);
                            this.registerHandlers(thoughts);
                        }).catch(err => {
                            console.log(err);
                        })
                })
                .catch((err) => {
                    INFORMATION.show('Error occured');
                    console.log(err);
                })
        }
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
            this.currentTagId = tagOrThought.id;
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
                INFORMATION.show('Error occured');
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
                        this.navigate(`/thoughts/${tagId}`, { content: drawer.thoughtTiles(thoughts), data: thoughts }, () => {
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
            this.tileListComponent.innerHTML = drawer.noThoughts();
        }
    }

    noThoughts() {
        window.history.pushState(
            {},
            window.location.pathname + '/no-thoughts',
            window.location.origin + window.location.pathname + '/no-thoughts'
        );
        document.querySelector('#new-thought').style.display = 'block';
        document.querySelector('#new-tag').style.display = 'none';
        this.tileListComponent.innerHTML = drawer.noThoughts();
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
