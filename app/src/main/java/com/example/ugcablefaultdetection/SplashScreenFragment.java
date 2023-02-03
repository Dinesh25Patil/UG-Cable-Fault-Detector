package com.example.ugcablefaultdetection;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigatorExtrasKt;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SplashScreenFragment extends Fragment {

    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseFirestore fstore;
    String aclvalue;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();

        //String uid = fUser.getUid();

        //uid = authResult.getUser().getUid();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if( fUser != null){
                    fetchdata();
                    //Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_admin_Page);
//                    getParentFragmentManager().setFragmentResultListener("datafromlgin", getViewLifecycleOwner(), new FragmentResultListener() {
//                        @Override
//                        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
//                            String email = result.getString("demail");
//                            String pass = result.getString("dpass");
//                            fAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                                @Override
//                                public void onSuccess(AuthResult authResult) {
//                                    DocumentReference df = fstore.collection("users").document(authResult.getUser().getUid());
//                                    df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                            if(documentSnapshot.getString("isAdmin") != null){
//                                                Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_admin_Page);
//                                            }
//                                            if (documentSnapshot.getString("isUser") != null){
//                                                Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_dataDisplayFragment);
//                                            }
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    });

                  //  Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_dataDisplayFragment);
                }else {
                   // Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_register_Fragment);
                    Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_select_ACL);
                }
            }
        },3500);


        return view;
    }

    public void fetchdata() {
        DocumentReference dr = fstore.collection("users").document(fUser.getUid());
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    if (documentSnapshot.getString("isAdmin") != null){
                        Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_admin_Page);
                        //Toast.makeText(getActivity(), documentSnapshot.getString("isAdmin"), Toast.LENGTH_SHORT).show();
                    }
                    //Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_admin_Page);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
       //Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_admin_Page);
    }

}