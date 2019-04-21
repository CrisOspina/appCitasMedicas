package com.example.appcitas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{
    EditText etCorreo, etClave;
    Button btnIngresar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        etClave = findViewById(R.id.etClave);
        btnIngresar = findViewById(R.id.btnIngresar);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    /* ********************************************************** */

    //Ingresar
    public void ingresar(View view)
    {
        final String correo = etCorreo.getText().toString();
        String clave  = etClave.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.setError("Debe ingresar un email valido");
            return;
        }
        if (clave.length() < 6)
        {
            etClave.setError("La contraseña debe tener minimo 6 caracteres");
            return;
        }

        //Login Firebase - correo y clave
        mAuth.signInWithEmailAndPassword(correo, clave)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                            Intent lo_intent = new Intent(getApplicationContext(),AsignacionCitaActivity.class);
                            lo_intent.putExtra("correo", correo);
                            startActivity(lo_intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Email no existe, verifica", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
