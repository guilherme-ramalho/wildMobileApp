package br.com.nanothings.wildmobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.adapter.PalpiteAdapter;
import br.com.nanothings.wildmobile.helper.Constants;
import br.com.nanothings.wildmobile.helper.MajoraMask;
import br.com.nanothings.wildmobile.helper.Utils;
import br.com.nanothings.wildmobile.interfaces.ModalidadeApostaService;
import br.com.nanothings.wildmobile.interfaces.PalpiteItemManager;
import br.com.nanothings.wildmobile.model.ModalidadeAposta;
import br.com.nanothings.wildmobile.model.Palpite;
import br.com.nanothings.wildmobile.model.TipoPalpite;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdicionarPalpiteActivity extends AppCompatActivity implements PalpiteItemManager {
    @BindView(R.id.spinnerTipoPalpite) Spinner spinnerTipoPalpite;
    @BindView(R.id.spinnerPrimeiroPremio) Spinner spinnerPrimeiroPremio;
    @BindView(R.id.spinnerUltimoPremio) Spinner spinnerUltimoPremio;
    @BindView(R.id.inputPalpite) EditText inputPalpite;
    @BindView(R.id.inputValorPalpite) EditText inputValorPalpite;
    @BindView(R.id.recyclerPalpitesInclusos) RecyclerView recyclerPalpitesInclusos;

    private List<ModalidadeAposta> listaModalidadeAposta;
    private Call<RestListResponse<ModalidadeAposta>> requestModalidades;
    private Palpite palpite;
    private Context context;
    private TipoPalpite tipoPalpite;
    private ModalidadeAposta modalidadeSelcionada;
    private MajoraMask majoraMask = new MajoraMask();
    private ArrayList<Palpite> listaPalpites = new ArrayList<>();
    private PalpiteAdapter palpiteAdapter;
    private ItemTouchHelper.SimpleCallback itemTouchCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_palpite);

        context = this;

        ButterKnife.bind(this);

        listarModalidadesAposta();
        setSpinnersPremios();
        spinnerModalidadeChange();
        spinnerPrimeiroPremioChange();
        spinnerUltimoPremioChange();
        setPalpiteSwipe();
        setRecyclerPalpitesInclusos();

        majoraMask.addCurrencyMask(inputValorPalpite);

        verificarIntentExtra();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void verificarIntentExtra() {
        palpite = (Palpite) getIntent().getSerializableExtra("PalpiteEdicao");

        if(palpite != null) {
            inputPalpite.setText(palpite.getNumerosString());
            inputValorPalpite.setText(palpite.getValorAposta().toString());
            spinnerPrimeiroPremio.setSelection(palpite.getPrimeiroPremio() - 1);
            spinnerUltimoPremio.setSelection(palpite.getUltimoPremio() - 1);
        } else {
            palpite = new Palpite();
        }
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
        spinnerTipoPalpite.setAdapter(adapter);

        spinnerTipoPalpite.setSelection(palpite.getTipoPalpite().getId() - 1);
    }

    private void setSpinnersPremios() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.premios_array, R.layout.custom_simple_spinner_item
        );

        adapter.setDropDownViewResource(R.layout.custom_simple_spinner_dropdown_item);

        spinnerPrimeiroPremio.setAdapter(adapter);
        spinnerUltimoPremio.setAdapter(adapter);
    }

    private void setRecyclerPalpitesInclusos() {
        palpiteAdapter = new PalpiteAdapter(listaPalpites, this);
        recyclerPalpitesInclusos.setLayoutManager(new LinearLayoutManager(context));
        recyclerPalpitesInclusos.setHasFixedSize(true);
        recyclerPalpitesInclusos.addItemDecoration(new DividerItemDecoration(
                recyclerPalpitesInclusos.getContext(), DividerItemDecoration.VERTICAL
        ));

        new ItemTouchHelper(itemTouchCallback).attachToRecyclerView(recyclerPalpitesInclusos);

        recyclerPalpitesInclusos.setAdapter(palpiteAdapter);
    }

    private void setPalpiteSwipe() {
        itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deletarPalpite(viewHolder.getAdapterPosition());
            }
        };
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

                        if(resposta.meta.status.equals(RestRequest.SUCCESS)) {
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

    @OnClick(R.id.buttonAdicionarPalpite)
    void buttonIncluirPalpiteClick() {
        if(!palpiteValido()) return;
        if(!valorApostaValido()) return;
        if(!premiosSelecionadosValidos()) return;

        //Verifica se existem palpites repetidos
        if (!listaPalpites.isEmpty()) {
            for(Palpite palpiteAtual : listaPalpites) {
                boolean tipoPalpitesIguais = palpiteAtual.getTipoPalpite().getId() == palpite.getTipoPalpite().getId();
                boolean numeroPalpitesIguais = palpiteAtual.getNumerosString().equals(palpite.getNumerosString());
                boolean intervalosIguais = palpiteAtual.getTextIntervaloPremio().equals(palpite.getTextIntervaloPremio());

                if (tipoPalpitesIguais && numeroPalpitesIguais && intervalosIguais) {
                    Toast.makeText(context, R.string.palpites_repetidos, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        listaPalpites.add(palpite);

        palpiteAdapter.setData(listaPalpites);

        palpite = new Palpite();
        palpite.setTipoPalpite(tipoPalpite);
        palpite.setPrimeiroPremio(spinnerPrimeiroPremio.getSelectedItemPosition()+1);
        palpite.setUltimoPremio(spinnerUltimoPremio.getSelectedItemPosition()+1);
    }

    @OnClick(R.id.buttonFinalizarInclusao)
    void buttonFinalizarInclusao() {
        if (listaPalpites.isEmpty()) {
            Toast.makeText(context, R.string.lista_palpites_vazia, Toast.LENGTH_SHORT).show();
        } else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("ListaPalpites", listaPalpites);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private boolean palpiteValido() {
        String numerosPalpiteStr = inputPalpite.getText().toString();

        String mascaraPalpite = majoraMask.getPalpiteMask();

        if(numerosPalpiteStr.isEmpty() || numerosPalpiteStr.length() != mascaraPalpite.length()) {
            Toast.makeText(this, R.string.erro_tamanho_palpite, Toast.LENGTH_SHORT).show();
            
            return false;
        }

        String[] numerosPalpiteArr = numerosPalpiteStr.split("-");

        for(int i = 0 ; i < numerosPalpiteArr.length ; i++) {
            int numeroPalpite = Integer.parseInt(numerosPalpiteArr[i]);
            int numeroMinimo = tipoPalpite.getValorMinimo();
            int numeroMaximo = tipoPalpite.getValorMaximo();

            //Verifica se o número está dentro do intervalo permitido
            if(numeroPalpite < numeroMinimo || numeroPalpite > numeroMaximo) {
                Toast.makeText(this, R.string.erro_valor_palpite, Toast.LENGTH_LONG).show();
                return false;
            }

            //Verifica se algum dos números do palpite está repetido
            for(int k = i + 1 ; k < numerosPalpiteArr.length ; k++) {
                if(numerosPalpiteArr[k].equals(numerosPalpiteArr[i])) {
                    Toast.makeText(context, "Números repetidos no palpite", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        palpite.setNumerosString(numerosPalpiteStr);
        
        return true;
    }
    
    private boolean premiosSelecionadosValidos() {
        if(palpite.getPrimeiroPremio() > palpite.getUltimoPremio()) {
            Toast.makeText(context, R.string.erro_intervalo_premio, Toast.LENGTH_LONG).show();
            
            return false;
        }

        if( (palpite.getUltimoPremio() - palpite.getPrimeiroPremio() + 1) < palpite.getNumerosArray().length) {
            String mensagem = getString(
                    R.string.erro_quantidade_premio,
                    String.valueOf(tipoPalpite.getQtdDigitos())
            );

            Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();

            return false;
        }
        
        return true;
    }

    private boolean valorApostaValido() {
        String valorApostaStr = inputValorPalpite.getText().toString();
        
        if(valorApostaStr.isEmpty()) {
            Toast.makeText(context, R.string.aposta_vazia, Toast.LENGTH_SHORT).show();
            inputValorPalpite.requestFocus();

            return false;
        }

        valorApostaStr = Utils.brToUsCurrency(valorApostaStr);

        BigDecimal valorAposta = new BigDecimal(valorApostaStr);

        //O valor da aposta está zerado
        if(valorAposta.compareTo(BigDecimal.ZERO) <= 0) {
            Toast.makeText(context, R.string.erro_valor_aposta_zerado, Toast.LENGTH_SHORT).show();
            return false;
        }

        //Calculando os valores da aposta, e do prêmio dividindo pela quantidade de prêmio selecionados.
        palpite.setValorAposta(valorAposta);
        Integer qtdPremios = (palpite.getUltimoPremio() - palpite.getPrimeiroPremio()) + 1;
        BigDecimal multiplicador = new BigDecimal(tipoPalpite.getMultiplicador()/qtdPremios);
        palpite.setValorPremio(valorAposta.multiply(multiplicador));

        return true;
    }

    private void spinnerModalidadeChange() {
        spinnerTipoPalpite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<TipoPalpite> listaTipoPalpite = new ArrayList<>();

                for (ModalidadeAposta modalidadeAposta : listaModalidadeAposta) {
                    listaTipoPalpite.addAll(modalidadeAposta.getPalpites());
                }

                tipoPalpite = listaTipoPalpite.get(i);

                aplicarMascaraPalpite(tipoPalpite.getIdModalidadeAposta());

                palpite.setTipoPalpite(tipoPalpite);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerPrimeiroPremioChange() {
        spinnerPrimeiroPremio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                palpite.setPrimeiroPremio(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerUltimoPremioChange() {
        spinnerUltimoPremio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                palpite.setUltimoPremio(i+1);
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
        int qtdDigitos = tipoPalpite.getQtdDigitos();

        String pattern = "";

        for(int i = 1 ; i <=qtdInputs ; i++) {
            pattern += Utils.repeatString("#", qtdDigitos);

            if(i < qtdInputs) pattern += "-";
        }

        majoraMask.removePalpiteTextWatcher(inputPalpite);
        majoraMask.setPalpiteMask(pattern);
        majoraMask.addPalpiteMask(inputPalpite);
    }

    @Override
    public void deletarPalpite(int position) {
        listaPalpites.remove(position);
        palpiteAdapter.notifyItemRemoved(position);
    }

    @Override
    public void editarPalpite(int position) {

    }
}
