package br.com.nanothings.wildmobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import br.com.nanothings.wildmobile.model.Aposta;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaApostaFragment extends Fragment implements ApostaItemManager {
    @BindView(R.id.recyclerListaApostas) RecyclerView recyclerListaApostas;

    private Context context;
    private ListaApostaAdapter listaApostaAdapter;
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
}
