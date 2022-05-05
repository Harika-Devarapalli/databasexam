package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.xml.sax.DTDHandler;

import java.util.HashMap;

class users{
    private String name,phn,regdno;
    public users(String name,String phn,String regdno){
        this.name=name;
        this.phn=phn;
        this.regdno=regdno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhn() {
        return phn;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public String getRegdno() {
        return regdno;
    }

    public void setRegdno(String regdno) {
        this.regdno = regdno;
    }
}

public class MainActivity extends AppCompatActivity {
    EditText name,phone,regno;
    Button insert,delete,update,retrive;
    //retrive texts
    EditText phnno,rgn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        regno=findViewById(R.id.regno);
        insert=findViewById(R.id.button);
        delete=findViewById(R.id.delete);
        update=findViewById(R.id.update);
        retrive=findViewById(R.id.retrive);
        phnno=findViewById(R.id.phnno);
        rgn=findViewById(R.id.rgn);
        firebaseDatabase=FirebaseDatabase.getInstance();
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference=firebaseDatabase.getReference("database").child(name.getText().toString());
                users use=new users(name.getText().toString(),phone.getText().toString(),regno.getText().toString());
                databaseReference.setValue(use);
            }
        });
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                databaseReference=firebaseDatabase.getReference("database");
                HashMap<String,Object> hm=new HashMap<>();
                hm.put("phn",phone.getText().toString());
                hm.put("regdno",regno.getText().toString());
                databaseReference.child(name.getText().toString()).updateChildren(hm);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference=firebaseDatabase.getReference("database");
                databaseReference.child(name.getText().toString()).removeValue();
            }
        });
        retrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference=firebaseDatabase.getReference("database");
                databaseReference.child(name.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot ds=task.getResult();
                            String phnnum=String.valueOf(ds.child("phn").getValue());
                            String reg=String.valueOf(ds.child("regdno").getValue());
                            phnno.setText(phnnum);
                            rgn.setText(reg);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"User does not exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
}}
