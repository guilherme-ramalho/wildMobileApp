package br.com.nanothings.wildmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import br.com.nanothings.wildmobile.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.inputUsuario) EditText inputUsuario;
    @BindView(R.id.inputSenha) EditText inputSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.botaoEntrar)
    void entrar() {
        Toast.makeText(this, "Entrando..." + inputUsuario.getText(), Toast.LENGTH_LONG).show();
    }
}
