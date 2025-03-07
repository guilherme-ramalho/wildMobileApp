package br.com.nanothings.wildmobile.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.adapter.ListaSorteioAdapter;
import br.com.nanothings.wildmobile.helper.DatePickerFragment;
import br.com.nanothings.wildmobile.interfaces.SorteioService;
import br.com.nanothings.wildmobile.model.Sorteio;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaSorteioFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.dataInicialEditText) EditText dataInicialEditText;
    @BindView(R.id.recyclerListaSorteio) RecyclerView recyclerListarSorteio;
    @BindView(R.id.progressBarSorteio) ProgressBar progressBarSorteio;

    private Context context;
    private ListaSorteioAdapter listaSorteioAdapter;
    private Date dataInicial = Calendar.getInstance().getTime();
    private DatePickerDialog.OnDateSetListener dateSetListener = this;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String datainicialStr;
    private Call<RestListResponse<Sorteio>> request;
    private List<Sorteio> listaSorteios = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.menu_resultado);

        return inflater.inflate(R.layout.fragment_lista_sorteio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        inicializarDateInput();
        setRecyclerListarResultado();
        listarSorteios();
    }

    private void setRecyclerListarResultado() {
        listaSorteioAdapter = new ListaSorteioAdapter(listaSorteios);

        recyclerListarSorteio.setLayoutManager(new LinearLayoutManager(context));
        recyclerListarSorteio.setHasFixedSize(true);
        recyclerListarSorteio.addItemDecoration(new DividerItemDecoration(
                recyclerListarSorteio.getContext(), DividerItemDecoration.VERTICAL
        ));

        recyclerListarSorteio.setAdapter(listaSorteioAdapter);
    }

    @OnClick(R.id.dataInicialEditText)
    void inputDataClick() {
        DialogFragment dataInicialPicker = new DatePickerFragment(dateSetListener);
        dataInicialPicker.show(getFragmentManager(), "InicioDatePicker");
    }

    @OnClick(R.id.botaoPesquisar)
    void buscarSorteios() {
        listarSorteios();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month, dayOfMonth, 0, 0);

        Date dataSelecionada = calendar.getTime();

        String dataFormatada = dateFormat.format(dataSelecionada);

        dataInicial = dataSelecionada;

        dataInicialEditText.setText(dataFormatada);
    }

    private void inicializarDateInput() {
        dataInicialEditText.setFocusable(false);

        dataInicialEditText.setText(dateFormat.format(dataInicial));
    }

    private void showProgressBar(boolean show) {
        recyclerListarSorteio.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBarSorteio.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void listarSorteios() {
        try {
            showProgressBar(true);

            SorteioService sorteioService = new RestRequest(context).getService(SorteioService.class);

            if(request != null) request.cancel();

            datainicialStr = new SimpleDateFormat("yyyy-MM-dd").format(dataInicial);

            request = sorteioService.listarResultado(datainicialStr);
            request.enqueue(new Callback<RestListResponse<Sorteio>>() {
                @Override
                public void onResponse(Call<RestListResponse<Sorteio>> call, Response<RestListResponse<Sorteio>> response) {
                    if (response.isSuccessful()) {
                        RestListResponse<Sorteio> resposta = response.body();

                        listaSorteios = resposta.data != null
                                ? resposta.data
                                : new ArrayList<>();

                        listaSorteioAdapter.setData(listaSorteios);
                        Toast.makeText(context, resposta.meta.mensagem, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.processing_error, Toast.LENGTH_SHORT).show();
                    }

                    showProgressBar(false);
                }

                @Override
                public void onFailure(Call<RestListResponse<Sorteio>> call, Throwable t) {
                    showProgressBar(false);
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch(Exception e) {
            showProgressBar(false);
            Toast.makeText(context, R.string.critical_error, Toast.LENGTH_SHORT).show();
        }
    }
}
