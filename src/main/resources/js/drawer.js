export function element(type) {
    let element = document.createElement(type);
    return element;
}

export function searchBy() {
    return `<div class="pad-2">
            <input id = "find-input" class="font-20 input" placeholder="Find" type="text" disabled>
            <div class="dropdown-label search-button display-in-bl"><span id="by">by</span>
                <div class="dropdown col text-black text-left w-33 pad-t-3">
                    <div class="overflow-hidden">
                     <ul class="dropdown-list hide-dropdown">
                         <li class="mar-t-2 pointer dropdown-item" data-item-type="all">all</li>
                         <li class="mar-t-2 pointer dropdown-item" data-item-type="name">name</li>
                         <li class="mar-t-2 pointer dropdown-item" data-item-type="content">content</li>
                         <li class="mar-t-2 pointer dropdown-item" data-item-type="tag">tag</li>
                     </ul>
                    </div>
                </div>
            </div>
    </div>
    `;
}

export function thoughtTiles(thoughts) {
    let thoughtsPage = '';
    for (let thought of thoughts) {
        let maxNameLength = 11;
        if (thought.name.length > maxNameLength) {
            thought.name = thought.name.substring(0, maxNameLength) + '...';
        }
        thoughtsPage = thoughtsPage +
            thoughtTile(thought);
    }
    return thoughtsPage + '<div class="drag-grid"></div>';
}

export function thoughtTile(thought) {
    return `<div class="tile" data-thought-id=${thought.id}" draggable="true">
    <div><div class="tile-name">${thought.name}</div></div>
    <div class="tile-img-wrapper">
        <div class="tile-img thought-img"></div>
    </div>
    <div class="bar">
        <div class="dot-menu">
            <div class="dot"></div>
            <div class="dot"></div>
            <div class="dot"></div>
        </div>
        <div class="drag-icon draggable"></div>
    </div>
    <div class="tile-menu display-none">
     <ul>
       <li class="tile-menu-item menu-item-delete">
          delete
       </li>
     </ul>
     </div>
        </div>`;
}

export function tagTiles(tags) {
    let tagsPage = '';
    for (let tag of tags) {
        let maxNameLength = 12;
        if (tag.name.length > maxNameLength) {
            tag.name = tag.name.substring(0, maxNameLength) + '...';
        }
        tagsPage = tagsPage + tagTile(tag);
    }
    return tagsPage + '<div class="drag-grid"></div>';
}

export function tagTile(tag) {
    return `<div class="tile" data-tag-id="${tag.id}" draggable="true">
    <div><div class="tile-name">${tag.name}</div></div>
    <div class="tile-img-wrapper">
        <div class="tile-img tag-img"></div>
    </div>
    <div class="bar">
        <div class="dot-menu">
            <div class="dot"></div>
            <div class="dot"></div>
            <div class="dot"></div>
        </div>
        <div class="drag-icon draggable"></div>
    </div>
    <div class="tile-menu display-none">
     <ul>
       <li class="tile-menu-item menu-item-delete">
          delete
       </li>
     </ul>
     </div>
    </div>`;
}

export function noThoughts(){
    return '<h2 class="no-thoughts-message pad-3">No thoughts found</h2>';
}

export function append(element, toElement) {
    toElement.append(divfromHtml(element));
}

function divfromHtml(html) {
    var div = document.createElement('div');
    div.innerHTML = html.trim();
    return div.firstChild;
}