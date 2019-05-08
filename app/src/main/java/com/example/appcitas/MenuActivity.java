package com.example.appcitas;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.appcitas.fragments.InicioFragment;

public class MenuActivity extends AppCompatActivity implements InicioFragment.OnFragmentInteractionListener {

    Fragment fragmentInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        fragmentInicio = new InicioFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentInicio).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
