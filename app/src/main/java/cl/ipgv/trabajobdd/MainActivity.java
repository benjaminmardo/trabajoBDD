package cl.ipgv.trabajobdd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private List<ClienteCompra> listaClientesCompras = new ArrayList<>();
    private List<String> listaClientesComprasInfo = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterString;

    EditText eTNombreCliente, eTEmailCliente, eTTelefonoCliente, eTProducto;
    Button bTGuardar, btEliminar;
    ListView lvListado;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        eTNombreCliente = findViewById(R.id.eTNombreCliente);
        eTEmailCliente = findViewById(R.id.eTEmailCliente);
        eTTelefonoCliente = findViewById(R.id.eTTelefonoCliente);
        eTProducto = findViewById(R.id.eTNombreProducto);
        bTGuardar = findViewById(R.id.bTGuardar);
        btEliminar = findViewById(R.id.btEliminar);
        lvListado = findViewById(R.id.lvListado);

        // Inicializar Firebase
        inicializarFireBase();
        listarDatos();

        // Guardar datos en Firebase
        bTGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClienteCompra clienteCompra = new ClienteCompra();
                clienteCompra.setId(UUID.randomUUID().toString());
                clienteCompra.setNombreCliente(eTNombreCliente.getText().toString());
                clienteCompra.setEmailCliente(eTEmailCliente.getText().toString());
                clienteCompra.setTelefonoCliente(eTTelefonoCliente.getText().toString());
                clienteCompra.setProducto(eTProducto.getText().toString());

                databaseReference.child("ClienteCompra").child(clienteCompra.getId()).setValue(clienteCompra);
                limpiarCampos();
            }
        });

        // Eliminar todos los datos
        btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaClientesCompras.clear();
                databaseReference.child("ClienteCompra").removeValue();
                arrayAdapterString.notifyDataSetChanged();
            }
        });
    }

    // Método para listar los datos en el ListView
    private void listarDatos() {
        databaseReference.child("ClienteCompra").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaClientesCompras.clear();
                listaClientesComprasInfo.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    ClienteCompra clienteCompra = objSnapshot.getValue(ClienteCompra.class);
                    listaClientesCompras.add(clienteCompra);
                    listaClientesComprasInfo.add(clienteCompra.getNombreCliente() + " - " + clienteCompra.getProducto());
                }
                arrayAdapterString = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listaClientesComprasInfo);
                lvListado.setAdapter(arrayAdapterString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    // Inicializar Firebase
    private void inicializarFireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    // Limpiar los campos de texto después de guardar
    private void limpiarCampos() {
        eTNombreCliente.setText("");
        eTEmailCliente.setText("");
        eTTelefonoCliente.setText("");
        eTProducto.setText("");
    }
}
