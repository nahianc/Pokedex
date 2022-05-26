package com.nahianc.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class EmailSignInFragment extends Fragment implements View.OnClickListener {

    public View view;
    private EditText emailField;
    private EditText passwordField;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_email_login, container, false);

        emailField = view.findViewById(R.id.emailField_SignIn);
        passwordField = view.findViewById(R.id.passwordField_SignIn);
        TextView registerPrompt = view.findViewById(R.id.registerPrompt_SignIn);
        CardView signInBtn = view.findViewById(R.id.signInBtn);
        progressBar = getActivity().findViewById(R.id.progressBar);

        registerPrompt.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerPrompt_SignIn:
                RegistrationFragment registrationFrag = new RegistrationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, registrationFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.signInBtn:
                if (emailField.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailField.getText().toString()).matches()) {
                    emailField.setError("Invalid Email!");
                    emailField.requestFocus();
                    return;
                }

                if (passwordField.getText().toString().isEmpty()) {
                    passwordField.setError("Password cannot be empty!");
                    passwordField.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                signIn();
                break;
        }
    }

    private void signIn() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(getActivity(), ListActivity.class));
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}