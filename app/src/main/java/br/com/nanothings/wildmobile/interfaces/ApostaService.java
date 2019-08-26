package br.com.nanothings.wildmobile.interfaces;

import br.com.nanothings.wildmobile.model.Aposta;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestObjResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApostaService {
    @POST("/aposta/cadastrar")
    Call<RestObjResponse<Aposta>> cadastrarAposta(@Body Aposta aposta);

    @GET("/aposta/listar")
    Call<RestListResponse<Aposta>> listar(
            @Query("dataInicial") String dataInicial,
            @Query("dataFinal") String dataFinal,
            @Query("pagina") int pagina
    );
}
