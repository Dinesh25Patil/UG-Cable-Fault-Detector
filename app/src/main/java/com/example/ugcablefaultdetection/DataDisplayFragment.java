package com.example.ugcablefaultdetection;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

public class DataDisplayFragment extends Fragment {

    View view;
    Button lgout;
    TextView name, email, phone, back;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseFirestore fstore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_data_display, container, false);


        lgout = (Button) view.findViewById(R.id.logout);
        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        phone = (TextView) view.findViewById(R.id.phone);
        //back = (TextView) view.findViewById(R.id.available);
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();


        //Reading the data from the firestore database and displaying it into the respective fields

        String userId = fAuth.getCurrentUser().getUid();
        DocumentReference docref = fstore.collection("users").document(userId);
                docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()){
                                name.setText(document.getString("fname"));
                                email.setText(document.getString("email"));
                                phone.setText(document.getString("phone"));
                            }else{
                                Log.d("dbError", "No such document");
                            }
                        }else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        docref.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                name.setText(value.getString("fname"));
//                email.setText(value.getString("email"));
//                phone.setText(value.getString("phone"));
//            }
//        });


        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.getInstance().signOut();
                //Navigation.findNavController(view).navigate(R.id.action_dataDisplayFragment_to_loginFragment);
                Navigation.findNavController(view).navigate(R.id.action_dataDisplayFragment_to_select_ACL);
                //Navigation.findNavController(view).navigate(R.id.action_dataDisplayFragment2_to_loginFragment);
            }
        });

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_dataDisplayFragment_to_admin_Page);
//            }
//        });

        return view;
    }
}