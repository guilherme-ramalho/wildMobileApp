package br.com.nanothings.wildmobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.interfaces.ModalidadeApostaService;
import br.com.nanothings.wildmobile.model.ModalidadeAposta;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioFragment extends Fragment {
    @BindView(R.id.spinnerModalidade) Spinner modalidadeSpinner;
    private List<ModalidadeAposta> listaModalidadeAposta;
    private Context context;
    private Call<RestListResponse<ModalidadeAposta>> requestModalidades;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        listarModalidadesAposta();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.menu_home);

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    private void setSpinnerModalidade() {

    }

    private void listarModalidadesAposta() {
        ModalidadeApostaService modalidadeApostaService = new RestRequest(context).getService(ModalidadeApostaService.class);

        if(requestModalidades != null) requestModalidades.cancel();

        requestModalidades = modalidadeApostaService.listar();
        requestModalidades.enqueue(new Callback<RestListResponse<ModalidadeAposta>>() {
            @Override
            public void onResponse(Call<RestListResponse<ModalidadeAposta>> call, Response<RestListResponse<ModalidadeAposta>> response) {
                if(response.isSuccessful()) {
                    RestListResponse<ModalidadeAposta> resposta = response.body();

                    if(resposta.Meta.status.equals("success")) {
                        listaModalidadeAposta = resposta.Data;
                    } else {
                        Toast.makeText(context, resposta.Meta.mensagem, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestListResponse<ModalidadeAposta>> call, Throwable t) {
                Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
