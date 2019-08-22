package br.com.nanothings.wildmobile.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.activity.AdicionarPalpiteActivity;
import br.com.nanothings.wildmobile.adapter.PalpiteAdapter;
import br.com.nanothings.wildmobile.helper.ProgressLoader;
import br.com.nanothings.wildmobile.helper.Utils;
import br.com.nanothings.wildmobile.interfaces.ApostaService;
import br.com.nanothings.wildmobile.interfaces.PalpiteItemManager;
import br.com.nanothings.wildmobile.interfaces.SorteioService;
import br.com.nanothings.wildmobile.model.Aposta;
import br.com.nanothings.wildmobile.model.Palpite;
import br.com.nanothings.wildmobile.model.Sorteio;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestObjResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioFragment extends Fragment implements PalpiteItemManager {
    @BindView(R.id.spinnerSorteio) Spinner spinnerSorteio;
    @BindView(R.id.botaoAdicionarPalpite) Button buttonAdicionarPalpite;
    @BindView(R.id.recyclerPalpites) RecyclerView recyclerPalpites;
    @BindView(R.id.valorApostaTextView) TextView valorApostaTextView;
    @BindView(R.id.valorPremioTextView) TextView valorPremioTextView;
    @BindView(R.id.botaoFinalizarAposta) Button botaoFinalizarAposta;
    @BindView(R.id.inputNomeApostador) EditText inputNomeApostador;

    private Context context;
    private Call<RestListResponse<Sorteio>> requestSorteio;
    private Call<RestObjResponse<Aposta>> requestAposta;
    private List<Sorteio> listaSorteio;
    private PalpiteAdapter palpiteAdapter;
    private ItemTouchHelper.SimpleCallback itemTouchCallback;
    private Aposta aposta = new Aposta();
    private ProgressLoader progressLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.menu_home);

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        listarSorteios();
        setPalpiteSwipe();
        setRecyclerPalpites();

        progressLoader = new ProgressLoader(getActivity());
        progressLoader.setLoader();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Sorteio sorteio = listaSorteio.get(spinnerSorteio.getSelectedItemPosition());

        Palpite palpite = (Palpite) data.getSerializableExtra("palpite");

        palpite.setSorteio(sorteio);

        aposta.getPalpites().add(palpite);

        palpiteAdapter.setData(aposta.getPalpites());

        calcularTotais();
    }

    @Override
    public void deletarPalpite(int position) {
        Palpite palpite = aposta.getPalpites().get(position);
        aposta.getPalpites().remove(position);
        palpiteAdapter.notifyItemRemoved(position);

        calcularTotais();

        desfazerExclusaoPalpite(palpite, position);
    }

    @Override
    public void editarPalpite(int position) {
        Palpite palpite = aposta.getPalpites().get(position);

        Intent intent = new Intent(getActivity(), AdicionarPalpiteActivity.class);
        intent.putExtra("PalpiteEdicao", palpite);

        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.botaoAdicionarPalpite)
    void adicionarPalpiteClick() {
        if(spinnerSorteio.getSelectedItem() != null) {
            Intent intent = new Intent(getActivity(), AdicionarPalpiteActivity.class);
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(context, R.string.sorteio_constraint, Toast.LENGTH_SHORT).show();
        }
    }

    private void listarSorteios() {
        try {
            SorteioService sorteioService = new RestRequest(context).getService(SorteioService.class);

            if(requestSorteio != null) requestSorteio.cancel();

            requestSorteio = sorteioService.listar();
            requestSorteio.enqueue(new Callback<RestListResponse<Sorteio>>() {
                @Override
                public void onResponse(Call<RestListResponse<Sorteio>> call, Response<RestListResponse<Sorteio>> response) {
                    if(response.isSuccessful()) {
                        RestListResponse<Sorteio> resposta = response.body();

                        if(resposta.meta.status.equals(RestRequest.SUCCESS)) {
                            listaSorteio = resposta.data;
                            setSpinnerSorteio();
                        } else if(resposta.meta.status.equals(RestRequest.EXPIRED)) {
                            //Implementar ação
                        } else {
                            Toast.makeText(context, resposta.meta.mensagem, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RestListResponse<Sorteio>> call, Throwable t) {
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch(Exception e) {
            Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinnerSorteio() {
        ArrayList<String> listaDataSorteio = new ArrayList<>();

        for(Sorteio sorteio : listaSorteio) {
            listaDataSorteio.add(sorteio.getData());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, R.layout.custom_simple_spinner_item, listaDataSorteio
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSorteio.setAdapter(adapter);
    }

    private void setRecyclerPalpites() {
        palpiteAdapter = new PalpiteAdapter(aposta.getPalpites(), this);

        recyclerPalpites.setLayoutManager(new LinearLayoutManager(context));
        recyclerPalpites.setHasFixedSize(true);
        recyclerPalpites.addItemDecoration(new DividerItemDecoration(
                recyclerPalpites.getContext(), DividerItemDecoration.VERTICAL
        ));

        new ItemTouchHelper(itemTouchCallback).attachToRecyclerView(recyclerPalpites);

        recyclerPalpites.setAdapter(palpiteAdapter);
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

    private void desfazerExclusaoPalpite(final Palpite palpite, final int position) {
        View view = getView().findViewById(R.id.inicioLayout);

        Snackbar.make(view, R.string.palpite_removido, Snackbar.LENGTH_LONG)
                .setAction(R.string.desfazer, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        aposta.getPalpites().add(position, palpite);
                        palpiteAdapter.setData(aposta.getPalpites());
                        calcularTotais();
                    }
                }).show();
    }

    private void calcularTotais() {
        aposta.setValorAposta(BigDecimal.ZERO);
        aposta.setValorPremio(BigDecimal.ZERO);

        for(Palpite palpite : aposta.getPalpites()) {
            aposta.addValorAposta(palpite.getValorAposta());

            BigDecimal multiplicadorPalpite = new BigDecimal(palpite.getTipoPalpite().getMultiplicador());
            BigDecimal premioPalpiteAtual = palpite.getValorAposta().multiply(multiplicadorPalpite);
            aposta.addPremioPalpite(premioPalpiteAtual);
        }

        valorApostaTextView.setText(Utils.bigDecimalToStr(aposta.getValorAposta()));
        valorPremioTextView.setText(Utils.bigDecimalToStr(aposta.getValorPremio()));
    }

    private boolean nomeApostadorValido() {
        String nomeApostador = inputNomeApostador.getText().toString();

        if(nomeApostador.isEmpty()) {
            Toast.makeText(context, R.string.sem_nome_apostador, Toast.LENGTH_SHORT).show();

            return false;
        } else {
            aposta.setNomeApostador(nomeApostador);

            return true;
        }
    }

    @OnClick(R.id.botaoFinalizarAposta)
    void botaoFinalizarApostaClick() {
        if(!nomeApostadorValido()) return;

        if(aposta.getPalpites().size() > 0) {
            cadastrarAposta();
        } else {
            Toast.makeText(context, R.string.sem_palpite, Toast.LENGTH_SHORT).show();
        }
    }

    private void cadastrarAposta() {
        try {
            progressLoader.showLoader(true);

            ApostaService apostaService = new RestRequest(context).getService(ApostaService.class);

            if(requestAposta != null) requestAposta.cancel();

            requestAposta = apostaService.cadastrarAposta(aposta);
            requestAposta.enqueue(new Callback<RestObjResponse<Aposta>>() {
                @Override
                public void onResponse(Call<RestObjResponse<Aposta>> call, Response<RestObjResponse<Aposta>> response) {
                    progressLoader.showLoader(false);

                    Utils.getDialogBuilder(getActivity(), getString(R.string.aposta_cadastrada))
                            .setNegativeButton(R.string.voltar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton(R.string.imprimir, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, "Imprimindo...", Toast.LENGTH_SHORT).show();
                                }
                            }).create().show();
                }

                @Override
                public void onFailure(Call<RestObjResponse<Aposta>> call, Throwable t) {
                    progressLoader.showLoader(false);
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch(Exception e) {
            progressLoader.showLoader(false);
            Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
        }
    }
}
