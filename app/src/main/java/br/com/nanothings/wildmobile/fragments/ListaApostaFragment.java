package br.com.nanothings.wildmobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.adapter.ListaApostaAdapter;
import br.com.nanothings.wildmobile.interfaces.ApostaItemManager;
import br.com.nanothings.wildmobile.interfaces.ApostaService;
import br.com.nanothings.wildmobile.model.Aposta;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaApostaFragment extends Fragment implements ApostaItemManager {
    @BindView(R.id.recyclerListaApostas) RecyclerView recyclerListaApostas;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private Context context;
    private ListaApostaAdapter listaApostaAdapter;
    private Call<RestListResponse<Aposta>> requestAposta;
    private List<Aposta> listaApostas = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.menu_apostas);

        return inflater.inflate(R.layout.fragment_lista_aposta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setRecyclerListaApostas();
        listarApostas();
    }

    private void setRecyclerListaApostas() {
        listaApostaAdapter = new ListaApostaAdapter(listaApostas, this);

        recyclerListaApostas.setLayoutManager(new LinearLayoutManager(context));
        recyclerListaApostas.setHasFixedSize(true);
        recyclerListaApostas.addItemDecoration(new DividerItemDecoration(
                recyclerListaApostas.getContext(), DividerItemDecoration.VERTICAL
        ));

        recyclerListaApostas.setAdapter(listaApostaAdapter);
    }

    private void showProgressBar(boolean show) {
        recyclerListaApostas.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void listarApostas() {
        try {
            showProgressBar(true);

            ApostaService apostaService = new RestRequest(context).getService(ApostaService.class);

            if (requestAposta != null) requestAposta.cancel();

            requestAposta = apostaService.listar(1);
            requestAposta.enqueue(new Callback<RestListResponse<Aposta>>() {
                @Override
                public void onResponse(Call<RestListResponse<Aposta>> call, Response<RestListResponse<Aposta>> response) {
                    showProgressBar(false);
                    if (response.isSuccessful()) {
                        RestListResponse<Aposta> resposta = response.body();

                        if (resposta.meta.status.equals(RestRequest.SUCCESS)) {
                            listaApostas.addAll(resposta.data);
                            listaApostaAdapter.setData(listaApostas);
                        } else {
                            Toast.makeText(context, resposta.meta.mensagem, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RestListResponse<Aposta>> call, Throwable t) {
                    showProgressBar(false);
                    Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            showProgressBar(false);
            Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void apostaItemClick(int position) {
        Toast.makeText(context, "Clicou em " + position, Toast.LENGTH_SHORT).show();
    }
}
