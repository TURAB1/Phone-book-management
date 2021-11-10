package com.example.phonebookmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {
    EditText editText;  //db name
    EditText editText2; //table name
    EditText editText3; //contact name
    EditText editText4; // contact age
    EditText editText5; //contact phone number

    //variables to help in sharing data between activities
    public static final String EXTRA_TEXT = "com.example.phonebookmanagement.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.example.phonebookmanagement.EXTRA_TEXT2";
    public static final String EXTRA_NUMBER = "com.example.phonebookmanagement.EXTRA_NUMBER";

    TextView textView;

    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editText = findViewById(R.id.editText2);//db name
        editText2 = findViewById(R.id.editText3);//table name
        editText3 = findViewById(R.id.editText4);//contact name
        editText4 = findViewById(R.id.editText5);// contact age
        editText5 = findViewById(R.id.editText6);//contact phone number

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String databaseName = editText.getText().toString();//데이터베이스 이름 설정
                openDatabase(databaseName);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = editText2.getText().toString();//테이블 이름설정
                createTable(tableName);
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText3.getText().toString().trim();
                String ageStr = editText4.getText().toString().trim();
                String mobile = editText5.getText().toString().trim();

                int age = -1;
                try{
                    age = Integer.parseInt(ageStr);
                }catch (Exception e){}

                insertData(name, age, mobile);
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = editText2.getText().toString();//테이블 이름설정
                selectData(tableName);   //조회
            }
        });
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openMainActivity();

            }
        });

    }

    public void openMainActivity() {

        String textName = editText3.getText().toString();
        String textPhone=editText5.getText().toString();
        String textTable=editText2.getText().toString();
        int number = Integer.parseInt(editText4.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        //pass data to MainActivity
        intent.putExtra(EXTRA_TEXT, textName);
        intent.putExtra(EXTRA_TEXT2, textPhone);
        intent.putExtra(EXTRA_NUMBER, number);
        startActivity(intent);
    }



    public void openDatabase(String databaseName){
        println("openDatabase() 호출됨");
        database = openOrCreateDatabase(databaseName, MODE_PRIVATE,null) ;
        if(database !=null){
            println("데이터베이스 오픈됨");
        }
    }

    public void createTable(String tableName){
        println("createTable() 호출됨.");

        if(database!= null) {
            String sql = "create table if not exists " + tableName + "(_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text)";
            database.execSQL(sql);

            println("테이블 생성됨.");
        }else {
            println("데이터베이스를 먼저 오픈하세요");
        }
    }

    public void insertData(String name, int age, String mobile){
        println("insertData() 호출됨.");

        if(database != null){
            String sql = "insert into phoneBook(name, age, mobile) values(?, ?, ?)";
            Object[] params = {name, age, mobile};
            database.execSQL(sql, params);
            println("데이터 추가함");

        }else {
            println("데이터베이스를 먼저 오픈하시오");
        }
    }

    public  void selectData(String tableName){
        println("selectData() 호출됨.");
        if(database != null){
            String sql = "select name, age, mobile from "+tableName;
            Cursor cursor = database.rawQuery(sql, null);
            println("조회된 데이터개수 :" + cursor.getCount());

            for( int i = 0; i< cursor.getCount(); i++){
                cursor.moveToNext();//다음 레코드로 넘어간다.
                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);
                println("*" + i + ")" +  name + ", " + age + ", "+ mobile );
            }
            cursor.close();
        }
    }

    public void println(String data){
        textView.append(data + "\n");
    }


}