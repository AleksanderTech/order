import { NavigationComponent } from './component/navigation-component.js';
import { SearchComponent } from './component/search-component.js';
import { ModeManager } from './mode-manager.js';
import { ThoughtRepository } from './repository/thought-repository.js';

const modeManager = new ModeManager();
const navigationComponent = new NavigationComponent(modeManager,'navigation-component');
const thoughtRepository = new ThoughtRepository();
const searchComponent  = new SearchComponent('search-component',thoughtRepository);
searchComponent.registerHandlers();
modeManager.loadUserMode();
navigationComponent.registerHandlers();
