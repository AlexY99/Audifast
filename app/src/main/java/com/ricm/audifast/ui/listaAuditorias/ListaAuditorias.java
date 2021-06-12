package com.ricm.audifast.ui.listaAuditorias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.ricm.audifast.R;
import com.ricm.audifast.SessionManager;
import com.ricm.audifast.viewpageradapters.tabsAuditoriasAdapter;

public class ListaAuditorias extends AppCompatActivity {
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        correo = intent.getStringExtra("correo");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_auditorias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);

        FragmentManager fm = getSupportFragmentManager();
        tabsAuditoriasAdapter adapter = new tabsAuditoriasAdapter(fm,getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Lista de Auditorías Lideradas"));
        tabLayout.addTab(tabLayout.newTab().setText("Lista de Auditorías Auxiliadas"));

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
            case R.id.itemLogout:
                sm.logoutUser();
                finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

}