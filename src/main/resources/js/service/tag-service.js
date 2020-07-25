import { buildUrl } from '../url-provider.js';

export class TagService {

    findMyTags() {
        return fetch(buildUrl('tag'))
            .then(res => res.json())
    }
}