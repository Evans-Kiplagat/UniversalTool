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
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    Button log;
    EditText eml,pwd;
     FirebaseAuth mFirebaseAuth;
    private   FirebaseAuth.AuthStateListener mFireAuthState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();

        log=findViewById(R.id.logg);
        eml=findViewById(R.id.email);
        pwd=findViewById(R.id.password);

        mFireAuthState = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =mFirebaseAuth.getCurrentUser();
                if(user !=null){
                    Intent Hm = new Intent(loginActivity.this,HomePage.class);
                    startActivity(Hm);
                    Toast.makeText(loginActivity.this,"your login in",Toast.LENGTH_LONG).show();
                 }
                else {
                    Toast.makeText(loginActivity.this,"log in",Toast.LENGTH_LONG).show();
                }
            }

        };

      log.setOnClickListener(new View.OnClickListener() {
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
                  Toast.makeText(loginActivity.this,"please fill the space",Toast.LENGTH_LONG).show();
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

                                      Intent Hm = new Intent(loginActivity.this,HomePage.class);
                                      startActivity(Hm);

                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      Toast.makeText(loginActivity.this,"error occured",Toast.LENGTH_LONG).show();
                                  }
                              });


                          }


                      }
                  });
              }
              else {
                  Toast.makeText(loginActivity.this,"error occured",Toast.LENGTH_LONG).show();
              }
          }
      });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mFireAuthState);
    }
}
