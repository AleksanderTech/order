import * as url from '../url-provider.js';


export class ThoughtService {

    findBy(findBy, value) {
        return fetch(url.buildUrl('thought', `by?${findBy}=${value}`))
            .then(res => res.json())
    }

    findAll() {
        return fetch(url.buildUrl('thoughts'))
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