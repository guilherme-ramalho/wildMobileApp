package br.com.nanothings.wildmobile.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class RestListResponse<T> implements Serializable {
    @Expose
    public List<T> data;

    @Expose
    public Meta meta;

    public RestListResponse(String json, Class<T> type) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        this.meta = new Gson().fromJson(jsonObject.getString("meta"), Meta.class);
        this.data = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
                .create()
                .fromJson(
                        jsonObject.getString("data"),
                        new GenericData<T>(type)
                );
    }
}
