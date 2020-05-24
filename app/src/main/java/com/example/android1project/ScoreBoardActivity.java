package com.example.android1project;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreBoardActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        //setListAdapter(adapter);

        /*List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> spain = new HashMap<>();
        spain.put("image", R.drawable.ic_boy_1);
        spain.put("name", "spain");
        spain.put("is_good", true);
        data.add(spain);

        String[] from = {"image", "name", "is_good"};
        int[] to = {R.id.btn_play};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.activity_game_menu, from, to);
        setListAdapter(simpleAdapter);*/
    }
}
