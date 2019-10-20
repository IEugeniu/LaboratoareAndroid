package com.example.laboratorul_3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.laboratorul_3.Data.DataBaseHandler;
import com.example.laboratorul_3.Data.RecyclerViewAdapter;
import com.example.laboratorul_3.Model.Information;
import com.example.laboratorul_3.R;

import java.util.List;

public class SavedInformationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<Information> informationList;
    private DataBaseHandler db;
    private TextView noSavedMessageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_information);

        db = new DataBaseHandler(this);

        informationList = db.getAllInformation();

        noSavedMessageText = (TextView) findViewById(R.id.noSavedMessageText);

        if(informationList.size() == 0)
            noSavedMessageText.setText("No Information Saved");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewIDSaved);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapter(this, informationList, 2);
        recyclerView.setAdapter(adapter);
    }
}
