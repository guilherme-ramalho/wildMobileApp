package br.com.nanothings.wildmobile.interfaces;

import br.com.nanothings.wildmobile.model.Aposta;
import br.com.nanothings.wildmobile.rest.RestObjResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApostaService {
    @POST("/aposta/cadastrar")
    Call<RestObjResponse<Aposta>> cadastrarAposta(@Body Aposta aposta);
}
