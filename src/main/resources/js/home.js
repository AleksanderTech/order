import { HomeComponent } from './home-component.js';
import { loadUserMode } from './mode-loader.js';

loadUserMode();
const homeComponent = new HomeComponent('home-component');
homeComponent.registerHandlers();

function goBack() {
    window.history.back();
}



