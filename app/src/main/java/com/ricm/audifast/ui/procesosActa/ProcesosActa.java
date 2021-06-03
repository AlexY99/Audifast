package com.ricm.audifast.ui.procesosActa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ricm.audifast.R;
import com.ricm.audifast.SessionManager;
import com.ricm.audifast.entidades.Proceso;
import com.ricm.audifast.entidades.Producto;
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
    int id;
    String correo;
    SessionManager sm;

    Button btnLogout;

    String resultString;

    private List<Proceso> listaProcesos;
    private RecyclerView recyclerProcesos;
    private listaProcesosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procesos_acta);

        listaProcesos = new ArrayList<Proceso>();

        sm = new SessionManager(getApplicationContext());
        correo = sm.getUserEmail();

        Intent intent = this.getIntent();
        id = intent.getIntExtra("id",0);

        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.logoutUser();
            }
        });

        recyclerProcesos = (RecyclerView) findViewById(R.id.ReciclerViewProcesos);
        adapter = new listaProcesosAdapter(new ArrayList<Proceso>(),correo);
        recyclerProcesos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerProcesos.setAdapter(adapter);

        ObtencionProcesos obtencionProcesos = new ObtencionProcesos(id);
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
                    String correo_encargado = proceso.getString("correo_encargado");
                    listaProcesos.add(new Proceso(id,descripcion,ponderacion,encargado,correo_encargado));
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
            final String url = "http://192.168.100.16:8080/AudiFast/AudiFastWS?wsdl";
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
        intent.putExtra("id",id);
        startActivity(intent);
        finish();
    }
}