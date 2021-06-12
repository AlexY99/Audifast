package com.ricm.audifast.ui.infoAuditoria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ricm.audifast.R;
import com.ricm.audifast.SessionManager;
import com.ricm.audifast.entidades.Auditoria;
import com.ricm.audifast.ui.listaAuditorias.ListaAuditorias;
import com.ricm.audifast.viewpageradapters.tabsApartadosAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class InfoAuditoria extends AppCompatActivity {
    int id;
    String resultString;

    TextView txtAuditoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_auditoria);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtAuditoria = (TextView) findViewById(R.id.txtAuditoria);
        txtAuditoria.setText("Auditoría "+id);

        TabLayout tabLayout = findViewById(R.id.tabsApartados);
        ViewPager2 viewPager2 = findViewById(R.id.pagerApartados);

        FragmentManager fm = getSupportFragmentManager();
        tabsApartadosAdapter adapter = new tabsApartadosAdapter(fm,getLifecycle(),resultString);
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Datos de Auditoría"));
        tabLayout.addTab(tabLayout.newTab().setText("Auditores Auxiliares"));
        tabLayout.addTab(tabLayout.newTab().setText("Contactos de Auditoría"));
        tabLayout.addTab(tabLayout.newTab().setText("Productos Auditados"));
        tabLayout.setTabMode(TabLayout.MODE_AUTO);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SessionManager sm = new SessionManager(getApplicationContext());
        switch(item.getItemId()){
            case R.id.itemLista:
                Intent intent = new Intent(this, ListaAuditorias.class);
                intent.putExtra("correo",sm.getUserEmail());
                startActivity(intent);
            break;

            case R.id.itemLogout:
                sm.logoutUser();
                finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}