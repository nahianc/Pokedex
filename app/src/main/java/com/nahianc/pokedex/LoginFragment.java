package com.nahianc.pokedex;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        TextView signupPromt = view.findViewById(R.id.signupPrompt);
        signupPromt.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        RegistrationFragment registrationFrag = new RegistrationFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, registrationFrag)
                .addToBackStack(null)
                .commit();
    }
}