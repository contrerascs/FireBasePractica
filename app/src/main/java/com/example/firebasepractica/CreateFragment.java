package com.example.firebasepractica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CreateFragment extends DialogFragment {
    Button btn_add_persona;
    EditText name,age,color;
    private FirebaseFirestore mfirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create, container, false);
        mfirestore = FirebaseFirestore.getInstance();

        name = v.findViewById(R.id.nombre);
        age = v.findViewById(R.id.edad);
        color = v.findViewById(R.id.color);
        btn_add_persona = v.findViewById(R.id.btn_add_ps);

        btn_add_persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickName = name.getText().toString().trim();
                String agePs = age.getText().toString().trim();
                String colorPs = color.getText().toString().trim();

                if(nickName.isEmpty() && agePs.isEmpty() && colorPs.isEmpty()){
                    Toast.makeText(getContext(),"Ingresar los datos",Toast.LENGTH_SHORT).show();
                }else{
                    postPs(nickName,agePs,colorPs);
                }
            }
        });

        return v;
    }

    private void postPs(String nickName, String agePs, String colorPs) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Name",nickName);
        map.put("Age",agePs);
        map.put("Color",colorPs);

        mfirestore.collection("Person").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(),"Creado con éxito", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}