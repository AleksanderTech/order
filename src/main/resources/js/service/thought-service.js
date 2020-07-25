import { buildUrl } from '../url-provider.js';

export class ThoughtService {

    findMyThoughts() {
        return fetch(buildUrl('thought'))
            .then(res => res.json())
    }

    findByTagId(tagId) {
        return fetch(buildUrl('thought?tag-id=' + tagId))
            .then(res => res.json());
    }
}