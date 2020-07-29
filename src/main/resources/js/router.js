export class Router {
    constructor() {
        this.routes = [];
    }

    add(uri, data, content, callback) {
        for (let route of this.routes) {
            if (route.uri === uri) {
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

    set(uri, data, content, callback) {
        for (let i = 0; i < this.routes.length; i++) {
            if (this.routes[i].uri === uri) {
                const route = {
                    uri,
                    data,
                    content,
                    callback
                }
                this.routes[i] = route;
            }
        }
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