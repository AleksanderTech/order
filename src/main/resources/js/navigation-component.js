import * as modes from './modes.js';

export class NavigationComponent {

    constructor(modeManager, componentId) {
        this.isOpened = false;
        this.modeManager = modeManager;
        this.componentId = componentId;
        this.navigationElement = document.getElementById(this.componentId);
        this.settingsList = document.getElementById('settings-list');
        this.settingsLabel = document.getElementById('settings-label');
        this.signOutElement = document.getElementById("sign-out");
        this.darkModeElement = document.getElementById('dark-mode');
        this.lightModeElement = document.getElementById('light-mode');
    }

    registerHandlers() {
        this.lightModeElement.addEventListener('click', this.setMode.bind(this, modes.LIGHT));
        this.darkModeElement.addEventListener('click', this.setMode.bind(this, modes.DARK));
        this.signOutElement.addEventListener('click', this.signOut.bind(this, this.signOutElement));
        this.settingsLabel.addEventListener('click', this.toggleList.bind(this));
        this.settingsList.addEventListener('mouseleave', this.closeList.bind(this));
    }

    setMode(mode) {
        this.modeManager.setMode(mode, document.documentElement);
    }

    signOut(element) {
        element.submit();
    }

    toggleList() {
        if (this.isOpened) {
            this.closeList();
        } else {
            this.settingsList.classList.add('show-settings');
            this.settingsList.classList.remove('hide-settings');
            this.isOpened = true;
        }
    }

    closeList() {
        this.settingsList.classList.add('hide-settings');
        this.settingsList.classList.remove('show-settings');
        this.isOpened = false;
    }
}
