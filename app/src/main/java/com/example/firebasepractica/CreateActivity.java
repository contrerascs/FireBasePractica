package com.example.firebasepractica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CreateActivity extends AppCompatActivity {

    Button btn_add_persona;
    EditText name,age,color;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        this.setTitle("Crear");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mfirestore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.nombre);
        age = findViewById(R.id.edad);
        color = findViewById(R.id.color);
        btn_add_persona = findViewById(R.id.btn_add_ps);

        btn_add_persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickName = name.getText().toString().trim();
                String agePs = age.getText().toString().trim();
                String colorPs = color.getText().toString().trim();

                if(nickName.isEmpty() && agePs.isEmpty() && colorPs.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingresar los datos",Toast.LENGTH_SHORT).show();
                }else{
                    postPs(nickName,agePs,colorPs);
                }
            }
        });
    }

    private void postPs(String nickName, String agePs, String colorPs) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Name",nickName);
        map.put("Age",agePs);
        map.put("Color",colorPs);

        mfirestore.collection("Person").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Creado con éxito", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  false;
    }
}