package br.com.nanothings.wildmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.adapter.PalpiteAdapter;
import br.com.nanothings.wildmobile.helper.Utils;
import br.com.nanothings.wildmobile.interfaces.ApostaService;
import br.com.nanothings.wildmobile.interfaces.PalpiteItemManager;
import br.com.nanothings.wildmobile.model.Aposta;
import br.com.nanothings.wildmobile.rest.RestObjResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalheApostaActivity extends AppCompatActivity implements PalpiteItemManager {
    @BindView(R.id.fabCancelar) FloatingActionButton fabCancelar;
    @BindView(R.id.recyclerDatalhePalpites) RecyclerView recyclerPalpites;
    @BindView(R.id.progressBarDetalhe) ProgressBar progressBar;
    @BindView(R.id.apostadorDetalheTextView) TextView apostadorTextView;
    @BindView(R.id.dataApostaDetalheTextView) TextView dataApostaTextView;
    @BindView(R.id.valorApostaDetalheTextView) TextView valorApostaTextView;
    @BindView(R.id.valorPremioDetalheTextView) TextView valorPremioTextView;
    @BindView(R.id.layoutDetalhesAposta) ConstraintLayout layoutDetalhesAposta;

    private Aposta aposta;
    private PalpiteAdapter palpiteAdapter;
    private Call<RestObjResponse<Aposta>> request;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_aposta);

        context = this;

        ButterKnife.bind(this);

        layoutDetalhesAposta.setVisibility(View.GONE);

        aposta = (Aposta) getIntent().getSerializableExtra("Aposta");

        this.setTitle("Aposta " + aposta.getCodigo());

        listarApostaPorId();
    }

    @OnClick(R.id.fabCancelar)
    void fabCancelarClick() {
        Toast.makeText(this, "Cancelando " + aposta.getCodigo(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_print_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.printButton) {
            imprimirAposta();
            return true;
        } else {
            finish();
            return true;
        }
    }

    private void setRecyclerPalpites() {
        palpiteAdapter = new PalpiteAdapter(aposta.getPalpites(), this);

        recyclerPalpites.setLayoutManager(new LinearLayoutManager(this));
        recyclerPalpites.setHasFixedSize(true);
        recyclerPalpites.addItemDecoration(new DividerItemDecoration(
                recyclerPalpites.getContext(), DividerItemDecoration.VERTICAL
        ));

        recyclerPalpites.setAdapter(palpiteAdapter);
    }

    private void showProgressBar(boolean show) {
        layoutDetalhesAposta.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void listarApostaPorId() {
        try {
            showProgressBar(true);

            ApostaService apostaService = new RestRequest(this).getService(ApostaService.class);

            if (request != null) request.cancel();

            request = apostaService.listarPorId(aposta.getId());
            request.enqueue(new Callback<RestObjResponse<Aposta>>() {
                @Override
                public void onResponse(Call<RestObjResponse<Aposta>> call, Response<RestObjResponse<Aposta>> response) {
                    if (response.isSuccessful()) {
                        RestObjResponse<Aposta> resposta = response.body();

                        if (resposta.meta.status.equals(RestRequest.SUCCESS)) {
                            aposta = resposta.data;

                            apostadorTextView.setText(aposta.getNomeApostador());
                            dataApostaTextView.setText(aposta.getDataFormatada());
                            valorApostaTextView.setText(Utils.bigDecimalToStr(aposta.getValorAposta()));
                            valorPremioTextView.setText(Utils.bigDecimalToStr(aposta.getValorPremio()));

                            setRecyclerPalpites();
                        } else {
                            Toast.makeText(context, resposta.meta.mensagem, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, R.string.processing_error, Toast.LENGTH_SHORT).show();
                    }

                    showProgressBar(false);
                }

                @Override
                public void onFailure(Call<RestObjResponse<Aposta>> call, Throwable t) {
                    showProgressBar(false);
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            showProgressBar(false);
            Toast.makeText(context, R.string.critical_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void imprimirAposta() {
        aposta.imprimirComprovante(context);
    }

    @Override
    public void deletarPalpite(int position) {

    }

    @Override
    public void editarPalpite(int position) {

    }
}
