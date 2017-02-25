package com.krld.papermessages.misc;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public class JSONUtils {

    public static Gson get(boolean withNullSerialization) {
        GsonBuilder gsonBuilder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT, Modifier.STATIC);
        if (withNullSerialization) {
            gsonBuilder.serializeNulls();
        }
        return gsonBuilder.create();
    }
}
