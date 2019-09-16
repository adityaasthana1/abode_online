package com.findmyhome.abodeonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.findmyhome.abodeonline.Information.HostelInfo;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class SearchHostelActivity extends AppCompatActivity {

    ListView HostelListView;
    FirebaseListAdapter<HostelInfo> adapter;
    Button CityInputButton;
    EditText SearchCityInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hostel);
        CityInputButton = findViewById(R.id.searchcitybutton);
        HostelListView = findViewById(R.id.searchhostellistview);

        CityInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchCityInput = findViewById(R.id.seachcityedittext);
                String Searchcity = SearchCityInput.getText().toString().trim().toLowerCase();

                Query query = FirebaseDatabase.getInstance().getReference("hostels").child(Searchcity);

                FirebaseListOptions<HostelInfo> options = new FirebaseListOptions.Builder<HostelInfo>()
                        .setQuery(query,HostelInfo.class)
                        .setLayout(R.layout.list_element_display_layout)
                        .build();

                adapter = new FirebaseListAdapter<HostelInfo>(options) {
                    @Override
                    protected void populateView(View v, HostelInfo model, int position) {
                        TextView ListName = v.findViewById(R.id.listelementname);
                        TextView ListAddress = v.findViewById(R.id.listelementaddress);
                        TextView ListCity = v.findViewById(R.id.listelementcity);
                        TextView ListRent = v.findViewById(R.id.listelementrent);
                        TextView ListEmail = v.findViewById(R.id.listelementemail);

                        ListName.setText(model.getHostelname());
                        ListAddress.setText(model.getHosteladdress());
                        ListCity.setText(model.getHostelcity().toUpperCase());
                        ListRent.setText(model.getHostelrent());
                        ListEmail.setText(model.getOwneremail());

                    }
                };
                HostelListView.setAdapter(adapter);
                adapter.startListening();
            }
        });

        HostelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchHostelActivity.this,ElementProfileActivity.class);
                HostelInfo hostelInfo = (HostelInfo)parent.getAdapter().getItem(position);
                intent.putExtra("HOSTEL_OBJECT",hostelInfo);
                startActivity(intent);
                //overridePendingTransition(R.anim.slide_up,R.anim.no_animation);
            }
        });



        //final TextView testText = findViewById(R.id.texttext);



    }


}
