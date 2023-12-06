package com.example.conectamovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AgregarContactoActivity extends AppCompatActivity {
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextApodo;
    private EditText editTextCorreo;

    private Button btnGuardarContacto;

    private FirebaseAuth mAuth;
    private DatabaseReference usuarioRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            usuarioRef = FirebaseDatabase.getInstance().getReference("usuarios").child(userId);
        }

        editTextNombre = findViewById(R.id.editTextNombreContacto);
        editTextApellido = findViewById(R.id.editTextApellidoContacto);
        editTextApodo = findViewById(R.id.editTextApodoContacto);
        editTextCorreo = findViewById(R.id.editTextCorreoContacto);

        btnGuardarContacto = findViewById(R.id.btnGuardarContacto);
        btnGuardarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarContacto();
            }
        });
    }



    private void guardarContacto() {
        String nombre = editTextNombre.getText().toString().trim();
        String apellido = editTextApellido.getText().toString().trim();
        String apodo = editTextApodo.getText().toString().trim();
        String correo = editTextCorreo.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || apodo.isEmpty() || correo.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usuarioRef != null) {
            // Crear un nuevo contacto
            Contacto nuevoContacto = new Contacto(nombre, apellido, apodo, correo);

            // Obtener el objeto Usuario actual
            usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);

                        // Verificar si el usuario tiene la lista de contactos
                        if (usuario != null && usuario.getContactos() != null) {
                            // Agregar el nuevo contacto a la lista de contactos
                            usuario.getContactos().add(nuevoContacto);

                            // Actualizar el nodo del usuario en la base de datos
                            usuarioRef.setValue(usuario);

                            Toast.makeText(AgregarContactoActivity.this, "Contacto guardado exitosamente", Toast.LENGTH_SHORT).show();
                            finish(); // Cerrar la actividad después de guardar el contacto
                        } else {
                            // Si la lista de contactos es nula, inicialízala y luego agrega el contacto
                            List<Contacto> listaContactos = new ArrayList<>();
                            listaContactos.add(nuevoContacto);

                            if (usuario != null) {
                                usuario.setContactos(listaContactos);

                                // Actualizar el nodo del usuario en la base de datos
                                usuarioRef.setValue(usuario);

                                Toast.makeText(AgregarContactoActivity.this, "Contacto guardado exitosamente", Toast.LENGTH_SHORT).show();
                                finish(); // Cerrar la actividad después de guardar el contacto
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(AgregarContactoActivity.this, "Error al guardar el contacto: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
