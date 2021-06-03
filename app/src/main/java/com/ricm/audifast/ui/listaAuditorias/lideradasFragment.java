package com.ricm.audifast.ui.listaAuditorias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ricm.audifast.R;
import com.ricm.audifast.SessionManager;
import com.ricm.audifast.entidades.Auditoria;
import com.ricm.audifast.recyclerviewadapters.listaAuditoriasAdapter;

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

public class lideradasFragment extends Fragment {

    private String resultString;
    private List<Auditoria> listaLideradas;
    private RecyclerView recyclerLideradas;
    private listaAuditoriasAdapter adapter;

    public lideradasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaLideradas = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_lideradas, container, false);

        recyclerLideradas = (RecyclerView) view.findViewById(R.id.ReciclerViewLideradas);
        adapter = new listaAuditoriasAdapter(new ArrayList<Auditoria>(),true);
        recyclerLideradas.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerLideradas.setAdapter(adapter);

        SessionManager sm = new SessionManager(getActivity().getApplicationContext());
        String correo = sm.getUserEmail();

        ObtencionAuditorias obtencionAuditorias = new ObtencionAuditorias(correo);
        obtencionAuditorias.start();

        return view;
    }

    private void llenarLista() {
        try {
            JSONObject listas = new JSONObject(resultString);
            JSONArray lideradas = listas.getJSONArray("listaLideradas");
            for (int i = 0; i < lideradas.length(); i++){
                JSONObject auditoria = lideradas.getJSONObject(i);
                int id = auditoria.getInt("id");
                String correo_auditor_lider = auditoria.getString("correo_auditor_lider");
                String fecha_registro = auditoria.getString("fecha_registro");
                String organizacion = auditoria.getString("organizacion");
                listaLideradas.add(new Auditoria(id,correo_auditor_lider,organizacion,fecha_registro));
            }
        } catch (JSONException e) {
            Log.e("Error",e.getMessage());
            e.printStackTrace();
        }
    }

    private class ObtencionAuditorias extends Thread{
        String correo;

        public ObtencionAuditorias(String correo){
            this.correo = correo;
        }

        @Override
        public void run() {
            final String metodo = "listasAuditorias";
            final String namespace = "http://controlador/";
            final String accionSoap = namespace + metodo;
            final String url = "http://192.168.100.16:8080/AudiFast/AudiFastWS?wsdl";
            String response;

            SoapObject request = new SoapObject(namespace,metodo);
            request.addProperty("correo",correo);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(url,3000);
            try {
                ht.call(accionSoap,envelope);
                SoapPrimitive resp = (SoapPrimitive)envelope.getResponse();
                response = resp.toString();
                Log.i("listaAuditorias",response);
            }catch (Exception e){
                Log.e("listaAuditorias-> Error",e.getMessage());
                response = "ERROR";
            }
            resultString = response;
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    llenarLista();
                    adapter.updateList(listaLideradas);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}