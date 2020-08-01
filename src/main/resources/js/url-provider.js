export const SERVER_URL = 'http://alexecute:7000';
export const SERVER_API_URL = 'http://alexecute:7000/api';
export const THOUGHT_API_URL = SERVER_API_URL + '/thought';
export const TAG_API_URL = SERVER_API_URL + '/tag';

export function buildUrl(...pathParts) {
    let result = SERVER_API_URL;
    for (let path of pathParts) {
        result += '/' + path;
    }
    return result;
}

