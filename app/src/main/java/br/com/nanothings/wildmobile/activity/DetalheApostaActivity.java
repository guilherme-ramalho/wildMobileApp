package br.com.nanothings.wildmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.model.Aposta;

public class DetalheApostaActivity extends AppCompatActivity {
    Aposta aposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_aposta);

        aposta = (Aposta) getIntent().getSerializableExtra("Aposta");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
