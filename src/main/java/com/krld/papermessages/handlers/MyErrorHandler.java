package com.krld.papermessages.handlers;

import com.google.gson.Gson;
import com.krld.papermessages.models.ApiError;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class MyErrorHandler implements io.vertx.ext.web.handler.ErrorHandler {
    @Override
    public void handle(RoutingContext context) {
        HttpServerResponse response = context.response();

        if (context.statusCode() != -1) {
            response.setStatusCode(context.statusCode());
        }
        response.end(new Gson().toJson(new ApiError()));
    }
}
