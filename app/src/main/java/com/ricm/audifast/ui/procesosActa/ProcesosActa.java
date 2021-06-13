package com.ricm.audifast.ui.procesosActa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ricm.audifast.R;
import com.ricm.audifast.SessionManager;
import com.ricm.audifast.entidades.Proceso;
import com.ricm.audifast.recyclerviewadapters.listaProcesosAdapter;
import com.ricm.audifast.ui.infoAuditoria.InfoAuditoria;
import com.ricm.audifast.ui.listaAuditorias.ListaAuditorias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ProcesosActa extends AppCompatActivity {
    int idAuditoria;
    String correo;
    SessionManager sm;

    String resultString;
    String correo_lider;

    private List<Proceso> listaProcesos;
    private RecyclerView recyclerProcesos;
    private listaProcesosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesos_acta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaProcesos = new ArrayList<Proceso>();

        sm = new SessionManager(getApplicationContext());
        correo = sm.getUserEmail();

        Intent intent = this.getIntent();
        idAuditoria = intent.getIntExtra("id",0);
        correo_lider = intent.getStringExtra("correo_lider");

        recyclerProcesos = (RecyclerView) findViewById(R.id.ReciclerViewProcesos);
        adapter = new listaProcesosAdapter(new ArrayList<Proceso>(),correo,idAuditoria,correo_lider);
        recyclerProcesos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerProcesos.setAdapter(adapter);

        ObtencionProcesos obtencionProcesos = new ObtencionProcesos(idAuditoria);
        obtencionProcesos.start();
    }

    private void llenarLista() {
        try {
            JSONArray procesos = new JSONArray(resultString);
            if(procesos!=null || procesos.length()>0)
                for (int i = 0; i < procesos.length(); i++){
                    JSONObject proceso = procesos.getJSONObject(i);
                    int id = proceso.getInt("id");
                    String descripcion = proceso.getString("descripcion");
                    double ponderacion = proceso.getDouble("ponderacion");
                    String encargado = proceso.getString("encargado");
                    boolean evaluado = proceso.getBoolean("evaluado");
                    String correo_encargado = proceso.getString("correo_encargado");
                    String observaciones = proceso.getString("observaciones");
                    double resultado = proceso.getDouble("resultado");
                    listaProcesos.add(new Proceso(id,descripcion,ponderacion,encargado,correo_encargado,evaluado,observaciones,resultado));
                }
        } catch (JSONException e) {
            Log.e("Error",e.getMessage());
            e.printStackTrace();
        }
    }

    private class ObtencionProcesos extends Thread{
        int id;

        public ObtencionProcesos(int id){
            this.id = id;
        }

        @Override
        public void run() {
            final String metodo = "procesosActaAuditoria";
            final String namespace = "http://controlador/";
            final String accionSoap = namespace + metodo;
            final String url = "http://192.168.100.53:8083/AudiFast/AudiFastWS?wsdl";
            String response;

            SoapObject request = new SoapObject(namespace,metodo);
            request.addProperty("id",id);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(url,3000);
            try {
                ht.call(accionSoap,envelope);
                SoapPrimitive resp = (SoapPrimitive)envelope.getResponse();
                response = resp.toString();
                Log.i("procesosActaAuditoria",response);
            }catch (Exception e){
                Log.e("procesosActa->Error",e.getMessage());
                response = "ERROR";
            }
            resultString = response;
            runOnUiThread(new Runnable() {
                public void run() {
                    llenarLista();
                    if(listaProcesos.size()>0){
                        adapter.updateList(listaProcesos);
                        adapter.notifyDataSetChanged();
                    }else{
                        noProcesos();
                    }

                }
            });
        }
    }

    public void noProcesos(){
        Toast.makeText(getApplicationContext(),"No se ha asignado acta de auditoria",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, InfoAuditoria.class);
        intent.putExtra("id", idAuditoria);
        startActivity(intent);
        finish();
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