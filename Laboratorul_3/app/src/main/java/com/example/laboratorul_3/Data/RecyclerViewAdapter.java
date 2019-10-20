package com.example.laboratorul_3.Data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboratorul_3.Model.Information;
import com.example.laboratorul_3.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Information> informationList;
    private DataBaseHandler db;
    private int activity;

    public RecyclerViewAdapter(Context context, List<Information> informationList, int activity) {
        this.context = context;
        this.informationList = informationList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String title = informationList.get(position).getTitle();
        holder.titleText.setText(title);
        db = new DataBaseHandler(context);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.addInformation(informationList.get(position));
                Toast.makeText(context, "Information saved", Toast.LENGTH_SHORT).show();
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteInformation(informationList.get(position).getTitle());
                Toast.makeText(context, "Information deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleText;
        public Button addButton;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            titleText = (TextView) itemView.findViewById(R.id.title);
            addButton = (Button) itemView.findViewById(R.id.addButtonList);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButtonList);

            if(activity == 1)
                deleteButton.setVisibility(View.INVISIBLE);
            else
                addButton.setVisibility(View.INVISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(informationList.get(getAdapterPosition()).getLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}