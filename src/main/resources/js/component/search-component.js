export class SearchComponent {


    constructor(componentId, thoughtService) {
        this.componentId = componentId;
        this.thoughtService = thoughtService;
        console.log(this.thoughtService);
        
        this.component = document.getElementById(this.componentId);
        this.searchByNameInput = document.getElementById('searchByNameInput');
    }

    registerHandlers() {
        this.searchByNameInput.addEventListener('click', event=>this.findMyThoughts(event));
    }

    findMyThoughts(event) {
        console.log(event);
        
        this.thoughtService.findMyThoughts().then(res => {
            console.log(res);
        });
    }
}