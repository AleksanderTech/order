import * as url from '../url-provider.js';


export class ThoughtService {

    findMyThoughts() {
        return fetch(url.buildUrl('thought'))
            .then(res => res.json())
    }

    findByTagId(tagId) {
        return fetch(url.buildUrl('thought?tag-id=' + tagId))
            .then(res => res.json());
    }

    save(thought) {
        return fetch(url.THOUGHT_API_URL, {
            method: 'POST',
            body: JSON.stringify(thought)
        });
    }
}