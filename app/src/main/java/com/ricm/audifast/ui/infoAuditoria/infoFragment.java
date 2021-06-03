package com.ricm.audifast.ui.infoAuditoria;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ricm.audifast.R;
import com.ricm.audifast.SessionManager;
import com.ricm.audifast.entidades.AuditorAuxiliar;
import com.ricm.audifast.recyclerviewadapters.listaAuxiliaresAdapter;
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

public class  infoFragment extends Fragment {

    int id;
    String resultString;

    Button btnActa;

    TextView txtAuditoria;

    TextView txtOrganizacionNombre;
    TextView txtRFC;
    TextView txtGiro;
    TextView txtDireccionO;
    TextView txtDireccionF;

    TextView txtAuditorLiderNombre;
    TextView txtAuditorLiderCorreo;
    TextView txtAuditorLiderTelefono;

    public infoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        id = intent.getIntExtra("id",0);

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        txtAuditoria = (TextView) view.findViewById(R.id.txtAuditoria);
        txtAuditoria.setText("Auditoría "+id);

        txtOrganizacionNombre = (TextView) view.findViewById(R.id.txtOrganizacionNombre);
        txtRFC = (TextView) view.findViewById(R.id.txtRFC);
        txtGiro = (TextView) view.findViewById(R.id.txtGiro);
        txtDireccionO = (TextView) view.findViewById(R.id.txtDireccionO);
        txtDireccionF = (TextView) view.findViewById(R.id.txtDireccionF);

        txtAuditorLiderNombre = (TextView) view.findViewById(R.id.txtAuditorLiderNombre);
        txtAuditorLiderCorreo = (TextView) view.findViewById(R.id.txtAuditorLiderCorreo);
        txtAuditorLiderTelefono = (TextView) view.findViewById(R.id.txtauditorLiderTelefono);

        btnActa = (Button) view.findViewById(R.id.btnActa);

        btnActa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProcesosActa.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });


        ObtencionInfo obtencionInfo = new ObtencionInfo(id);
        obtencionInfo.start();

        return view;
    }

    private void llenarInfo() {
        try {
            JSONObject infoAuditoria = new JSONObject(resultString);

            JSONObject organizacion = infoAuditoria.getJSONObject("organizacion");
            JSONObject auditorLider = infoAuditoria.getJSONObject("auditorLider");

            txtOrganizacionNombre.setText(organizacion.getString("nombre"));
            txtRFC.setText(organizacion.getString("rfc"));
            txtGiro.setText(organizacion.getString("giro"));
            txtDireccionO.setText(organizacion.getString("direccion_operacion"));
            txtDireccionF.setText(organizacion.getString("direccion_fiscal"));

            txtAuditorLiderNombre.setText(auditorLider.getString("nombre"));
            txtAuditorLiderCorreo.setText(auditorLider.getString("correo"));
            txtAuditorLiderTelefono.setText(auditorLider.getString("telefono"));
        } catch (JSONException e) {
            Log.e("Error",e.getMessage());
            e.printStackTrace();
        }
    }

    private class ObtencionInfo extends Thread{
        int id;

        public ObtencionInfo(int id){
            this.id = id;
        }

        @Override
        public void run() {
            final String metodo = "infoAuditoria";
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
                Log.i("infoAuditoria",response);
            }catch (Exception e){
                Log.e("infoAuditoria-> Error",e.getMessage());
                response = "ERROR";
            }
            resultString = response;
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    llenarInfo();
                }
            });
        }
    }
}