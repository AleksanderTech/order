export class SearchComponent {


    constructor(componentId, thoughtRepository) {
        this.componentId = componentId;
        this.thoughtRepository = thoughtRepository;
        console.log(this.thoughtRepository);
        
        this.component = document.getElementById(this.componentId);
        this.searchByNameInput = document.getElementById('searchByNameInput');
    }

    registerHandlers() {
        this.searchByNameInput.addEventListener('click', event=>this.findMyThoughts(event));
    }

    findMyThoughts(event) {
        console.log(event);
        
        this.thoughtRepository.findMyThoughts().then(res => {
            console.log(res);
        });
    }
}