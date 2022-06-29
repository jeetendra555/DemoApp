package com.jk.demoapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.demoapp.R;
import com.jk.demoapp.callbreaks.DeleteButtonOnClickListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewUserDataAdapter extends RecyclerView.Adapter<RecyclerViewUserDataAdapter.Holder> {
    private Context context;
    private ArrayList<HashMap<String, String>> arrUserData = new ArrayList<>();
    private DeleteButtonOnClickListener deleteButtonOnClickListener;

    public RecyclerViewUserDataAdapter(Context context, ArrayList<HashMap<String, String>> arrUserData, DeleteButtonOnClickListener deleteButtonOnClickListener) {
        this.context = context;
        this.arrUserData = arrUserData;
        this.deleteButtonOnClickListener = deleteButtonOnClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item_rv, parent, false);
        return new Holder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        HashMap<String, String> map = arrUserData.get(position);
        if (map.containsKey("firstname"))
            holder.etFirstName.setText(map.get("firstname"));
        if (map.containsKey("lastname"))
            holder.etLastName.setText(map.get("lastname"));
        if (map.containsKey("mobile"))
            holder.etContactNumber.setText(map.get("mobile"));
        if (map.containsKey("email"))
            holder.etEmailAddress.setText(map.get("email"));
        if (map.containsKey("address"))
            holder.etAddress.setText(map.get("address"));

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButtonOnClickListener.onDeleteButtonClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrUserData.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private EditText etFirstName, etLastName, etEmailAddress, etContactNumber, etAddress;
        private ImageView imageViewDelete;

        public Holder(@NonNull View itemView) {
            super(itemView);
            etFirstName = itemView.findViewById(R.id.etFirstName);
            etLastName = itemView.findViewById(R.id.etLastName);
            etContactNumber = itemView.findViewById(R.id.etMobile);
            etEmailAddress = itemView.findViewById(R.id.etEmailAddress);
            etAddress = itemView.findViewById(R.id.etAddress);
            imageViewDelete = itemView.findViewById(R.id.imgViewDelete);
        }
    }
}
