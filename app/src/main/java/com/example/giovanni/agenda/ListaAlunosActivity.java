package com.example.giovanni.agenda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ListaAlunosActivity extends AppCompatActivity {

    private ImageButton imageButton;
    TextView textView2;
    CallbackManager callbackManager;
    ProgressDialog nDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_lista_alunos);
        Button b = findViewById(R.id.btnLogin);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });


        callbackManager = CallbackManager.Factory.create();
        ImageButton imageButton = (ImageButton) findViewById(R.id.fb_Button);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(ListaAlunosActivity.this, "Login efetuado com sucesso",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(ListaAlunosActivity.this, "Login Inválido",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void login(){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtSenha = findViewById(R.id.edtSenha);

        Task<AuthResult> processo = auth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtSenha.getText().toString());
        processo.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                Intent it = new Intent(ListaAlunosActivity.this, TelaPrincipalActivity.class); // Intent it = new Intent(this, TelaPrincipalActivity.class); tive que coloacr a classe no primeiro this pq ela ta dentro do oncompletelistener
                startActivity(it);
                }else{
                    Toast.makeText(ListaAlunosActivity.this, "E-mail ou senha invalidos",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
