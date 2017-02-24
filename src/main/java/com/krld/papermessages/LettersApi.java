package com.krld.papermessages;

import com.google.gson.Gson;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;

public class LettersApi {

    private Gson gson = new Gson();

    public void catchLetter(RoutingContext context) {
        HttpServerResponse response = context.response();

        //TODO pick from store
        Letter src = new Letter();
        src.messages = new ArrayList<>();

        response.end(gson.toJson(src));
    }

    public void createLetter(RoutingContext event) {
        String string = event.getBodyAsString();
        Message msg = gson.fromJson(string, Message.class);
        if (msg.text == null || msg.text.isEmpty()) {
            event.fail(400);
            return;
        }
        event.response().end(gson.toJson(new Object()));
    }
}
