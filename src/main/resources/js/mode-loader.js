import { setMode } from './mode.js'

export function loadUserMode() {
    let userMode = localStorage.getItem('mode');
    if (userMode) {
        setMode(userMode, document.documentElement);
    }
}