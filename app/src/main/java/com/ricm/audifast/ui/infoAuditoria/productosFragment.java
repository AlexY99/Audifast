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
import com.ricm.audifast.entidades.Producto;
import com.ricm.audifast.recyclerviewadapters.listaProductosAdapter;
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

public class productosFragment extends Fragment {

    private static final String ARG_JSON_APARTADOS = "param1";

    int id;
    private String resultString;
    private List<Producto> listaProductos;
    private RecyclerView recyclerProductos;
    private listaProductosAdapter adapter;

    public productosFragment() {
        // Required empty public constructor
    }

    public static productosFragment newInstance(String json) {
        productosFragment fragment = new productosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JSON_APARTADOS, json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaProductos = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        id = intent.getIntExtra("id",0);

        View view = inflater.inflate(R.layout.fragment_lista_productos, container, false);

        recyclerProductos = (RecyclerView) view.findViewById(R.id.ReciclerViewProductos);
        adapter = new listaProductosAdapter(new ArrayList<Producto>());
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerProductos.setAdapter(adapter);

        ObtencionProductos obtencionProductos = new ObtencionProductos(id);
        obtencionProductos.start();

        return view;
    }

    private void llenarLista() {
        try {
            JSONArray productos = new JSONArray(resultString);
            if(productos!=null)
                for (int i = 0; i < productos.length(); i++){
                    JSONObject producto = productos.getJSONObject(i);
                    String clave = producto.getString("clave");
                    String nombre = producto.getString("nombre");
                    listaProductos.add(new Producto(clave,nombre));
                }
        } catch (JSONException e) {
            Log.e("Error",e.getMessage());
            e.printStackTrace();
        }
    }

    private class ObtencionProductos extends Thread{
        int id;

        public ObtencionProductos(int id){
            this.id = id;
        }

        @Override
        public void run() {
            final String metodo = "productosAuditoria";
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
                Log.i("infoAuditoriaProd",response);
            }catch (Exception e){
                Log.e("infoAuditoria-> Error",e.getMessage());
                response = "ERROR";
            }
            resultString = response;
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    llenarLista();
                    adapter.updateList(listaProductos);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}