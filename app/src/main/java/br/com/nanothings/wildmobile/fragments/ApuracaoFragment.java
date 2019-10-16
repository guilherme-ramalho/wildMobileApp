package br.com.nanothings.wildmobile.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.helper.DatePickerFragment;
import br.com.nanothings.wildmobile.helper.Utils;
import br.com.nanothings.wildmobile.interfaces.CambistaService;
import br.com.nanothings.wildmobile.model.Apuracao;
import br.com.nanothings.wildmobile.rest.RestObjResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApuracaoFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.dataInicialEditText) EditText dataInicialEditText;
    @BindView(R.id.dataFinalEditText) EditText dataFinalEditText;
    @BindView(R.id.botaoPesquisar) Button botaoPesquisar;
    @BindView(R.id.limiteDisponivelTextView) TextView limiteDisponivelTextView;
    @BindView(R.id.quantidadeApostasTextView) TextView quantidadeApostasTextView;
    @BindView(R.id.valorApostadoTextView) TextView valorApostadoTextView;
    @BindView(R.id.valorComissaoTextView) TextView valorComissaoTextView;
    @BindView(R.id.valorPremiacaoTextView) TextView valorPremiacaoTextView;
    @BindView(R.id.valorLiquidoTextView) TextView valorLiquidoTextView;
    @BindView(R.id.progressBarApuracao) ProgressBar progressBar;
    @BindView(R.id.constraintLayoutApuracao) ConstraintLayout layoutApuracao;

    private Context context;
    private Date dataInicial = Calendar.getInstance().getTime();
    private Date dataFinal = dataInicial;
    private DatePickerDialog.OnDateSetListener dateSetListener = this;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final int PICKER_DATA_INICIAL = 0;
    private static final int PICKER_DATA_FINAL = 1;
    private int datePickerSelecionado = 0;
    private String dataInicialStr, dataFinalStr;
    private Call<RestObjResponse<Apuracao>> request;
    private Apuracao apuracao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.menu_apuracao);

        return inflater.inflate(R.layout.fragment_apuracao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        inicializarDateInputs();
        apurar();
    }

    private void inicializarDateInputs() {
        dataInicialEditText.setFocusable(false);
        dataFinalEditText.setFocusable(false);

        dataInicialEditText.setText(dateFormat.format(dataInicial));
        dataFinalEditText.setText(dateFormat.format(dataFinal));
    }

    private void showProgressBar(boolean show) {
        layoutApuracao.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.dataInicialEditText)
    void inputDataInicialClick() {
        datePickerSelecionado = 0;
        DialogFragment dataInicialPicker = new DatePickerFragment(dateSetListener);
        dataInicialPicker.show(getFragmentManager(), "InicioDatePicker");
    }

    @OnClick(R.id.dataFinalEditText)
    void inputDataFinalClick() {
        datePickerSelecionado = 1;
        DialogFragment dataFinalPicker = new DatePickerFragment(dateSetListener);
        dataFinalPicker.show(getFragmentManager(), "FinalDatePicker");
    }

    @OnClick(R.id.botaoPesquisar)
    void botaoPesquisarClick() {
        apurar();
    }

    private void apurar() {
        try {
            showProgressBar(true);

            CambistaService cambistaService = new RestRequest(context).getService(CambistaService.class);

            if (request != null) request.cancel();

            formatarStringsData();

            request = cambistaService.apuracao(dataInicialStr, dataFinalStr);
            request.enqueue(new Callback<RestObjResponse<Apuracao>>() {
                @Override
                public void onResponse(Call<RestObjResponse<Apuracao>> call, Response<RestObjResponse<Apuracao>> response) {
                    if (response.isSuccessful()) {
                        RestObjResponse<Apuracao> resposta = response.body();

                        if (resposta.meta.status.equals(RestRequest.SUCCESS)) {
                            apuracao = resposta.data;
                            atualizarTextos();
                        } else {
                            Toast.makeText(context, resposta.meta.mensagem, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, R.string.processing_error, Toast.LENGTH_SHORT).show();
                    }

                    showProgressBar(false);
                }

                @Override
                public void onFailure(Call<RestObjResponse<Apuracao>> call, Throwable t) {
                    showProgressBar(false);
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            showProgressBar(false);
            Toast.makeText(context, R.string.critical_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarTextos() {
        limiteDisponivelTextView.setText(Utils.bigDecimalToStr(apuracao.getLimiteDisponivel()));
        quantidadeApostasTextView.setText(String.valueOf(apuracao.getQtdApostas()));
        valorApostadoTextView.setText(Utils.bigDecimalToStr(apuracao.getValorApostado()));
        valorComissaoTextView.setText(Utils.bigDecimalToStr(apuracao.getValorComissao()));
        valorPremiacaoTextView.setText(Utils.bigDecimalToStr(apuracao.getValorPremiacao()));
        valorLiquidoTextView.setText(Utils.bigDecimalToStr(apuracao.getValorLiquido()));

        //O text fica vermelho caso o valor seja negativo
        if (apuracao.getValorLiquido().compareTo(BigDecimal.ZERO) >= 0) {
            valorLiquidoTextView.setTextColor(getResources().getColor(R.color.colorSuccess));
        } else {
            valorLiquidoTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
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
