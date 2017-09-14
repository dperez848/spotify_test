package com.daniela.data.managers.remote;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */

public class LongTypedAdapter implements JsonSerializer<Long> {

    @Override
    public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null || src == -1) // exclusion is made here
            return null;

        return new JsonPrimitive(src);
    }
}