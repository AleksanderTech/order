import { NavigationComponent } from './navigation-component.js';
import { ModeManager } from './mode-manager.js';
import { ThoughtsComponent } from './thoughts-component.js';

const modeManager = new ModeManager();
const navigationComponent = new NavigationComponent(modeManager, 'navigation-component');
const thoughtsComponent = new ThoughtsComponent();

modeManager.loadUserMode();
navigationComponent.registerHandlers();
thoughtsComponent.registerHandlers();
let pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
load();
dragElement(document.getElementById("thoughts-grid"));

document.getElementById('saveBlock').addEventListener('click', e => {
    save();
})

function outOfBounds() {
    let thoughtsWrapper = document.getElementById('thoughts-wrapper');
    thoughtsWrapper.classList.remove('dashed-border');
    thoughtsWrapper.classList.add('alert-border');
}

function inBounds() {
    let thoughtsWrapper = document.getElementById('thoughts-wrapper');
    thoughtsWrapper.classList.remove('alert-border');
    thoughtsWrapper.classList.add('dashed-border');
}

function isOutOfBounds(element, parent) {
    let elementBox = element.getBoundingClientRect();
    let parentBox = parent.getBoundingClientRect();
    let topElem = elementBox.top;
    let leftElem = elementBox.left;
    let rightElem = elementBox.right;

    let topPar = parentBox.top;
    let leftPar = parentBox.left;
    let rightPar = parentBox.right;
    return topElem < topPar || leftElem < leftPar || rightElem > rightPar;
}

const ro = new ResizeObserver(entries => {
    for (let entry of entries) {
        entry.target.classList.add('dashed-border');
    }
});

document.getElementById('parent-tag-id-input');

document.getElementById('resize-grid').addEventListener('change', e => {
    if (document.getElementById('resize-grid').checked) {
        ro.observe(document.getElementById('thoughts-grid'));
        addBorder();
    } else {
        ro.unobserve(document.getElementById('thoughts-grid'));
        removeBorder();
    }
});

document.getElementById('resize-handle').addEventListener('mousedown', e => {

    addBorder();
    console.log('adding');

    ro.observe(document.getElementById('thoughts-grid'));
});


document.getElementById('resize-handle').addEventListener('mouseup', e => {

    console.log('removing');
    removeBorder();
    ro.unobserve(document.getElementById('thoughts-grid'));

});
// Only observe the 2nd box
// ro.observe(document.getElementById('thoughts-grid'));


function addBorder() {
    let thoughtsWrapper = document.getElementById('thoughts-wrapper');
    let thoughtsGrid = document.getElementById('thoughts-grid');
    thoughtsWrapper.classList.add('dashed-border');
    thoughtsGrid.classList.add('dashed-border');
}

function removeBorder() {
    let thoughtsWrapper = document.getElementById('thoughts-wrapper');
    let thoughtsGrid = document.getElementById('thoughts-grid');
    thoughtsWrapper.classList.remove('dashed-border');
    thoughtsGrid.classList.remove('dashed-border');
}

function save() {
    localStorage.setItem('pos1', pos1);
    localStorage.setItem('pos2', pos2);
    localStorage.setItem('pos3', pos3);
    localStorage.setItem('pos4', pos4);
}

function load() {
    pos1 = localStorage.getItem('pos1', pos1);
    pos2 = localStorage.getItem('pos2', pos2);
    pos3 = localStorage.getItem('pos3', pos3);
    pos4 = localStorage.getItem('pos4', pos4);
}

function dragElement(element) {

    element.style.left = pos1 + 'px';
    element.style.top = pos2 + 'px';
    document.getElementById('drag-me').addEventListener('mousedown', dragMouseDown);

    function dragMouseDown(e) {
        addBorder()
        pos3 = e.clientX;
        pos4 = e.clientY;
        document.onmouseup = closeDragElement;
        document.onmousemove = elementDrag;
    }

    function elementDrag(e) {
        if (isOutOfBounds(element, element.parentElement)) {
            outOfBounds();
        } else {
            inBounds();
        }
        pos1 = pos3 - e.clientX;
        pos2 = pos4 - e.clientY;
        pos3 = e.clientX;
        pos4 = e.clientY;
        element.style.top = (element.offsetTop - pos2) + "px";
        element.style.left = (element.offsetLeft - pos1) + "px";
    }

    function closeDragElement() {
        document.onmouseup = null;
        document.onmousemove = null;
        removeBorder();
    }
}



