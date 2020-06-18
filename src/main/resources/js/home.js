import { HomeComponent } from './home-component.js';
import { loadUserMode } from './mode-loader.js';

loadUserMode();
const homeComponent = new HomeComponent('home-component');
homeComponent.registerHandlers();



// .rate-list-div {
//   overflow: hidden;
//   width: 25%;
// }

// .rate-list {
//     transform:translateY(-120%);  
//     list-style-type: none;
//     transition: all 1s ease;
//   }

//   .rate-list-item {
//     margin: 0.4rem 0;
//     font-size: 1.2rem;
//     cursor: pointer;
//     border: 2px solid #a56245;
//     width: 100%;
//     text-align: center;
//   }