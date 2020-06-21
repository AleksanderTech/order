import { HomeComponent } from './home-component.js';
import { ModeManager } from './mode-manager.js';

const modeManager = new ModeManager();
const homeComponent = new HomeComponent(modeManager,'home-component');

modeManager.loadUserMode();
homeComponent.registerHandlers();
