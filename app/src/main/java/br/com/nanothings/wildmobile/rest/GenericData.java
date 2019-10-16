package br.com.nanothings.wildmobile.rest;

import androidx.annotation.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericData<T> implements ParameterizedType {
    private final Class<T> type;

    public GenericData(Class<T> type) {
        this.type = type;
    }

    @NonNull
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[0];
    }

    @NonNull
    @Override
    public Type getRawType() {
        return null;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
