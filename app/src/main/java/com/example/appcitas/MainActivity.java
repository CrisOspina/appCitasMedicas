package com.example.appcitas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button btnLogin, btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin       = findViewById(R.id.btnIngresar);
        btnRegistrarse = findViewById(R.id.btnRegistrar);

    }

    //Registrar
    public void registrar(View view)
    {
        Intent ob_intent = new Intent(MainActivity.this, RegistroActivity.class);
        startActivity(ob_intent);
    }

    /* ********************************************************** */

    //Login
    public void login(View view)
    {
        Intent ob_intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(ob_intent);
    }

    /* ********************************************************** */
}
