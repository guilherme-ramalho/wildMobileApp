package br.com.nanothings.wildmobile.interfaces;

import br.com.nanothings.wildmobile.model.Cambista;
import br.com.nanothings.wildmobile.rest.RestObjResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ModalidadeApostaService {
    @GET("modalidadeAposta/listar")
    Call<RestObjResponse<Cambista>> listar();
}
