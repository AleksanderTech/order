import { ModeManager } from './mode-manager.js';

const modeManager = new ModeManager();

modeManager.loadUserMode();
document.addEventListener('keypress', e => {
    if(e.keyCode == 13){
        document.forms["sign-in-form"].submit();
    }
});