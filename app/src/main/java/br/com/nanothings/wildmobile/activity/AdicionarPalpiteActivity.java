package br.com.nanothings.wildmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.helper.MajoraMask;
import br.com.nanothings.wildmobile.helper.Utils;
import br.com.nanothings.wildmobile.interfaces.ModalidadeApostaService;
import br.com.nanothings.wildmobile.model.ModalidadeAposta;
import br.com.nanothings.wildmobile.model.Palpite;
import br.com.nanothings.wildmobile.model.TipoPalpite;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdicionarPalpiteActivity extends AppCompatActivity {
    @BindView(R.id.spinnerModalidade) Spinner spinnerModalidade;
    @BindView(R.id.spinnerInicioCerco) Spinner spinnerInicioCerco;
    @BindView(R.id.spinnerFinalCerco) Spinner spinnerFinalCerco;
    @BindView(R.id.buttonIncluirPalpite) Button buttonIncluirPalpite;
    @BindView(R.id.inputPalpite) EditText inputPalpite;
    @BindView(R.id.inputValorAposta) EditText inputValorAposta;

    private List<ModalidadeAposta> listaModalidadeAposta;
    private Call<RestListResponse<ModalidadeAposta>> requestModalidades;
    private Palpite palpite = new Palpite();
    private Context context;
    private TipoPalpite palpiteSelecionado;
    private ModalidadeAposta modalidadeSelcionada;
    private MajoraMask majoraMask = new MajoraMask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_palpite);

        context = this;

        ButterKnife.bind(this);

        listarModalidadesAposta();
        setSpinnersCerco();
        buttonIncluirPalpiteClick();
        spinnerModalidadeChange();
        spinnerInicioCercoChange();
        spinnerFinalCercoChange();

        majoraMask.addCurrencyMask(inputValorAposta);
    }

    private void setSpinnerModalidade() {
        ArrayList<String> listaTipoPalpite = new ArrayList<>();

        for (ModalidadeAposta modalidadeAposta : listaModalidadeAposta) {
            listaTipoPalpite.addAll(modalidadeAposta.getListaNomesPalpites());
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

    private void buttonIncluirPalpiteClick() {
        buttonIncluirPalpite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retornarPalpite();
            }
        });
    }

    private void retornarPalpite() {
        if(!valorApostaValido()) return;
        if(!cercoValido()) return;

        Intent resultIntent = new Intent();
        resultIntent.putExtra("palpite", palpite);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    
    private boolean cercoValido() {
        if(palpite.getInicioCerco() > palpite.getFinalCerco()) {
            Toast.makeText(context, R.string.cerco_invalido, Toast.LENGTH_SHORT).show();
            
            return false;
        }
        
        return true;
    }

    private boolean valorApostaValido() {
        String valorApostaStr = inputValorAposta.getText().toString();
        
        if(valorApostaStr.isEmpty()) {
            Toast.makeText(context, R.string.aposta_vazia, Toast.LENGTH_SHORT).show();
            inputValorAposta.requestFocus();

            return false;
        }

        palpite.setValorAposta(new BigDecimal(valorApostaStr));

        return true;
    }

    private void spinnerModalidadeChange() {
        spinnerModalidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<TipoPalpite> listaTipoPalpite = new ArrayList<>();

                for (ModalidadeAposta modalidadeAposta : listaModalidadeAposta) {
                    listaTipoPalpite.addAll(modalidadeAposta.getPalpites());
                }

                palpiteSelecionado = listaTipoPalpite.get(i);

                aplicarMascaraPalpite(palpiteSelecionado.getIdModalidadeAposta());

                palpite.setIdTipoPalpite(palpiteSelecionado.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerInicioCercoChange() {
        spinnerInicioCerco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                palpite.setInicioCerco(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerFinalCercoChange() {
        spinnerFinalCerco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                palpite.setFinalCerco(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void aplicarMascaraPalpite(int idModalidade) {
        for(ModalidadeAposta modalidadeAposta : listaModalidadeAposta) {
            if(idModalidade == modalidadeAposta.getId()) {
                modalidadeSelcionada =  modalidadeAposta;
            }
        }

        int qtdInputs = modalidadeSelcionada.getQtdInputs();
        int qtdDigitos = palpiteSelecionado.getQtdDigitos();

        String pattern = "";

        for(int i = 1 ; i <=qtdInputs ; i++) {
            pattern += Utils.repeatString("#", qtdDigitos);

            if(i < qtdInputs) pattern += "-";
        }

        majoraMask.removePalpiteTextWatcher(inputPalpite);
        majoraMask.setPalpiteMask(pattern);
        majoraMask.addPalpiteMask(inputPalpite);
    }
}
