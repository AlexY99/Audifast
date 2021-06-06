package com.ricm.audifast.ui.requisitosProceso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ricm.audifast.R;
import com.ricm.audifast.SessionManager;
import com.ricm.audifast.entidades.Requisito;
import com.ricm.audifast.recyclerviewadapters.listaRequisitosAdapter;
import com.ricm.audifast.ui.infoAuditoria.InfoAuditoria;
import com.ricm.audifast.ui.procesosActa.ProcesosActa;

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

public class RequisitosProceso extends AppCompatActivity {
    int idProceso;
    int idAuditoria;
    String observacionesProceso;
    SessionManager sm;

    Button btnLogout;
    Button btnActa;

    EditText txtObservacionesProceso;

    String resultString;

    private List<Requisito> listaRequisitos;
    private RecyclerView recyclerRequisitos;
    private listaRequisitosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisitos_proceso);

        listaRequisitos = new ArrayList<Requisito>();

        sm = new SessionManager(getApplicationContext());

        Intent intent = this.getIntent();
        idProceso = intent.getIntExtra("idProceso",0);
        idAuditoria = intent.getIntExtra("idAuditoria",0);
        observacionesProceso = intent.getStringExtra("observacionesProceso");


        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnActa = (Button) findViewById(R.id.btnEvaluar);

        txtObservacionesProceso = (EditText) findViewById(R.id.txtObservacionesProceso);
        txtObservacionesProceso.setText(observacionesProceso);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm.logoutUser();
            }
        });

        recyclerRequisitos = (RecyclerView) findViewById(R.id.ReciclerViewRequisitos);
        adapter = new listaRequisitosAdapter(new ArrayList<Requisito>());
        recyclerRequisitos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerRequisitos.setAdapter(adapter);

        txtObservacionesProceso.setVisibility(View.GONE);
        btnActa.setVisibility(View.GONE);

        ObtencionProcesos obtencionProcesos = new ObtencionProcesos(idProceso);
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
                    String clave_norma = proceso.getString("clave_norma");
                    int cumplimiento = proceso.getInt("cumplimiento");
                    listaRequisitos.add(new Requisito(id,descripcion,clave_norma,cumplimiento));
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
            final String metodo = "requisitosProcesoActa";
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
                Log.i("requisitosProcesoActa",response);
            }catch (Exception e){
                Log.e("requisitosPActa->Error",e.getMessage());
                response = "ERROR";
            }
            resultString = response;
            runOnUiThread(new Runnable() {
                public void run() {
                    llenarLista();
                    if(listaRequisitos.size()>0){
                        adapter.updateList(listaRequisitos);
                        adapter.notifyDataSetChanged();
                    }else{
                        noRequisitos();
                    }

                    btnActa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listaRequisitos = adapter.getRequisitoList();
                            String jsonResp = "{procesoEvaluacion:{\"id\":" +idProceso+",";
                            jsonResp += "\"observaciones\":\""+txtObservacionesProceso.getText().toString()+"\"},";
                            jsonResp += "requisitosEvaluacion:[";
                            for(Requisito req : listaRequisitos){
                                jsonResp += "{\"id\":" + req.getId()+",";
                                jsonResp += "\"cumplimiento\":" + req.getCumplimiento()+"},";
                            }
                            jsonResp = jsonResp.substring(0,jsonResp.length()-1);
                            jsonResp+="]}";
                            Log.i("evaluacionJSON", jsonResp);

                            EvaluacionProceso evaluacionProceso = new EvaluacionProceso(jsonResp);
                            evaluacionProceso.start();
                        }
                    });

                    txtObservacionesProceso.setVisibility(View.VISIBLE);
                    btnActa.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private class EvaluacionProceso extends Thread{
        String jsonResp;

        public EvaluacionProceso(String jsonResp){
            this.jsonResp = jsonResp;
        }

        @Override
        public void run() {
            final String metodo = "evaluacionProceso";
            final String namespace = "http://controlador/";
            final String accionSoap = namespace + metodo;
            final String url = "http://192.168.100.16:8080/AudiFast/AudiFastWS?wsdl";
            String response;

            SoapObject request = new SoapObject(namespace,metodo);
            request.addProperty("json",jsonResp);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(url,3000);
            try {
                ht.call(accionSoap,envelope);
                SoapPrimitive resp = (SoapPrimitive)envelope.getResponse();
                response = resp.toString();
                Log.i("evaluacionProceso",response);
            }catch (Exception e){
                Log.e("evaluacionP->Error",e.getMessage());
                response = "ERROR";
            }
            resultString = response;
            runOnUiThread(new Runnable() {
                public void run() {
                    evaluacionExitosa();
                }
            });
        }
    }

    public void noRequisitos(){
        Toast.makeText(getApplicationContext(),"No se han asignado requisitos al proceso",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ProcesosActa.class);
        intent.putExtra("id", idAuditoria);
        startActivity(intent);
        finish();
    }

    public void evaluacionExitosa(){
        Toast.makeText(getApplicationContext(),"Proceso Evaluado",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ProcesosActa.class);
        intent.putExtra("id", idAuditoria);
        startActivity(intent);
        finish();
    }


}