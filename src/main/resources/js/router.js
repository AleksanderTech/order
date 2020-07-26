export class Router {
    constructor() {
        this.routes = [];
    }

    add(uri, data, content, callback) {
        // this.routes.forEach(route => {

        // });
        for (let route of this.routes) {
            if (route.uri === uri) {
                // throw new Error(`the uri ${route.uri} already exists`);
                return;
            }
        }
        const route = {
            uri,
            data,
            content,
            callback
        }
        this.routes.push(route);
    }

    thereIsNo(pathName) {
        let thereIsNo = true;
        this.routes.forEach(route => {
            if (route.uri === pathName) {
                thereIsNo = false;
            }
        });
        return thereIsNo;
    }

    getRoute(uri) {
        let routeResult = {};
        this.routes.forEach(route => {
            if (route.uri === uri) {
                routeResult = route;
            }
        });
        return routeResult;
    }
}
export const ROUTER_INSTANCE = new Router();