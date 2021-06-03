package com.ricm.audifast.ui.infoAuditoria;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.ricm.audifast.R;
import com.ricm.audifast.entidades.AuditorAuxiliar;
import com.ricm.audifast.recyclerviewadapters.listaAuxiliaresAdapter;
import com.ricm.audifast.viewpageradapters.tabsApartadosAdapter;

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

public class auxiliaresFragment extends Fragment {

    int id;
    private String resultString;
    private List<AuditorAuxiliar> listaAuxiliares;
    private RecyclerView recyclerAuxiliares;
    private listaAuxiliaresAdapter adapter;

    public auxiliaresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listaAuxiliares = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        id = intent.getIntExtra("id",0);

        View view = inflater.inflate(R.layout.fragment_lista_auxiliares, container, false);

        recyclerAuxiliares = (RecyclerView) view.findViewById(R.id.ReciclerViewAuxiliares);
        adapter = new listaAuxiliaresAdapter(new ArrayList<AuditorAuxiliar>());
        recyclerAuxiliares.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerAuxiliares.setAdapter(adapter);

        ObtencionAuxiliares obtencionAuxiliares = new ObtencionAuxiliares(id);
        obtencionAuxiliares.start();

        return view;
    }

    private void llenarLista() {
        try {
            JSONArray auxiliares = new JSONArray(resultString);
            if(auxiliares!=null)
                for (int i = 0; i < auxiliares.length(); i++){
                    JSONObject auxiliar = auxiliares.getJSONObject(i);
                    String correo = auxiliar.getString("correo");
                    String nombre = auxiliar.getString("nombre");
                    String telefono = auxiliar.getString("telefono");
                    listaAuxiliares.add(new AuditorAuxiliar(correo,nombre,telefono));
                }
        } catch (JSONException e) {
            Log.e("Error",e.getMessage());
            e.printStackTrace();
        }
    }

    private class ObtencionAuxiliares extends Thread{
        int id;

        public ObtencionAuxiliares(int id){
            this.id = id;
        }

        @Override
        public void run() {
            final String metodo = "auxiliaresAuditoria";
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
                Log.i("infoAuditoriaAux",response);
            }catch (Exception e){
                Log.e("infoAuditoria-> Error",e.getMessage());
                response = "ERROR";
            }
            resultString = response;
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    llenarLista();
                    adapter.updateList(listaAuxiliares);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}