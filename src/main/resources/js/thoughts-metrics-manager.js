import { buildUrl } from "./url-provider.js";

export class ThoughtsMetricsManager {

    constructor() {
    }

    fetchUserPosition() {
        return fetch(buildUrl('thought', 'view-metrics'))
        .then(res=>res.json());
    }

    savePosition(position) {
        fetch(buildUrl('thought', 'view-metrics'), {
            method: 'POST',
            body: JSON.stringify(position)
        });
    }
}