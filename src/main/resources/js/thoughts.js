import { NavigationComponent } from './component/navigation-component.js';
import { ModeManager } from './mode-manager.js';
import { ThoughtsComponent } from './component/thoughts-component.js';
import { ThoughtsMetricsManager } from './thoughts-metrics-manager.js';
import { ThoughtService } from './service/thought-service.js';
import { TagService } from './service/tag-service.js';

const modeManager = new ModeManager();
const thoughtsMetricsManager = new ThoughtsMetricsManager();
const thoughtService = new ThoughtService();
const tagService = new TagService();
const navigationComponent = new NavigationComponent(modeManager, 'navigation-component');
const thoughtsComponent = new ThoughtsComponent('thoughts-component', thoughtsMetricsManager, thoughtService, tagService);


modeManager.loadUserMode();
navigationComponent.registerHandlers();
// thoughtsComponent.init();



