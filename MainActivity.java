package com.example.phonebookmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    ListView listView;
    ContactAdapter adapter;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);

        adapter = new ContactAdapter();

        adapter.addItem(new ContactItem("Emma", "01060308054", 35));
        adapter.addItem(new ContactItem("Kelly", "01098201505", 30));
        adapter.addItem(new ContactItem("김윤주", "01060308056", 25));



        listView.setAdapter(adapter);

        Intent intent = getIntent();
        //get data from InputActivity
        String textName = intent.getStringExtra(InputActivity.EXTRA_TEXT);
        String textPhone = intent.getStringExtra(InputActivity.EXTRA_TEXT2);
        int number = intent.getIntExtra(InputActivity.EXTRA_NUMBER, 0);


        Button button0 = (Button) findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                startActivity(intent);
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = textName;
                String mobile = textPhone;
                int age = number;


                adapter.addItem(new ContactItem(name, mobile, age));
                adapter.notifyDataSetChanged();


            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ContactItem item = (ContactItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });

    }
    class ContactAdapter extends BaseAdapter {
        ArrayList<ContactItem> items = new ArrayList<ContactItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ContactItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ContactItemView view = new ContactItemView(getApplicationContext());

            ContactItem item = items.get(position);
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setAge(item.getAge());


            return view;
        }
    }


}