package com.krld.papermessages.verticles;

import com.krld.papermessages.api.LettersApi;
import com.krld.papermessages.handlers.MyErrorHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

public class ApiVerticle extends AbstractVerticle {

    private static final String APPLICATION_JSON = "application/json";
    private HttpServer server;

    private LettersApi lettersApi;

    @Override
    public void start() throws Exception {
        super.start();
        lettersApi = new LettersApi();
        server = vertx.createHttpServer(createOptions());
        server.requestHandler(createRouter()::accept);
        server.listen();
    }

    private Router createRouter() {

        Router router = Router.router(vertx);
        router.route().failureHandler(new MyErrorHandler());

		/* Static resources *//*
        staticHandler(router);

		*//* Session / cookies for users *//*
        router.route().handler(CookieHandler.create());
        SessionStore sessionStore = LocalSessionStore.create(vertx);
        SessionHandler sessionHandler = SessionHandler.create(sessionStore);
        router.route().handler(sessionHandler);
        userContextHandler = new UserContextHandler(mongo);*/

		/* Dynamic pages */

        router.mountSubRouter("/api", apiRouter());


        return router;
    }

    private Router apiRouter() {
        /* API */
        Router router = Router.router(vertx);
        router.route().consumes(APPLICATION_JSON);
        router.route().produces(APPLICATION_JSON);
        router.route().handler(BodyHandler.create());
        router.route().handler(context -> {
            context.response().headers().add(CONTENT_TYPE, APPLICATION_JSON);
            context.next();
        });

        router.get("/letter/catch/").handler(event -> lettersApi.catchLetter(event));
        router.put("/letter/create/").handler(event -> lettersApi.createLetter(event));

        return router;
    }

    private static HttpServerOptions createOptions() {
        HttpServerOptions options = new HttpServerOptions();
        options.setHost("localhost");
        options.setPort(9000);
        return options;
    }

}
