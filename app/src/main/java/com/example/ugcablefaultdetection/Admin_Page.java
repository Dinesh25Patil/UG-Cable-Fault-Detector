package com.example.ugcablefaultdetection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Admin_Page extends Fragment {

    View view;
    Button reg_new_user, adm_lgout;
    TextView chk_flt, engr_list;

    FirebaseAuth fAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin__page, container, false);

        fAuth = FirebaseAuth.getInstance();

        reg_new_user = (Button) view.findViewById(R.id.reg_new_user);
        adm_lgout = (Button) view.findViewById(R.id.adm_lgout);
        chk_flt = (TextView) view.findViewById(R.id.checkflt);
        engr_list = (TextView) view.findViewById(R.id.engrlist);

        adm_lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                Navigation.findNavController(view).navigate(R.id.action_admin_Page_to_select_ACL);
            }
        });

        reg_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_admin_Page_to_register_Fragment);
            }
        });

        chk_flt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_admin_Page_to_dataDisplayFragment);
            }
        });

        engr_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_admin_Page_to_engr_list_Fragment);
            }
        });


        return view;
    }
}