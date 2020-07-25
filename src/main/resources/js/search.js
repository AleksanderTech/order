import { NavigationComponent } from './component/navigation-component.js';
import { SearchComponent } from './component/search-component.js';
import { ModeManager } from './mode-manager.js';
import { ThoughtService } from './service/thought-service.js';

const modeManager = new ModeManager();
const navigationComponent = new NavigationComponent(modeManager,'navigation-component');
const thoughtService = new ThoughtService();
const searchComponent  = new SearchComponent('search-component',thoughtService);
searchComponent.registerHandlers();
modeManager.loadUserMode();
navigationComponent.registerHandlers();
