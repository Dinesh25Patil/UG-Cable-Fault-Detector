package com.example.ugcablefaultdetection;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register_Fragment extends Fragment {

    View view;
    Button reg_btn;
    EditText regemail, rpass, rphone, rname;
    TextView loginpgbtn;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    ProgressBar pgbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_register_, container, false);
        reg_btn = (Button) view.findViewById(R.id.Register);
        regemail = (EditText) view.findViewById(R.id.email);
        rpass = (EditText) view.findViewById(R.id.pass);
        rphone = (EditText) view.findViewById(R.id.phone);
        rname = (EditText) view.findViewById(R.id.fullname);
        //loginpgbtn = (TextView) view.findViewById(R.id.login_pg_btn);
        pgbar = (ProgressBar) view.findViewById(R.id.pg_bar);


        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

//        loginpgbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_register_Fragment_to_loginFragment);
//            }
//        });


//        regemail.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (!genemail.matches(emailpattern)){
//                    regemail.setError("Enter valid email address");
//                    return false;
//                }
//                return false;
//
//            }
//        });

        regemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String genemail = regemail.getText().toString().trim();
                if( (!genemail.matches(emailpattern))) {
                    regemail.setError("Please enter valid email address");
                }
                if(TextUtils.isEmpty(genemail)){
                    regemail.setError("Please enter email address");
                }
            }
        });

        rpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String passvalid = rpass.getText().toString().trim();
                if(passvalid.length() < 6){
                    rpass.setError("Password should have >= 6 characters");
                }
            }
        });

        rphone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String phoneno = rphone.getText().toString();
                if(phoneno.length() > 10 || phoneno.length() < 10){
                    rphone.setError("Phone no should have 10 numbers");
                }
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = regemail.getText().toString().trim();
                String pass = rpass.getText().toString().trim();
                String phone = rphone.getText().toString();
                String fullname = rname.getText().toString();


                pgbar.setVisibility(View.VISIBLE);

                //register using the firebase

                fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        userId = fAuth.getCurrentUser().getUid();
                                        regemail.setText("");
                                        rpass.setText("");
                                        rphone.setText("");
                                        rname.setText("");
                                        DocumentReference docref = fstore.collection("users").document(userId);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("email", email);
                                        user.put("phone", phone);
                                        user.put("fname", fullname);
                                        user.put("isUser", "1");
                                        docref.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getActivity(), "Registered successfully please verify the email", Toast.LENGTH_SHORT).show();
                                                //Navigation.findNavController(view).navigate(R.id.action_register_Fragment_to_loginFragment);
                                                pgbar.setVisibility(View.GONE);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                pgbar.setVisibility(View.GONE);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(getActivity(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pgbar.setVisibility(View.GONE);
                        }
                    }
                });



            }
        });
        return view;
    }
}