package com.krld.papermessages.verticles;

import com.krld.papermessages.api.LettersApi;
import com.krld.papermessages.handlers.MyErrorHandler;
import com.krld.papermessages.misc.FLog;
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
        HttpServerOptions options = createOptions();
        server = vertx.createHttpServer(options);
        server.requestHandler(createRouter()::accept);
        server.listen(event -> {
            if (event.succeeded()) {
                FLog.d("Succeeded started web server on port: " + options.getPort());
            } else {
                FLog.e("Start FAILED for web server on port: " + options.getPort());
                System.exit(1);
            }
        });
    }

    private Router createRouter() {

        Router router = Router.router(vertx);
        router.route().failureHandler(new MyErrorHandler());

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
        router.put("/letter/addMessage/:token").handler(event -> lettersApi.addMessage(event));

        return router;
    }

    private static HttpServerOptions createOptions() {
        HttpServerOptions options = new HttpServerOptions();
        options.setHost("localhost");
        options.setPort(9000);
        return options;
    }

}
