package br.com.nanothings.wildmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.model.Aposta;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalheApostaActivity extends AppCompatActivity {
    @BindView(R.id.fabCancelar) FloatingActionButton fabCancelar;

    Aposta aposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_aposta);

        ButterKnife.bind(this);

        aposta = (Aposta) getIntent().getSerializableExtra("Aposta");
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
            Toast.makeText(this, "Imprimindo " + aposta.getCodigo(), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            finish();
            return true;
        }
    }
}
