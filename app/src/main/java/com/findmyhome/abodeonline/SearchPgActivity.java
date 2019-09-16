package com.findmyhome.abodeonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.findmyhome.abodeonline.Information.InformationPg;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class SearchPgActivity extends AppCompatActivity {

    FirebaseListAdapter<InformationPg> adapter;
    ListView PgListView;
    Button SearchPgCityButton;
    EditText SeachPgCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pg);

        PgListView = findViewById(R.id.searchpglistview);
        SeachPgCity = findViewById(R.id.searchpgcity);
        SearchPgCityButton = findViewById(R.id.searchpgcitybutton);

        SearchPgCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PgCityInput = SeachPgCity.getText().toString().trim().toLowerCase();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PG").child(PgCityInput);
                Query query = FirebaseDatabase.getInstance().getReference("PG").child(PgCityInput);
                if(databaseReference==null){
                    Toast.makeText(SearchPgActivity.this, "No PG available", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseListOptions<InformationPg> options = new FirebaseListOptions.Builder<InformationPg>()
                            .setQuery(query,InformationPg.class)
                            .setLayout(R.layout.list_element_display_layout)
                            .build();

                    adapter = new FirebaseListAdapter<InformationPg>(options) {
                        @Override
                        protected void populateView(View v, InformationPg model, int position) {
                            TextView PgName = v.findViewById(R.id.listelementname);
                            TextView PgAddress = v.findViewById(R.id.listelementaddress);
                            TextView PgCity = v.findViewById(R.id.listelementcity);
                            TextView PgRent = v.findViewById(R.id.listelementrent);

                            PgName.setText(model.getPgname());
                            PgAddress.setText(model.getPgaddress());
                            PgCity.setText(model.getPgcity().toUpperCase());
                            PgRent.setText(model.getPgrent());

                        }
                    };

                    PgListView.setAdapter(adapter);

                    adapter.startListening();
                }


            }
        });



    }
}
