package br.com.nanothings.wildmobile.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.activity.AdicionarPalpiteActivity;
import br.com.nanothings.wildmobile.interfaces.ModalidadeApostaService;
import br.com.nanothings.wildmobile.model.ModalidadeAposta;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioFragment extends Fragment {
    @BindView(R.id.spinnerSorteio)
    Spinner sorteio;
    @BindView(R.id.buttonAdicionarPalpite)
    Button buttonAdicionarPalpite;

    private Context context;

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

        adicionarPalpiteClick();
    }

    private void adicionarPalpiteClick() {
        buttonAdicionarPalpite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdicionarPalpiteActivity.class));
            }
        });
    }
}
