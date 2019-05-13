package com.example.appcitas;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appcitas.fragments.LoginFragment;
import com.example.appcitas.fragments.RegistroFragment;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, RegistroFragment.OnFragmentInteractionListener
{
    Button btnLogin, btnRegistrar;
    LoginFragment loginFragment;
    RegistroFragment registroFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  btnLogin       = findViewById(R.id.btnIngresar);
        btnRegistrarse = findViewById(R.id.btnRegistrar);*/

        loginFragment = new LoginFragment();
        registroFragment = new RegistroFragment();

        btnLogin = findViewById(R.id.btnLogin);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutContainer, loginFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }

    public void cambiarFragment(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.btnLogin:
                fragmentTransaction.replace(R.id.frameLayoutContainer,loginFragment).commit();
                break;
            case R.id.btnRegistrar:
                fragmentTransaction.replace(R.id.frameLayoutContainer,registroFragment).commit();
                break;
        }
    }


    //Registrar
   /* public void registrar(View view)
    {
        Intent ob_intent = new Intent(MainActivity.this, RegistroActivity.class);
        startActivity(ob_intent);
    }*/

    /* ********************************************************** */

    //Login
  /*  public void login(View view)
    {
        Intent ob_intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(ob_intent);
    }*/
    /* ********************************************************** */



}