export const LIGHT = 'light';
export const DARK = 'dark';

export function setMode(mode,element) {
    switch (mode) {
        case DARK:
            element.style.setProperty('--black-white-1', '#FFFFFF');
            element.style.setProperty('--black-white-2', '#F8F8F8');
            element.style.setProperty('--black-white-3', '#DCDCDC');
            element.style.setProperty('--black-white-4', '#C8C8C8');
            element.style.setProperty('--black-white-5', '#B0B0B0');
            element.style.setProperty('--black-white-6', '#989898');
            element.style.setProperty('--black-white-7', '#787878');
            element.style.setProperty('--black-white-8', '#606060');
            element.style.setProperty('--black-white-9', '#404040');
            element.style.setProperty('--black-white-10', '#202020');
            element.style.setProperty('--red-1', '#980000');
            element.style.setProperty('--tree-image', "url('tree-light.png')");
            element.style.setProperty('--tree-long-image', "url('tree-long-light.png')");
            localStorage.setItem('mode', mode);
            break;
        case LIGHT:
            element.style.setProperty('--black-white-1', '#202020');
            element.style.setProperty('--black-white-2', '#404040');
            element.style.setProperty('--black-white-3', '#606060');
            element.style.setProperty('--black-white-4', '#787878');
            element.style.setProperty('--black-white-5', '#989898');
            element.style.setProperty('--black-white-6', '#B0B0B0');
            element.style.setProperty('--black-white-7', '#C8C8C8');
            element.style.setProperty('--black-white-8', '#DCDCDC');
            element.style.setProperty('--black-white-9', '#F8F8F8');
            element.style.setProperty('--black-white-10', '#FFFFFF');
            element.style.setProperty('--red-1', '#B80000');
            element.style.setProperty('--tree-image', "url('tree.png')");
            element.style.setProperty('--tree-long-image', "url('tree-long.png')");
            localStorage.setItem('mode', mode);
            break;
        default: break;
    }
}

