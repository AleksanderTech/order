import * as modes from './mode.js';

export class HomeComponent {

    constructor(componentId) {
        this.componentId = componentId;
        this.element = document.getElementById(this.componentId)
    }

    registerHandlers() {
        let lightModeElement = this.element.querySelector('#light-mode');
        let darkModeElement = this.element.querySelector('#dark-mode');
        let signOut = this.element.querySelector("#sign-out");
        lightModeElement.addEventListener('click', () => {
            modes.setMode(modes.LIGHT, document.documentElement);
        });
        darkModeElement.addEventListener('click', () => {
            modes.setMode(modes.DARK, document.documentElement)
        })
        signOut.addEventListener('click', () => {
          signOut.submit();
        });
    }
}