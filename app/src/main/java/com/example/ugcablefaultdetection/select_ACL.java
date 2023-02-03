package com.example.ugcablefaultdetection;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class select_ACL extends Fragment {

    CardView admin_cdv, user_cdv;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_select__a_c_l, container, false);

        admin_cdv = (CardView) view.findViewById(R.id.admin_cdv);
        user_cdv = (CardView) view.findViewById(R.id.user_cdv);

        admin_cdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_select_ACL_to_loginFragment);
            }
        });

        user_cdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_select_ACL_to_loginFragment);
            }
        });

        return view;

    }
}