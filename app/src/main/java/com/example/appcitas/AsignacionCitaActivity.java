package com.example.appcitas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcitas.fragments.AsignacionCitaFragment;
import com.example.appcitas.fragments.ConsultarCitaFragment;
import com.example.appcitas.fragments.LoginFragment;
import com.example.appcitas.fragments.RegistroFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AsignacionCitaActivity extends AppCompatActivity implements AsignacionCitaFragment.OnFragmentInteractionListener, ConsultarCitaFragment.OnFragmentInteractionListener
{
    /*
    TextView tvCorreo, tvCedula;
    Spinner spEspecialista, spEps;
    EditText etFecha;
    DatePickerDialog datePickerDialog;
    FirebaseFirestore db;
    */

    Button btnAsignarC, btnConsultarC;
    AsignacionCitaFragment AsignacionCitaFragment;
    ConsultarCitaFragment ConsultarCitaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion_cita);

        AsignacionCitaFragment = new AsignacionCitaFragment();
        ConsultarCitaFragment = new ConsultarCitaFragment();

        btnAsignarC = findViewById(R.id.btnAsignarC);
        btnConsultarC = findViewById(R.id.btnConsultarC);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutContainer2, AsignacionCitaFragment).commit();

        /*
        etFecha = findViewById(R.id.etFecha);
        db = FirebaseFirestore.getInstance();

        //Recibiendo correo de login
        recibirCorreo();

        //Recibiendo cedula de registro
        recibirCedula();

        //Lista de especialistas
        spinnerEspecialista();

        //Lista de EPS
        spinnerEPS();*/
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void cambiarFragments(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.btnAsignarC:
                fragmentTransaction.replace(R.id.frameLayoutContainer2,AsignacionCitaFragment).commit();
                break;
            case R.id.btnConsultarC:
                fragmentTransaction.replace(R.id.frameLayoutContainer2,ConsultarCitaFragment).commit();
                break;
        }
    }




    /*
    //Recibir correo
    public void recibirCorreo()
    {
        Bundle extra  = getIntent().getExtras();
        String correo = extra.getString("correo");
        tvCorreo      = findViewById(R.id.tvCorreo);
        tvCorreo.setText(correo);
    }

    //Recibir cedula
    public void recibirCedula()
    {
        Bundle extra  = getIntent().getExtras();
        String cedula = extra.getString("cedula");
        tvCedula      = findViewById(R.id.tvCedula);
        tvCedula.setText(cedula);
    }

    //Spinner para los especialistas y medicos
    public  void spinnerEspecialista()
    {

        spEspecialista = findViewById(R.id.spEspecialista);

        //Spinner especialista
        final ArrayList<String> lo_tipos = new ArrayList<>();

        //Inserto datos para elegir.
        lo_tipos.add("Selecciona especialista");
        lo_tipos.add("General");
        lo_tipos.add("Pediatra");
        lo_tipos.add("Psiquiatra");
        lo_tipos.add("Cirujano");
        lo_tipos.add("Ortopedista");

        //Se crea adaptador
        ArrayAdapter lo_adp_tipos = new ArrayAdapter(AsignacionCitaActivity.this, android.R.layout.simple_spinner_dropdown_item, lo_tipos);
        spEspecialista.setAdapter(lo_adp_tipos);

        //Identificar cuando es presionado en alguno de los elementos.
        spEspecialista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Marca posición sobre que elemento hemos seleccionado.
                String lo_tipos = (String) spEspecialista.getAdapter().getItem(position);
                //Mostrar mensaje
                //Toast.makeText(Main4Activity_registros.this, "Seleccionaste: " + lo_tipos, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Spinner para las EPS
    public  void spinnerEPS()
    {
        spEps = findViewById(R.id.spEps);

        //Spinner EPS
        final ArrayList<String> lo_tiposE = new ArrayList<>();

        //Inserto datos para elegir.
        lo_tiposE.add("Seleciona EPS");
        lo_tiposE.add("SURA");
        lo_tiposE.add("Sisben");
        lo_tiposE.add("Salud total");

        //Se crea adaptador
        ArrayAdapter lo_adp_tiposE = new ArrayAdapter(AsignacionCitaActivity.this, android.R.layout.simple_spinner_dropdown_item, lo_tiposE);
        spEps.setAdapter(lo_adp_tiposE);

        //Identificar cuando es presionado en alguno de los elementos.
        spEps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Marca posición sobre que elemento hemos seleccionado.
                String lo_tiposE = (String) spEps.getAdapter().getItem(position);
                //Mostrar mensaje
                //Toast.makeText(Main4Activity_registros.this, "Seleccionaste: " + lo_tipos, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    //Calendario para elegir la fecha de la cita
    public void datePicker(View view)
    {
        Calendar ob_calendar = Calendar.getInstance();
        int year = ob_calendar.get(Calendar.YEAR);
        int month = ob_calendar.get(Calendar.MONTH);
        final int day = ob_calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(
                AsignacionCitaActivity.this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etFecha.setText(day + " del " +  month + " de " + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    //Al presionar el botón verifica y realizara la asignación de la cita
    public void asignar(View view)
    {
        String fecha = etFecha.getText().toString();

        if (fecha.isEmpty())
        {
            Toast.makeText(AsignacionCitaActivity.this, "Elegir fecha", Toast.LENGTH_SHORT).show();
        }

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();

        //Traer del registro anterior el correo y la cédula
        Bundle extra  = getIntent().getExtras();
        String correo = extra.getString("correo");
        String cedula = extra.getString("cedula");
        tvCorreo      = findViewById(R.id.tvCorreo); tvCorreo.setText(correo);
        tvCedula      = findViewById(R.id.tvCedula); tvCedula.setText(cedula);

        //Registrar en la BD firebase - colección Citas
        user.put("Fecha", fecha);
        user.put("Correo", correo);
        user.put("Cedula", cedula);

        // Add a new document with a generated ID
        db.collection("Citas")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("er", "DocumentSnapshot added with ID: " + documentReference.getId());

                        Toast.makeText(AsignacionCitaActivity.this, "Cita registrada", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("err", "Error adding document", e);
                        Toast.makeText(AsignacionCitaActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/
}
