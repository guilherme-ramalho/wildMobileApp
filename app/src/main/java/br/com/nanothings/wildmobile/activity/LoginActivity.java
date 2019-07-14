package br.com.nanothings.wildmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.model.Cambista;
import br.com.nanothings.wildmobile.rest.RestObjResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.inputUsuario) EditText inputUsuario;
    @BindView(R.id.inputSenha) EditText inputSenha;
    private Call<RestObjResponse<Cambista>> request;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
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

    }
}
