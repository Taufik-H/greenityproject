package project.greetiny.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import project.greetiny.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText ed_signup_username, ed_signup_email, ed_signup_password;
    private LinearLayout btn_signup;
    private TextView signup, txt_signup_login;
    private ProgressBar progress_signup;
    private FirebaseAuth auth;
    private String getUsername, getEmail, getPassword;

    LottieAnimationView animationView;
    TextView btn_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

     //   ed_signup_username = findViewById(R.id.ed_signup_username);
        ed_signup_email = findViewById(R.id.ed_regist_email);
        ed_signup_password = findViewById(R.id.ed_regist_passwd);
        btn_signup = findViewById(R.id.btn_signup);
       // progress_signup = findViewById(R.id.progress_signup);
       // progress_signup.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        btn_text = findViewById(R.id.btn_text);
        animationView = findViewById(R.id.button_animation);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   progress_signup.setVisibility(View.VISIBLE);
                cekDataUser();
            }
        });
        TextView Signup = findViewById(R.id.textRegistrasi);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });

    }
    private void cekDataUser() {
//        getUsername=ed_signup_username.getText().toString();
        getEmail=ed_signup_email.getText().toString();
        getPassword=ed_signup_password.getText().toString();

        if (TextUtils.isEmpty(getEmail)|| TextUtils.isEmpty(getPassword))
        {
            Toast.makeText(SignUpActivity.this, "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
        else {
            if (getPassword.length()<6)
            {
                Toast.makeText(SignUpActivity.this, "password terlalu pendek", Toast.LENGTH_SHORT).show();
            }
            else
            {
                btn_text.setVisibility(View.GONE);
                animationView.setVisibility(View.VISIBLE);
                createUser();
            }
        }
    }

    private void createUser() {
        auth.createUserWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                user user = new user(getEmail, getPassword);

                FirebaseDatabase.getInstance().getReference("users")
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                            cek status register
                                if(task.isSuccessful())
                                {
                                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                          //  progress_signup.setVisibility(View.GONE);
                                            btn_text.setVisibility(View.VISIBLE);
                                            animationView.setVisibility(View.GONE);
                                            if (task.isSuccessful()){
                                                Toast.makeText(SignUpActivity.this, "Registrasi Berhasil, cek email kamu untuk verifikasi", Toast.LENGTH_SHORT).show();
//                                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                                getEmail.toString();
                                                getPassword.toString();
                                                finish();
                                            }else{
                                                Toast.makeText(SignUpActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else{

                                    btn_text.setVisibility(View.VISIBLE);
                                    animationView.setVisibility(View.GONE);
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                              //  progress_signup.setVisibility(View.GONE);
                                btn_text.setVisibility(View.VISIBLE);
                                animationView.setVisibility(View.GONE);
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              //  progress_signup.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}