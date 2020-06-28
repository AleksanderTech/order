import { NavigationComponent } from './navigation-component.js';
import { ModeManager } from './mode-manager.js';
import { ThoughtsComponent } from './thoughts-component.js';

const modeManager = new ModeManager();
const navigationComponent = new NavigationComponent(modeManager, 'navigation-component');
const thoughtsComponent = new ThoughtsComponent();

modeManager.loadUserMode();
navigationComponent.registerHandlers();
thoughtsComponent.registerHandlers();




