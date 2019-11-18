package br.com.nanothings.wildmobile.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.widget.TextView;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.fragments.ListaApostaFragment;
import br.com.nanothings.wildmobile.fragments.ApuracaoFragment;
import br.com.nanothings.wildmobile.fragments.InicioFragment;
import br.com.nanothings.wildmobile.fragments.ListaSorteioFragment;
import br.com.nanothings.wildmobile.helper.PreferenceManager;
import br.com.nanothings.wildmobile.model.Cambista;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    private Cambista cambista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        this.setNavigationDrawer();
        this.sincronizarTextosNav();

        fragmentInflater(new InicioFragment());
    }

    private void setNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.app_bar_dropdown_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            fragmentInflater(new InicioFragment());
        } else if (id == R.id.nav_apostas) {
            fragmentInflater(new ListaApostaFragment());
        } else if (id == R.id.nav_apuracao) {
            fragmentInflater(new ApuracaoFragment());
        } else if(id == R.id.nav_resultado) {
            fragmentInflater(new ListaSorteioFragment());
        }
        else if (id == R.id.nav_sair) {
            encerrarSessao();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void fragmentInflater(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
    }

    private void sincronizarTextosNav() {
        cambista = (Cambista) new PreferenceManager(this, Cambista.class).getPreference("Cambista");
        TextView nomeCambistaNav = navigationView.getHeaderView(0).findViewById(R.id.nome_cambista_nav);
        nomeCambistaNav.setText(cambista.getNome());
    }

    private void encerrarSessao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.warning)
                .setMessage(R.string.logoff_confirmation)
                .setCancelable(false)
                .setNegativeButton(R.string.decline, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                })
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new PreferenceManager(getApplicationContext()).clearPreferences();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                }).create().show();
    }
}
