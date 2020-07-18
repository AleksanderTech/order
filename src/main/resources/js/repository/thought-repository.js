import { buildUrl } from '../url-provider.js';

export class ThoughtRepository {

    findMyThoughts() {
        return fetch(buildUrl('thought'))
            .then(res => res.json())
    }
}