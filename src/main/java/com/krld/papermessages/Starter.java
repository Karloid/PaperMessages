package com.krld.papermessages;

import com.krld.papermessages.verticles.ApiVerticle;
import io.vertx.core.Vertx;

public class Starter {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ApiVerticle());
    }
}
