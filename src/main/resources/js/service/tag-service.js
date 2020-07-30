import { TAG_API_URL } from '../url-provider.js';

export class TagService {

    findMyTags() {
        return fetch(TAG_API_URL)
            .then(res => res.json())
    }

    delete(tagId) {
        return fetch(TAG_API_URL + `?id=${tagId}`, {
            method: 'DELETE'
        }).then(response => response.status);
    }
}