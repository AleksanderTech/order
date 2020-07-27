import { TileListComponent } from '../component/tile-list-component.js';
import { TagService } from '../service/tag-service.js';

export class SearchComponent {
    constructor(componentId, thoughtService) {
        this.isOpened=false;
        this.componentId = componentId;
        this.thoughtService = thoughtService;
        this.component = document.getElementById(this.componentId);
        this.tileListComponent = new TileListComponent(document.getElementById('thoughts-grid'), [], this.thoughtsService, new TagService());
        this.searching = this.component.querySelector('#searching');
        this.init();
    }

    init() {
        console.log(this.component);
        this.drawSearchBy();
        this.tileListComponent.fetchTags();
        this.registerHandlers();
    }

    drawSearchBy(){
        this.searching.innerHTML =
        `<div class="pad-2">
        <input id = "by-name-input" class="font-20 input" placeholder="Find" type="text">
        <div class="dropdown-label by">by
        <div class="dropdown col text-black text-left w-33 pad-t-3">
                <div class="overflow-hidden">
                    <ul class="dropdown-list hide-dropdown">
                        <li class="mar-t-2 pointer">Name</li>
                        <li class="mar-t-2 pointer">Content</li>
                        <li class="mar-t-2 pointer">Tags</li>
                    </ul>
                </div>
            </div>
        </div>
        </div>
        `;
        const list = this.component.querySelector('.dropdown-list');
        list.addEventListener('mouseleave', this.closeList.bind(this,list));
        this.component.querySelector('.dropdown-label').addEventListener('click', this.toggleList.bind(this,list));;
    }

    toggleList(list) {
        if (this.isOpened) {
            this.closeList(list);
        } else {
            list.classList.add('show-dropdown');
            list.classList.remove('hide-dropdown');
            this.isOpened = true;
        }
    }

    closeList(list) {
        list.classList.add('hide-dropdown');
        list.classList.remove('show-dropdown');
        this.isOpened = false;
    }

    registerHandlers() {
        this.component.querySelector('#by-name-input').addEventListener('input', (e) => {
            console.log(e.target.value);
        });
    }

    // findMyThoughts(event) {
    //     console.log(event);

    //     this.thoughtService.findMyThoughts().then(res => {
    //         console.log(res);
    //     });
    // }
}