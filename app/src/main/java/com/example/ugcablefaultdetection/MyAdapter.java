package com.example.ugcablefaultdetection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {

    Context context;
    ArrayList<users> usersArrayList;

    public MyAdapter(Context context, ArrayList<users> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View  v = LayoutInflater.from(context).inflate(R.layout.list, parent, false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.myViewHolder holder, int position) {
         users users = usersArrayList.get(position);

         holder.fname.setText(users.fname);
         holder.email.setText(users.email);
         holder.phone.setText(users.phone);
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView fname, email, phone;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.fname);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           // Navigation.findNavController(view).navigate(R.id.);
            Toast.makeText(itemView.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
