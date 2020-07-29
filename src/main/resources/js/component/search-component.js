import { TileListComponent } from '../component/tile-list-component.js';
import { TagService } from '../service/tag-service.js';
import { ThoughtService } from '../service/thought-service.js';
import * as Drawer from '../drawer.js';

export class SearchComponent {

    searchBy;

    constructor(componentId, thoughtService) {
        this.isOpened = false;
        this.componentId = componentId;
        this.thoughtService = thoughtService;
        this.component = document.getElementById(this.componentId);
        this.tileListComponent = new TileListComponent(document.getElementById('thoughts-grid'), [], this.thoughtService, new TagService());
        this.searching = this.component.querySelector('#searching');
        this.init();
    }

    init() {
        this.drawTheSearch()
        this.registerHandlers();
    }

    drawTheSearch() {
        this.searching.innerHTML = Drawer.searchBy();
        const list = this.component.querySelector('.dropdown-list');
        const dropdown = this.component.querySelector('.dropdown');
        const dropdownLabel = this.component.querySelector('.dropdown-label');
        const dropdownItems = this.component.querySelectorAll('.dropdown-item');
        list.addEventListener('mouseleave', this.closeList.bind(this, list, dropdown));
        dropdownItems.forEach(item => item.addEventListener('click', e => {
            this.searchBy = e.target.getAttribute('data-item-type');
            const findInput = this.component.querySelector('#find-input');
            if (this.searchBy === 'all') {
                findInput.disabled = true;
                this.findMyThoughts();
            } else {
                findInput.removeAttribute('disabled');
            }
            this.component.querySelector('#by').textContent = this.searchBy === 'all' ? 'all' : `by ${this.searchBy}`;
        }));
        dropdownLabel.addEventListener('click', this.toggleList.bind(this, list, dropdown));
    }

    toggleList(list, dropdown) {
        if (this.isOpened) {
            this.closeList(list, dropdown);
        } else {
            dropdown.style.width = ''
            dropdown.style.height = '';
            list.classList.add('show-dropdown');
            list.classList.remove('hide-dropdown');
            this.isOpened = true;
        }
    }

    closeList(list, dropdown) {
        list.classList.add('hide-dropdown');
        list.classList.remove('show-dropdown');
        this.isOpened = false;
        setTimeout(() => {
            dropdown.style.width = 0;
            dropdown.style.height = 0;
        }, 350);
    }

    registerHandlers() {
        this.component.querySelector('#find-input').addEventListener('input', e => {
            let input = e.target.value;
            if (input.trim().length > 0) {
                this.findMyThoughts(input);
            } else if (input.trim().length == 0) {
                this.findMyThoughts('', '');
            }
        });
    }

    findMyThoughts(input) {
        if (this.searchBy === 'all') {
            this.thoughtService.findAll().then(res => { this.drawThoughts(res); });
        } else if (this.searchBy && input) {
            this.thoughtService.findBy(this.searchBy, input).then(res => { this.drawThoughts(res); });
        } else {this.drawThoughts(res); }
    }

    drawThoughts(res){
        this.tileListComponent.drawBareThoughts(res);
        this.tileListComponent.registerHandlers(res);
    }
}