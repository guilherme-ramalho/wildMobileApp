package br.com.nanothings.wildmobile.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class RestObjResponse<T> implements Serializable {
    @Expose
    public T data;

    @Expose
    public Meta meta;

    public RestObjResponse(String json, Class<T> type) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        this.meta = new Gson().fromJson(jsonObject.getString("meta"), Meta.class);
        this.data = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
                .fromJson(jsonObject.getString("data"), new GenericData<T>(type));
    }

}
