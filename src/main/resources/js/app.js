

let mode = document.getElementById('mode');
let root = document.documentElement;
let isLightMode = true;
mode.addEventListener("click", e => {
    if (isLightMode) {
        root.style.setProperty('--black-white-1', '#FFFFFF');
        root.style.setProperty('--black-white-2', '#F8F8F8');
        root.style.setProperty('--black-white-3', '#DCDCDC');
        root.style.setProperty('--black-white-4', '#C8C8C8');
        root.style.setProperty('--black-white-5', '#B0B0B0');
        root.style.setProperty('--black-white-6', '#989898');
        root.style.setProperty('--black-white-7', '#787878');
        root.style.setProperty('--black-white-8', '#606060');
        root.style.setProperty('--black-white-9', '#404040');
        root.style.setProperty('--black-white-10', '#202020');
        root.style.setProperty('--red-1', '#980000');
        root.style.setProperty('--tree-image', "url('tree-light.png')");
        root.style.setProperty('--tree-long-image', "url('tree-long-light.png')");
        isLightMode = false;
    } else {
        root.style.setProperty('--black-white-1', '#202020');
        root.style.setProperty('--black-white-2', '#404040');
        root.style.setProperty('--black-white-3', '#606060');
        root.style.setProperty('--black-white-4', '#787878');
        root.style.setProperty('--black-white-5', '#989898');
        root.style.setProperty('--black-white-6', '#B0B0B0');
        root.style.setProperty('--black-white-7', '#C8C8C8');
        root.style.setProperty('--black-white-8', '#DCDCDC');
        root.style.setProperty('--black-white-9', '#F8F8F8');
        root.style.setProperty('--black-white-10', '#FFFFFF');
        root.style.setProperty('--red-1', '#B80000');
        root.style.setProperty('--tree-image', "url('tree.png')");
        root.style.setProperty('--tree-long-image', "url('tree-long.png')");
        isLightMode = true;
    }
});
function goBack() {
    window.history.back();
}