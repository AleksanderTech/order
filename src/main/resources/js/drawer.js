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
                         <li class="mar-t-2 pointer dropdown-item" data-item-type="name">Name</li>
                         <li class="mar-t-2 pointer dropdown-item" data-item-type="content">Content</li>
                         <li class="mar-t-2 pointer dropdown-item" data-item-type="tag">Tag</li>
                     </ul>
                    </div>
                </div>
            </div>
    </div>
    `;
}

export function append(element, toElement) {
    toElement.append(divfromHtml(element));
}

function divfromHtml(html) {
    var div = document.createElement('div');
    div.innerHTML = html.trim();
    return div.firstChild;
}