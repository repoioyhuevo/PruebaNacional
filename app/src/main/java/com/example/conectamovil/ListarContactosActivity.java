package com.example.conectamovil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListarContactosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactoAdapter contactoAdapter;
    private List<Contacto> listaContactos;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_contactos);

        recyclerView = findViewById(R.id.recyclerViewContactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaContactos = new ArrayList<>();
        contactoAdapter = new ContactoAdapter(listaContactos);
        recyclerView.setAdapter(contactoAdapter);

        editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrarListaContactos(editable.toString());
            }
        });

        obtenerListaContactos();

        // Configurar el botón de agregar contacto
        Button btnAgregarContacto = findViewById(R.id.btnAgregarContacto);
        btnAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirAgregarContactoActivity();
            }
        });
    }

    private void filtrarListaContactos(String filtro) {
        List<Contacto> contactosFiltrados = new ArrayList<>();

        for (Contacto contacto : listaContactos) {
            if (contacto.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                contactosFiltrados.add(contacto);
            }
        }

        // Utiliza el nuevo método para actualizar la lista en el adaptador
        contactoAdapter.actualizarLista(contactosFiltrados);
    }

    private void obtenerListaContactos() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            DatabaseReference usuarioRef = FirebaseDatabase.getInstance().getReference("usuarios").child(userId);

            usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);

                        if (usuario != null && usuario.getContactos() != null) {
                            listaContactos.clear();
                            listaContactos.addAll(usuario.getContactos());
                            contactoAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ListarContactosActivity.this, "Error al obtener la lista de contactos: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void abrirAgregarContactoActivity() {
        Intent intent = new Intent(this, AgregarContactoActivity.class);
        startActivity(intent);
    }
}
