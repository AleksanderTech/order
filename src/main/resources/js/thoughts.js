import { NavigationComponent } from './navigation-component.js';
import { ModeManager } from './mode-manager.js';
import { ThoughtsComponent } from './thoughts-component.js';

const modeManager = new ModeManager();
const navigationComponent = new NavigationComponent(modeManager, 'navigation-component');
const thoughtsComponent = new ThoughtsComponent();

modeManager.loadUserMode();
navigationComponent.registerHandlers();
thoughtsComponent.registerHandlers();
thoughtsComponent.dragElements();

// function save() {
//     localStorage.setItem('pos1', pos1);
//     localStorage.setItem('pos2', pos2);
//     localStorage.setItem('pos3', pos3);
//     localStorage.setItem('pos4', pos4);
// }

// function load() {
//     pos1 = localStorage.getItem('pos1', pos1);
//     pos2 = localStorage.getItem('pos2', pos2);
//     pos3 = localStorage.getItem('pos3', pos3);
//     pos4 = localStorage.getItem('pos4', pos4);
// }
