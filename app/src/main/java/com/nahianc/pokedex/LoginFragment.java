package com.nahianc.pokedex;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import com.facebook.FacebookSdk;


public class LoginFragment extends Fragment implements View.OnClickListener {

    View view;
    LoginButton officialFacebookBtn;
    CardView googleBtn, facebookBtn;
    private FirebaseAuth mAuth;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;
    CallbackManager mCallbackManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);

        FacebookSdk.sdkInitialize(getApplicationContext());

        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(
                mCallbackManager,
                new FacebookCallback < LoginResult > () {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Handle success
                        handleFacebookToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        CardView emailBtn = view.findViewById(R.id.emailContainer);
        googleBtn = view.findViewById(R.id.googleContainer);
        facebookBtn = view.findViewById(R.id.facebookContainer);
        officialFacebookBtn = view.findViewById(R.id.login_button);
        TextView signupPrompt = view.findViewById(R.id.signupPrompt);

        signupPrompt.setOnClickListener(this);
        emailBtn.setOnClickListener(this);
        googleBtn.setOnClickListener(this);
        facebookBtn.setOnClickListener(this);

        officialFacebookBtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }

            @Override
            public void onCancel() {

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signupPrompt:
                RegistrationFragment registrationFrag = new RegistrationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, registrationFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.emailContainer:
                EmailSignInFragment emailSignInFrag = new EmailSignInFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, emailSignInFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.googleContainer:
                googleSignIn();
                break;
            case R.id.facebookContainer:
                officialFacebookBtn.performClick();
                break;
        }
    }

    private void googleSignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getActivity(), "Google sign in failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(getActivity(), authResult -> {
                    startActivity(new Intent(getActivity(), ListActivity.class));
                    getActivity().finish();
                })
                .addOnFailureListener(getActivity(), e ->
                        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show());
    }

    private void handleFacebookToken(AccessToken token) {
        startActivity(new Intent(getActivity(), ListActivity.class));
        getActivity().finish();
    }


}