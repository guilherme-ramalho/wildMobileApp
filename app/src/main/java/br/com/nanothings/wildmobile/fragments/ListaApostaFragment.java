package br.com.nanothings.wildmobile.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import br.com.nanothings.wildmobile.activity.DetalheApostaActivity;
import br.com.nanothings.wildmobile.adapter.ListaApostaAdapter;
import br.com.nanothings.wildmobile.helper.Constants;
import br.com.nanothings.wildmobile.helper.DatePickerFragment;
import br.com.nanothings.wildmobile.interfaces.ApostaItemManager;
import br.com.nanothings.wildmobile.interfaces.ApostaService;
import br.com.nanothings.wildmobile.model.Aposta;
import br.com.nanothings.wildmobile.rest.Paginacao;
import br.com.nanothings.wildmobile.rest.RestListResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaApostaFragment extends Fragment implements ApostaItemManager, DatePickerDialog.OnDateSetListener {
    @BindView(R.id.recyclerListaApostas) RecyclerView recyclerListaApostas;
    @BindView(R.id.progressBarApuracao) ProgressBar progressBar;
    //@BindView(R.id.bottomProgressFrame) FrameLayout bottomProgressFrame;
    @BindView(R.id.dataInicialEditText) EditText dataInicialEditText;
    @BindView(R.id.dataFinalEditText) EditText dataFinalEditText;
    @BindView(R.id.botaoPesquisar) Button botaoPesquisar;

    private Context context;
    private ListaApostaAdapter listaApostaAdapter;
    private Call<RestListResponse<Aposta>> requestAposta;
    private List<Aposta> listaApostas = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener dateSetListener = this;
    private Date dataInicial = Calendar.getInstance().getTime();
    private Date dataFinal = dataInicial;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final int PICKER_DATA_INICIAL = 0;
    private static final int PICKER_DATA_FINAL = 1;
    private static final int ITEMS_POR_PAGINA = 15;
    private int datePickerSelecionado = 0;
    private String dataInicialStr, dataFinalStr;
    private Paginacao paginacao = new Paginacao();

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

        inicializarDateInputs();
        inputDataInicialClick();
        inputDataFinalClick();
        setRecyclerListaApostas();
        listarApostas(true);
        pesquisarApostasClick();
        setRecyclerOnScrollListener();
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

    private void setRecyclerOnScrollListener() {
        recyclerListaApostas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int scollState) {
                super.onScrollStateChanged(recyclerView, scollState);

                int qtdItens = recyclerView.getAdapter().getItemCount();
                boolean scrollParou = scollState == RecyclerView.SCROLL_STATE_IDLE;

                if (mostrandoUltimoItem(recyclerView) && qtdItens < paginacao.getTotalItens() && scrollParou) {
                    paginacao.avancarPagina();
                    listarApostas(false);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private boolean mostrandoUltimoItem(RecyclerView recyclerView) {
        int qtdItens = recyclerView.getAdapter().getItemCount();

        if (qtdItens > 0) {
            int posicaoUltimoItemVisivel = ((LinearLayoutManager)recyclerView.getLayoutManager())
                    .findLastVisibleItemPosition();

            if (posicaoUltimoItemVisivel != RecyclerView.NO_POSITION && posicaoUltimoItemVisivel == qtdItens - 1) {
                return true;
            }
        }

        return false;
    }

    private void showProgressBar(boolean show) {
        recyclerListaApostas.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void listarApostas(boolean esvaziarLista) {
        try {
            showProgressBar(true);

            ApostaService apostaService = new RestRequest(context).getService(ApostaService.class);

            if (requestAposta != null) requestAposta.cancel();

            formatarStringsData();

            requestAposta = apostaService.listar(dataInicialStr, dataFinalStr, paginacao.getPaginaAtual(), ITEMS_POR_PAGINA);
            requestAposta.enqueue(new Callback<RestListResponse<Aposta>>() {
                @Override
                public void onResponse(Call<RestListResponse<Aposta>> call, Response<RestListResponse<Aposta>> response) {
                    if (response.isSuccessful()) {
                        RestListResponse<Aposta> resposta = response.body();

                        if (resposta.meta.status.equals(RestRequest.SUCCESS)) {
                            if (esvaziarLista) {
                                listaApostas.clear();
                            }

                            listaApostas.addAll(resposta.data);
                            listaApostaAdapter.setData(listaApostas);

                            paginacao = resposta.meta.paginacao;
                        } else {
                            Toast.makeText(context, resposta.meta.mensagem, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, R.string.processing_error, Toast.LENGTH_SHORT).show();
                    }

                    showProgressBar(false);
                }

                @Override
                public void onFailure(Call<RestListResponse<Aposta>> call, Throwable t) {
                    showProgressBar(false);
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            showProgressBar(false);
            Toast.makeText(context, R.string.critical_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void formatarStringsData() {
        SimpleDateFormat usDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dataInicialStr = usDateFormat.format(dataInicial);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataFinal);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        dataFinalStr = usDateFormat.format(calendar.getTime());
    }

    private void inputDataInicialClick() {
        dataInicialEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerSelecionado = 0;
                DialogFragment dataInicialPicker = new DatePickerFragment(dateSetListener);
                dataInicialPicker.show(getFragmentManager(), "InicioDatePicker");
            }
        });
    }

    private void inputDataFinalClick() {
        dataFinalEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerSelecionado = 1;
                DialogFragment dataFinalPicker = new DatePickerFragment(dateSetListener);
                dataFinalPicker.show(getFragmentManager(), "FinalDatePicker");
            }
        });
    }

    private void inicializarDateInputs() {
        dataInicialEditText.setFocusable(false);
        dataFinalEditText.setFocusable(false);

        dataInicialEditText.setText(dateFormat.format(dataInicial));
        dataFinalEditText.setText(dateFormat.format(dataFinal));
    }

    private void pesquisarApostasClick() {
        botaoPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarApostas(true);
            }
        });
    }

    @Override
    public void apostaItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetalheApostaActivity.class);
        intent.putExtra("Aposta", listaApostas.get(position));
        //startActivity(intent);
        startActivityForResult(intent, Constants.FRAGMENT_APOSTAS_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && data.getBooleanExtra("ApostaCancelada", false) == true) {
            listarApostas(true);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 0, 0);
        Date dataSelecioanda = calendar.getTime();
        String dataFormatada = dateFormat.format(dataSelecioanda);

        switch (datePickerSelecionado) {
            case PICKER_DATA_INICIAL:
                dataInicial = dataSelecioanda;
                dataInicialEditText.setText(dataFormatada);
                break;

            case PICKER_DATA_FINAL:
                dataFinal = dataSelecioanda;
                dataFinalEditText.setText(dataFormatada);
                break;
        }
    }
}
