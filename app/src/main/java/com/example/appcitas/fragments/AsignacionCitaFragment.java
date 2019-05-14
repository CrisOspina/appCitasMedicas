package com.example.appcitas.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcitas.AsignacionCitaActivity;
import com.example.appcitas.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AsignacionCitaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AsignacionCitaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsignacionCitaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db;
    TextView tvCorreo, tvCedula;
    Spinner spEspecialista, spEps;
    EditText etFecha;
    DatePickerDialog datePickerDialog;
    Button btnAsignarC, btnAsignar;
    View view;

    private OnFragmentInteractionListener mListener;

    public AsignacionCitaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AsignacionCitaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AsignacionCitaFragment newInstance(String param1, String param2) {
        AsignacionCitaFragment fragment = new AsignacionCitaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_asignacion_cita, container, false);
        etFecha = view.findViewById(R.id.etFecha);
        tvCedula = view.findViewById(R.id.tvCedula);
        tvCorreo = view.findViewById(R.id.tvCorreo);
        btnAsignar = view.findViewById(R.id.btnAsignar);
        btnAsignarC = view.findViewById(R.id.btnAsignarC);
        db = FirebaseFirestore.getInstance();

        btnAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recibiendo correo de login
                recibirCorreo();

                //Recibiendo cedula de registro
                recibirCedula();

                //Lista de especialistas
                spinnerEspecialista();

                //Lista de EPS
                spinnerEPS();

            }
            //Recibir correo
            public void recibirCorreo()
            {
                Bundle extra  = getActivity().getIntent().getExtras();
                String correo = extra.getString("correo");
                tvCorreo      = view.findViewById(R.id.tvCorreo);
                tvCorreo.setText(correo);
            }

            //Recibir cedula
            public void recibirCedula()
            {
                Bundle extra  = getActivity().getIntent().getExtras();
                String cedula = extra.getString("cedula");
                tvCedula      = view.findViewById(R.id.tvCedula);
                tvCedula.setText(cedula);
            }

            //Spinner para los especialistas y medicos
            public void spinnerEspecialista()
            {
                spEspecialista = view.findViewById(R.id.spEspecialista);

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
                ArrayAdapter lo_adp_tipos = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, lo_tipos);
                spEspecialista.setAdapter(lo_adp_tipos);

                //Identificar cuando es presionado en alguno de los elementos.
                spEspecialista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Marca posición sobre que elemento hemos seleccionado.
                        //String lo_tipos = (String) spEspecialista.getAdapter().getItem(position);
                        //Mostrar mensaje
                        //Toast.makeText(Main4Activity_registros.this, "Seleccionaste: " + lo_tipos, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            //Spinner para las EPS
            public void spinnerEPS()
            {
                spEps = view.findViewById(R.id.spEps);

                //Spinner EPS
                final ArrayList<String> lo_tiposE = new ArrayList<>();

                //Inserto datos para elegir.
                lo_tiposE.add("Seleciona EPS");
                lo_tiposE.add("SURA");
                lo_tiposE.add("Sisben");
                lo_tiposE.add("Salud total");

                //Se crea adaptador
                ArrayAdapter lo_adp_tiposE = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, lo_tiposE);
                spEps.setAdapter(lo_adp_tiposE);

                //Identificar cuando es presionado en alguno de los elementos.
                spEps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Marca posición sobre que elemento hemos seleccionado.
                        //String lo_tiposE = (String) spEps.getAdapter().getItem(position);
                        //Mostrar mensaje
                        //Toast.makeText(Main4Activity_registros.this, "Seleccionaste: " + lo_tipos, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }


        });

        //Calendario para elegir la fecha de la cita
        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar ob_calendar = Calendar.getInstance();
                final int year = ob_calendar.get(Calendar.YEAR);
                final int month = ob_calendar.get(Calendar.MONTH);
                final int day = ob_calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(
                        getContext(),R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etFecha.setText(day + " del " +  month + " de " + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //Al presionar el botón verifica y realizara la asignación de la cita
        btnAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = etFecha.getText().toString();
                String correo = tvCorreo.getText().toString();
                String cedula = tvCedula.getText().toString();

                if (fecha.isEmpty())
                {
                    Toast.makeText(getContext(), "Elegir fecha", Toast.LENGTH_SHORT).show();
                }

                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();

                //Traer del registro anterior el correo y la cédula
                //Bundle extra  = getIntent().getExtras();
                //String correo = extra.getString("correo");
                //String cedula = extra.getString("cedula");
                //tvCorreo      = view.findViewById(R.id.tvCorreo); tvCorreo.setText(correo);
                //tvCedula      = view.findViewById(R.id.tvCedula); tvCedula.setText(cedula);

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

                                Toast.makeText(getContext(), "Cita registrada", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("err", "Error adding document", e);
                                Toast.makeText(getContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Inflate the layout for this fragment
            return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
