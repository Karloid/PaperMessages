package com.krld.papermessages.api;

import com.google.gson.Gson;
import com.krld.papermessages.api.errors.*;
import com.krld.papermessages.misc.Constants;
import com.krld.papermessages.misc.JSONUtils;
import com.krld.papermessages.misc.Utils;
import com.krld.papermessages.models.CatchLetterResponse;
import com.krld.papermessages.models.Letter;
import com.krld.papermessages.models.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.util.*;

public class LettersApi {

    private Gson gson = JSONUtils.get(false);

    private List<Letter> letters = new ArrayList<>();

    private Map<String, Letter> tokensToLetter = new HashMap<>();
    private LinkedList<String> tokens = new LinkedList<>();

    public void catchLetter(RoutingContext context) {
        HttpServerResponse response = context.response();

        if (letters.isEmpty()) {
            context.put(Constants.EXTRA_ERROR, new NoLettersAvailableError());
            context.fail(400);
            return;
        }

        Letter letter = letters.get((int) (Math.random() * letters.size()));
        CatchLetterResponse letterResponse = new CatchLetterResponse();
        letterResponse.letter = letter;
        letterResponse.token = Utils.randomString(10);
        saveToken(letterResponse.token, letter);
        response.end(gson.toJson(letterResponse));
    }

    public void createLetter(RoutingContext context) {
        String string = context.getBodyAsString();
        if (Utils.isEmpty(string)) {
            Utils.fail(context, new BadJsonError());
            return;
        }

        Message msg = gson.fromJson(string, Message.class);
        if (Utils.isEmpty(msg.text)) {
            Utils.fail(context, new BadMessageError());
            return;
        }
        msg.originServerTs = System.currentTimeMillis();
        Letter letter = Letter.create(msg);
        letters.add(letter);
        context.response().end(gson.toJson(new Object()));
    }

    private void saveToken(String token, Letter letter) {
        tokensToLetter.put(token, letter);
        tokens.addLast(token);
        if (tokens.size() > 10_000) {
            String oldest = tokens.getFirst();
            tokens.removeFirst();
            tokensToLetter.remove(oldest);
        }
    }

    public void addMessage(RoutingContext context) {
        String token = context.request().getParam("token");
        if (Utils.isEmpty(token)) {
            Utils.fail(context, new MissingTokenError());
            return;
        }
        String string = context.getBodyAsString();
        if (Utils.isEmpty(string)) {
            Utils.fail(context, new BadJsonError());
            return;
        }

        Message msg = gson.fromJson(string, Message.class);
        if (Utils.isEmpty(msg.text)) {
            Utils.fail(context, new BadMessageError());
            return;
        }

        Letter letter = tokensToLetter.get(token);
        if (letter == null) {
            Utils.fail(context, new UnknownTokenError());
            return;
        }
        msg.originServerTs = System.currentTimeMillis();
        letter.messages.add(msg);
        tokensToLetter.remove(token);
        tokens.remove(token);

        context.response().end(gson.toJson(new Object()));
    }
}
