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
import br.com.nanothings.wildmobile.interfaces.SorteioService;
import br.com.nanothings.wildmobile.model.Sorteio;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioFragment extends Fragment {
    @BindView(R.id.spinnerSorteio)
    Spinner spinnerSorteio;
    @BindView(R.id.buttonAdicionarPalpite)
    Button buttonAdicionarPalpite;

    private Context context;
    private Call<RestListResponse<Sorteio>> requestSorteio;
    private List<Sorteio> listaSorteio;

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
        adicionarPalpiteClick();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int numero = data.getIntExtra("numero", 1);
    }

    private void adicionarPalpiteClick() {
        buttonAdicionarPalpite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerSorteio.getSelectedItem() != null) {
                    Intent intent = new Intent(getActivity(), AdicionarPalpiteActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(context, R.string.sorteio_constraint, Toast.LENGTH_SHORT).show();
                }
            }
        });
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

        spinnerSorteio.setAdapter(adapter);
    }
}
