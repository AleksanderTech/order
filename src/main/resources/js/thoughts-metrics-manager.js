import { buildUrl } from "./url-provider.js";

export class ThoughtsMetricsManager {

    constructor() {
    }

    fetchUserPosition() {
        return fetch(buildUrl('thought', 'view-metrics'))
            .then(res => res.json())
    }

    setPosition(position) {
        // this.applyStyle(element, this.map(mode));
    }

    savePosition(position) {
        fetch(buildUrl('thought', 'view-metrics'), {
            method: 'POST',
            body: JSON.stringify(position)
        }).then(res => console.log(res));
    }

}