package br.com.nanothings.wildmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.helper.PreferenceManager;
import br.com.nanothings.wildmobile.helper.ProgressLoader;
import br.com.nanothings.wildmobile.interfaces.CambistaService;
import br.com.nanothings.wildmobile.model.Cambista;
import br.com.nanothings.wildmobile.rest.RestObjResponse;
import br.com.nanothings.wildmobile.rest.RestRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.inputUsuario) EditText inputUsuario;
    @BindView(R.id.inputSenha) EditText inputSenha;
    private Call<RestObjResponse<Cambista>> request;
    private ProgressLoader progressLoader = new ProgressLoader(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressLoader.setLoader();

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.checarAutenticacao();
    }

    @OnClick(R.id.botaoEntrar)
    void entrar() {
        String usuario = inputUsuario.getText().toString();
        String senha = inputSenha.getText().toString();

        if(usuario.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show();
        } else {
            this.autenticar();
        }
    }

    private void autenticar() {
        try {
            progressLoader.showLoader(true);

            String usuario = inputUsuario.getText().toString();
            String senha = inputSenha.getText().toString();

            CambistaService cambistaService = new RestRequest(this).getService(CambistaService.class);

            if(request != null) request.cancel();

            request = cambistaService.autenticar(usuario, senha);
            request.enqueue(new Callback<RestObjResponse<Cambista>>() {
                @Override
                public void onResponse(Call<RestObjResponse<Cambista>> call, Response<RestObjResponse<Cambista>> response) {
                    progressLoader.showLoader(false);

                    RestObjResponse<Cambista> resposta = response.body();

                    if(resposta.meta.status.equals("success")) {
                        new PreferenceManager(getApplicationContext()).setPreference("Cambista", resposta.data);

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, resposta.meta.mensagem, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RestObjResponse<Cambista>> call, Throwable t) {
                    progressLoader.showLoader(false);
                    Toast.makeText(LoginActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch(Exception ex) {
            progressLoader.showLoader(false);
            Toast.makeText(this, R.string.server_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void checarAutenticacao() {
        Cambista cambista = (Cambista) new PreferenceManager(this, Cambista.class).getPreference("Cambista");

        if(cambista != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}
