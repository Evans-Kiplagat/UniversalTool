package com.example.universaltool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button sign;
    EditText eml,pwd;
    TextView log;
    private  FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mFirebaseAuth = FirebaseAuth.getInstance();

        sign=findViewById(R.id.signup);
        eml=findViewById(R.id.email);
        pwd=findViewById(R.id.password);
        log=findViewById(R.id.tvLog);
        if(mFirebaseAuth !=null){
            Intent Hm = new Intent(MainActivity.this,HomePage.class);
            startActivity(Hm);
        }

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 final String em=eml.getText().toString();
                final String pw=pwd.getText().toString();
                if(em.isEmpty()){
                    eml.setError("please fill the email");
                    eml.requestFocus() ;}
                else if(pw.isEmpty()){
                    eml.setError("please fill the password");
                    eml.requestFocus();
                }
               else if(em.isEmpty()&& pw.isEmpty()){
                    Toast.makeText(MainActivity.this,"please fill the space",Toast.LENGTH_LONG).show();
                }

               else if(!(em.isEmpty()&& pw.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(em,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mFirebaseAuth.signInWithEmailAndPassword(em,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task)
                                    {

                                        Intent Hm = new Intent(MainActivity.this,HomePage.class);
                                        startActivity(Hm);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this,"error occured",Toast.LENGTH_LONG).show();
                                    }
                                });


                            }


                        }
                    });
                }
               else {
                    Toast.makeText(MainActivity.this,"error occured",Toast.LENGTH_LONG).show();
                }
            }
        });

 log.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent LOG = new Intent(MainActivity.this,loginActivity.class);
         startActivity(LOG);
     }
 });


    }


}
