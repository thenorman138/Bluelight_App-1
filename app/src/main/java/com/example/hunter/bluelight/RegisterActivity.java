package com.example.hunter.bluelight;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    databaseHelper myDb;
    EditText editUTCID, editPASS, editFirst, editLast;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new databaseHelper(this);

        editUTCID = (EditText) findViewById(R.id.etUTCid);
        editPASS = (EditText) findViewById(R.id.etPassword);
        editFirst = (EditText) findViewById(R.id.etFirstName);
        editLast = (EditText) findViewById(R.id.etLastName);

        btnAdd = (Button) findViewById(R.id.Register);

        AddData();

       /** Button advanceToPin = (Button) findViewById(R.id.Register);
        advanceToPin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(RegisterActivity.this , pinActivity.class);
                startActivity(intent);
            }

        });**/

        TextView termsText = (TextView) findViewById(R.id.termsLink);
        termsText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, TermsActivity.class));

            }
        });
    }

    public void AddData() {
        btnAdd.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isInserted = myDb.insertData(editUTCID.getText().toString(),
                                editPASS.getText().toString(),
                                editFirst.getText().toString(),
                                editLast.getText().toString() );
                        if(isInserted=true)
                            Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(RegisterActivity.this, "Error registering user", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(RegisterActivity.this , pinActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
