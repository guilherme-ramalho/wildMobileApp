package br.com.nanothings.wildmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.interfaces.ModalidadeApostaService;
import br.com.nanothings.wildmobile.model.ModalidadeAposta;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdicionarPalpiteActivity extends AppCompatActivity {
    @BindView(R.id.spinnerModalidade)
    Spinner spinnerModalidade;
    @BindView(R.id.spinnerInicioCerco)
    Spinner spinnerInicioCerco;
    @BindView(R.id.spinnerFinalCerco)
    Spinner spinnerFinalCerco;

    private List<ModalidadeAposta> listaModalidadeAposta;
    private Call<RestListResponse<ModalidadeAposta>> requestModalidades;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_palpite);

        context = this;

        ButterKnife.bind(this);

        listarModalidadesAposta();
        setSpinnersCerco();
    }

    private void setSpinnerModalidade() {
        ArrayList<String> listaTipoPalpite = new ArrayList<>();

        for (ModalidadeAposta modalidadeAposta : listaModalidadeAposta) {
            listaTipoPalpite.addAll(modalidadeAposta.getListaPalpites());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.custom_simple_spinner_item, listaTipoPalpite
        );

        adapter.setDropDownViewResource(R.layout.custom_simple_spinner_dropdown_item);
        spinnerModalidade.setAdapter(adapter);
    }

    private void setSpinnersCerco() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.premios_array, R.layout.custom_simple_spinner_item
        );

        adapter.setDropDownViewResource(R.layout.custom_simple_spinner_dropdown_item);

        spinnerInicioCerco.setAdapter(adapter);
        spinnerFinalCerco.setAdapter(adapter);
    }

    private void listarModalidadesAposta() {
        try {
            ModalidadeApostaService modalidadeApostaService = new RestRequest(this).getService(ModalidadeApostaService.class);

            if(requestModalidades != null) requestModalidades.cancel();

            requestModalidades = modalidadeApostaService.listar();
            requestModalidades.enqueue(new Callback<RestListResponse<ModalidadeAposta>>() {
                @Override
                public void onResponse(Call<RestListResponse<ModalidadeAposta>> call, Response<RestListResponse<ModalidadeAposta>> response) {
                    if(response.isSuccessful()) {
                        RestListResponse<ModalidadeAposta> resposta = response.body();

                        if(resposta.meta.status.equals("success")) {
                            listaModalidadeAposta = resposta.data;
                            setSpinnerModalidade();
                        } else {
                            Toast.makeText(context, resposta.meta.mensagem, Toast.LENGTH_SHORT).show();
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
        } catch(Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
