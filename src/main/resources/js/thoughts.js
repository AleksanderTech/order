import { NavigationComponent } from './component/navigation-component.js';
import { ModeManager } from './mode-manager.js';
import { ThoughtsComponent } from './component/thoughts-component.js';
import { ThoughtsMetricsManager } from './thoughts-metrics-manager.js';

const modeManager = new ModeManager();
const thoughtsMetricsManager = new ThoughtsMetricsManager();
const navigationComponent = new NavigationComponent(modeManager, 'navigation-component');
const thoughtsComponent = new ThoughtsComponent('thoughts-component', thoughtsMetricsManager);


modeManager.loadUserMode();
navigationComponent.registerHandlers();
thoughtsComponent.init();



