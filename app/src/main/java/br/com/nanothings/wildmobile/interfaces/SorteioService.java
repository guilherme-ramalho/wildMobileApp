package br.com.nanothings.wildmobile.interfaces;

import br.com.nanothings.wildmobile.model.Sorteio;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SorteioService {
    @GET("sorteio/listar?listarValidos=true")
    Call<RestListResponse<Sorteio>> listar();

    @GET("sorteio/resultado")
    Call<RestListResponse<Sorteio>> listarResultado(@Query("data") String data);
}
