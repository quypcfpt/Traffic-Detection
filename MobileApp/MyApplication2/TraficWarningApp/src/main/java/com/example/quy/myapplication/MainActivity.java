package com.example.quy.myapplication;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        listView=(ListView) findViewById(R.id.list);

        dataModels = new ArrayList<>();
        dataModels.add(new DataModel("Apple Pie","Android 1.0" , "1",1));
        dataModels.add(new DataModel("Banana Bread","Android 1.2" , "2",0));
        dataModels.add(new DataModel("Cupcake","Android 1.3" , "3",1));
        dataModels.add(new DataModel("Eclair","Android 1.8" , "4",0));
        dataModels.add(new DataModel("Froyo","Android 2.0" , "5",1));
        dataModels.add(new DataModel("Apple Pie","Android 1.0" , "1",0));
        dataModels.add(new DataModel("Banana Bread","Android 1.2" , "2",1));
        dataModels.add(new DataModel("Cupcake","Android 1.3" , "3",1));
        dataModels.add(new DataModel("Eclair","Android 1.8" , "4",1));
        dataModels.add(new DataModel("Froyo","Android 2.0" , "5",0));

        adapter= new CustomerAdapter(dataModels,getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                String name =dataModel.getName();
                intent.putExtra("NAME_ROAD",name);
                startActivity(intent);
                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
