package br.com.nanothings.wildmobile.interfaces;

import br.com.nanothings.wildmobile.model.Apuracao;
import br.com.nanothings.wildmobile.model.Cambista;
import br.com.nanothings.wildmobile.rest.RestObjResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CambistaService {
    @GET("cambista/autenticar")
    Call<RestObjResponse<Cambista>> autenticar(
            @Header("usuario") String usuario,
            @Header("senha") String senha
    );

    @GET("cambista/apuracao")
    Call<RestObjResponse<Apuracao>> apuracao(
            @Query("dataInicial") String dataInicial,
            @Query("dataFinal") String dataFinal
    );
}
