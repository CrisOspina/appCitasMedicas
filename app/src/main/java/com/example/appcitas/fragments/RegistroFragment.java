package com.example.appcitas.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appcitas.AsignacionCitaActivity;
import com.example.appcitas.R;
import com.example.appcitas.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
     Button btnFragmentRegistro;
    EditText etCedula, etCorreo, etNombre, etClave;
    Button btnRegistro;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
     View view;



    private OnFragmentInteractionListener mListener;

    public RegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
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

        view = inflater.inflate(R.layout.fragment_registro, container, false);
        etCedula = view.findViewById(R.id.etCedula);
        etCorreo = view.findViewById(R.id.etCorreo);
        etNombre =  view.findViewById(R.id.etNombre);
        etClave =  view.findViewById(R.id.etClave);
        btnRegistro =  view.findViewById(R.id.btnRegistro);
        mAuth = FirebaseAuth.getInstance();
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
       // btnFragmentRegistro = view.findViewById(R.id.btnRegistrar);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String cedula = etCedula.getText().toString();
                final String correo = etCorreo.getText().toString();
                final String nombre = etNombre.getText().toString();
                final String clave  = etClave.getText().toString();

                //Validar campos para el registro
                if (cedula.isEmpty()){
                    Toast.makeText(getContext(), "Digita tu identificación", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nombre.isEmpty()){
                    Toast.makeText(getContext(), "Digita tu nombre", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    etCorreo.setError("Debe ingresar un email valido");
                    return;
                }
                if (clave.length() < 6)
                {
                    etClave.setError("La contraseña debe tener minimo 6 caracteres");
                    return;
                }

                //Crear usuario nuevo autenticacion firebase - correo, clave
                mAuth.createUserWithEmailAndPassword(correo, clave)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Registro de usuarios en FIREBASE.
                                    registrarUsuarios(cedula, nombre, correo, clave);
                                }
                                else {
                                    Toast.makeText(getContext(), "No registrado, tu correo ya existe", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            //Registrar en la BD firebase
            public void registrarUsuarios(final String cedula, String nombre, final String correo, String clave)
            {
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();

                //Registrar en la BD firebase
                user.put("Cedula", cedula);
                user.put("Nombre", nombre);
                user.put("Correo", correo);
                user.put("Clave", clave);

                // Add a new document with a generated ID
                db.collection("Registros")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("er", "DocumentSnapshot added with ID: " + documentReference.getId());

                                Toast.makeText(getContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                Intent lo_intent = new Intent(getContext(), AsignacionCitaActivity.class);
                                //Enviar variables para mostrar en la asignación de citas.
                                lo_intent.putExtra("cedula", cedula);
                                lo_intent.putExtra("correo", correo);
                                startActivity(lo_intent);

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
