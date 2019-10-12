package com.example.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mdatabase;
    private EditText mEmail;
    private EditText mPassword;
    private Button mAddUser;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mAuth= FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();
        mEmail = (EditText) findViewById(R.id.email_ed2);
        mPassword = (EditText) findViewById(R.id.pass_ed2);
        mAddUser = (Button) findViewById(R.id.adduser);

        mAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adduser();
            }
        });
    }
    public void adduser(){

        email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if(TextUtils.isEmpty(email )|| TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Fields are Empty!!!", Toast.LENGTH_SHORT).show();
        }else{

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(register.this, "Added", Toast.LENGTH_SHORT).show();
                       add_to_database();
                    }else{
                        Toast.makeText(register.this, "Error!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }
    public void add_to_database(){

        HashMap<String, String> newMap = new HashMap<String, String>();
        newMap.put("Email:- ", email);
        mdatabase.push().setValue(newMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(register.this, email, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(register.this, account.class));

                }
            }
        });

    }
}
