export const SERVER_URL = 'http://localhost:7000';
export const SERVER_API_URL = 'http://localhost:7000/api';
export const THOUGHT_API_URL = SERVER_API_URL + '/thought';

export function buildUrl(...pathParts) {
    let result = SERVER_API_URL;
    for (let path of pathParts) {
        result += '/' + path;
    }
    return result;
}

