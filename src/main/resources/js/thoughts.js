import { NavigationComponent } from './component/navigation-component.js';
import { ModeManager } from './mode-manager.js';
import { ThoughtsComponent } from './component/thoughts-component.js';

const modeManager = new ModeManager();
const navigationComponent = new NavigationComponent(modeManager, 'navigation-component');
const thoughtsComponent = new ThoughtsComponent();

modeManager.loadUserMode();
navigationComponent.registerHandlers();
thoughtsComponent.registerHandlers();
thoughtsComponent.dragElements();
thoughtsComponent.fetchTags();


