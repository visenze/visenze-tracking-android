package com.visenze.datatracking.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public abstract class JsonStringSerializer {
    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.setLongSerializationPolicy( LongSerializationPolicy.STRING );

        gsonBuilder.registerTypeAdapter(Double.class,  new JsonSerializer<Double>() {
            @Override
            public JsonElement serialize(final Double src, final Type typeOfSrc, final JsonSerializationContext context) {
                return getJsonElement(src == null, BigDecimal.valueOf(src));
            }
        });

        gsonBuilder.registerTypeAdapter(Float.class,  new JsonSerializer<Float>() {
            @Override
            public JsonElement serialize(final Float src, final Type typeOfSrc, final JsonSerializationContext context) {
                return getJsonElement(src == null, BigDecimal.valueOf(src));
            }
        });

        gsonBuilder.registerTypeAdapter(BigDecimal.class,  new JsonSerializer<BigDecimal>() {
            @Override
            public JsonElement serialize(final BigDecimal src, final Type typeOfSrc, final JsonSerializationContext context) {
                return getJsonElement(src == null, src);
            }
        });

        gsonBuilder.registerTypeAdapter(Integer.class,  new JsonSerializer<Integer>() {
            @Override
            public JsonElement serialize(final Integer src, final Type typeOfSrc, final JsonSerializationContext context) {
                return getJsonElement(src == null, BigDecimal.valueOf(src));
            }
        });

    }

    private static JsonElement getJsonElement(boolean isNull, BigDecimal bigDecimal) {
        if (isNull) {
            return new JsonPrimitive("");
        }

        BigDecimal value = bigDecimal;
        return new JsonPrimitive(value.toPlainString());
    }

    public static Gson getStringSerializer() {
        return gsonBuilder.create();
    }
}
