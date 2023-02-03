package com.example.ugcablefaultdetection;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class LoginFragment extends Fragment {

    View view;
    EditText lemail, lpass;
    Button lbutton;
    TextView fpassword, backregbtn;
    ProgressBar pgbar;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_login, container, false);

        lemail = (EditText) view.findViewById(R.id.loginemail);
        lpass = (EditText) view.findViewById(R.id.loginpass);
        lbutton = (Button) view.findViewById(R.id.login_btn);
        fpassword = (TextView) view.findViewById(R.id.forgotpass);
        //backregbtn = (TextView) view.findViewById(R.id.back_reg_btn);
        pgbar = (ProgressBar) view.findViewById(R.id.pg_bar);


        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

//        if (fAuth.getCurrentUser() != null){
//            Intent intent = new Intent(getActivity(), MainActivity2.class);
//            startActivity(intent);
//        }

        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forget_pass_Fragment);
            }
        });

//        backregbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_register_Fragment);
//            }
//        });

        lemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String emailvaid = lemail.getText().toString().trim();
                String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(TextUtils.isEmpty(emailvaid)){
                    lemail.setError("Please enter the email address");
                }
                if(!emailvaid.matches(emailpattern)){
                    lemail.setError("Please enter the valid email address");
                }
            }
        });

        lpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String passvalid = lpass.getText().toString().trim();
                if(passvalid.length() < 6){
                    lpass.setError("Password should have >= 6 characters");
                }
            }
        });

        lbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = lemail.getText().toString().trim();
                String pass = lpass.getText().toString().trim();

                pgbar.setVisibility(View.VISIBLE);

//                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        pgbar.setVisibility(View.GONE);
//                        if (task.isSuccessful()){
//                            if(fAuth.getCurrentUser().isEmailVerified()){
//                               
//                                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_dataDisplayFragment);
//                            }
//                            else{
//                                Toast.makeText(getActivity(), "Please Verify email address", Toast.LENGTH_SHORT).show();
//                                
//                            }
//
//                        }
//                        else {
//                            Toast.makeText(getActivity(), "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
                
                
                fAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        pgbar.setVisibility((View.GONE));
                        CheckUserAccessLevel(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pgbar.setVisibility((View.GONE));
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Bundle result = new Bundle();
        result.putString("demail",lemail.getText().toString().trim());
        result.putString("dpass",lpass.getText().toString().trim());
        getParentFragmentManager().setFragmentResult("datafromlgin",result);



        return view;

    }

    private void CheckUserAccessLevel(String uid) {
        DocumentReference df = fstore.collection("users").document(uid);

        //extract data from the database/document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess" + documentSnapshot.getData());

                //identify the access level

                if(documentSnapshot.getString("isAdmin") != null){
                    //user is admin now
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_admin_Page);
                }

                if(documentSnapshot.getString("isUser") != null){
                    //User is normal user now

                    //Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_dataDisplayFragment);

                    if(fAuth.getCurrentUser().isEmailVerified()){
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_dataDisplayFragment);
                    }
                    else{
                        Toast.makeText(getActivity(), "Please Verify email address", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}