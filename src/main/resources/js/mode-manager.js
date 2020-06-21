import * as modes from './modes.js';

export class ModeManager {

    constructor(){
    }

    loadUserMode() {
        const userMode = localStorage.getItem('mode');
        if (userMode) {
            this.setMode(userMode, document.documentElement);
        }
    }

    setMode(mode, element) {
        this.applyStyle(element, this.map(mode));
        localStorage.setItem('mode', mode);
    }

    applyStyle(element, map) {
        for (const key of map.keys()) {
            element.style.setProperty(key, map.get(key));
        }
    }

    map(mode) {
        switch (mode) {
            case modes.LIGHT: return modes.LIGHT_MAP;
            case modes.DARK: return modes.DARK_MAP;
        }
    }
}

