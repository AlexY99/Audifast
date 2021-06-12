 package com.ricm.audifast.ui;

import androidx.appcompat.app.AppCompatActivity;

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
import com.ricm.audifast.ui.listaAuditorias.ListaAuditorias;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

 public class MainActivity extends AppCompatActivity {

    private EditText txtCorreo;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView lblResult;

    String correo, password;
    String resultString;

    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = new SessionManager(getApplicationContext());

        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        lblResult = (TextView) findViewById(R.id.lblResult);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = txtCorreo.getText().toString();
                password = txtPassword.getText().toString();

                lblResult.setText("Cargando...");
                lblResult.setVisibility(View.VISIBLE);

                try{
                    InicioSesion inicioSesion = new InicioSesion(v);
                    inicioSesion.start();
                }catch (Exception e){
                    Log.e("Login","Ocurrió un error inesperado");
                    e.printStackTrace();
                    lblResult.setText("Ocurrió un error inesperado");
                }

            }
        });

        if (sm.isLoggedIn()){
            Intent intent = new Intent(this, ListaAuditorias.class);
            startActivity(intent);
            this.finish();
        }
    }

    private class InicioSesion extends Thread{
        View v;

        public InicioSesion(View v){
            this.v = v;
        }

        @Override
        public void run() {
            final String metodo = "iniciarSesion";
            final String namespace = "http://controlador/";
            final String accionSoap = namespace + metodo;
            final String url = "http://192.168.100.16:8080/AudiFast/AudiFastWS?wsdl";
            String response = "LOCAL INVALID";

            SoapObject request = new SoapObject(namespace,metodo);
            request.addProperty("correo",correo);
            request.addProperty("password",password);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(url,3000);
            try {
                ht.call(accionSoap,envelope);
                SoapPrimitive resp = (SoapPrimitive)envelope.getResponse();
                response = resp.toString();
                Log.i("Login Response",response);
            }catch (Exception e){
                Log.e("Login Error",e.getMessage());
                response = "ERROR";
            }
            resultString = response;
            runOnUiThread(new Runnable() {
                public void run() {
                    if(resultString.equals("INVALID")) {
                        lblResult.setText("Usuario/Contraseña Incorrecto");
                        lblResult.setVisibility(View.VISIBLE);
                    }else if(resultString.equals("ERROR")){
                        lblResult.setText("Ocurrió un error inesperado");
                        lblResult.setVisibility(View.VISIBLE);
                    }else {
                        lblResult.setVisibility(View.GONE);
                        listasAuditorias(v);
                    }
                }
            });
        }
    }

    private void listasAuditorias(View view){
        Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
        sm.createLoginSession(correo);
        Intent intent = new Intent(this,ListaAuditorias.class);
        intent.putExtra("correo",correo);
        startActivity(intent);
        finish();
    }

}