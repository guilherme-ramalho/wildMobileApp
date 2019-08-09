package br.com.nanothings.wildmobile.interfaces;

import br.com.nanothings.wildmobile.model.Sorteio;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SorteioService {
    @GET("sorteio/listar")
    Call<RestListResponse<Sorteio>> listar();
}
