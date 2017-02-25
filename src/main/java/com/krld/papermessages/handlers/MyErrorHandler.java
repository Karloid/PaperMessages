package com.krld.papermessages.handlers;

import com.google.gson.Gson;
import com.krld.papermessages.api.errors.ApiError;
import com.krld.papermessages.misc.Constants;
import com.krld.papermessages.misc.JSONUtils;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class MyErrorHandler implements io.vertx.ext.web.handler.ErrorHandler {

    public static final ApiError API_ERROR = new ApiError();
    private Gson gson = JSONUtils.get(false);

    @Override
    public void handle(RoutingContext context) {
        HttpServerResponse response = context.response();

        if (context.statusCode() != -1) {
            response.setStatusCode(context.statusCode());
        }
        ApiError error = context.get(Constants.EXTRA_ERROR);
        if (error == null) {
            error = API_ERROR;
        }

        response.end(gson.toJson(error));
    }
}
