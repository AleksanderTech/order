import { NavigationComponent } from './navigation-component.js';
import { ModeManager } from './mode-manager.js';

const modeManager = new ModeManager();
const navigationComponent = new NavigationComponent(modeManager,'navigation-component');

modeManager.loadUserMode();
navigationComponent.registerHandlers();
