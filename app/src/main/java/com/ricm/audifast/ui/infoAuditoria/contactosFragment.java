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
import com.ricm.audifast.entidades.Contacto;
import com.ricm.audifast.recyclerviewadapters.listaContactosAdapter;
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

public class contactosFragment extends Fragment {
    private static final String ARG_JSON_APARTADOS = "param1";

    int id;
    private String resultString;
    private List<Contacto> listaContactos;
    private RecyclerView recyclerContactos;
    private listaContactosAdapter adapter;

    public contactosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaContactos = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        id = intent.getIntExtra("id",0);

        View view = inflater.inflate(R.layout.fragment_lista_contactos, container, false);

        recyclerContactos = (RecyclerView) view.findViewById(R.id.ReciclerViewContactos);
        adapter = new listaContactosAdapter(new ArrayList<Contacto>());
        recyclerContactos.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerContactos.setAdapter(adapter);

        ObtencionContactos obtencionContactos = new ObtencionContactos(id);
        obtencionContactos.start();

        return view;
    }

    private void llenarLista() {
        try {
            JSONArray contactos = new JSONArray(resultString);
            if(contactos!=null)
                for (int i = 0; i < contactos.length(); i++){
                    JSONObject contacto = contactos.getJSONObject(i);
                    String correo = contacto.getString("correo");
                    String nombre = contacto.getString("nombre");
                    String puesto = contacto.getString("puesto");
                    String telefono = contacto.getString("telefono");
                    listaContactos.add(new Contacto(correo,nombre,puesto,telefono));
                }
        } catch (JSONException e) {
            Log.e("Error",e.getMessage());
            e.printStackTrace();
        }
    }

    private class ObtencionContactos extends Thread{
        int id;

        public ObtencionContactos(int id){
            this.id = id;
        }

        @Override
        public void run() {
            final String metodo = "contactosAuditoria";
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
                Log.i("infoAuditoriaCont",response);
            }catch (Exception e){
                Log.e("infoAuditoria-> Error",e.getMessage());
                response = "ERROR";
            }
            resultString = response;
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    llenarLista();
                    adapter.updateList(listaContactos);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

}