package com.example.trabalhofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListaEventos extends AppCompatActivity {

    private RecyclerView recyclerEventosFuturos;
    private RecyclerView recyclerEventosPassados;
    private EventoDAO eventoDAO;
    private EventoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_eventos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerEventosPassados = findViewById(R.id.recyclerViewPassados);
        recyclerEventosFuturos = findViewById(R.id.recyclerViewFuturos);
        eventoDAO = new EventoDAO(this);
        recyclerEventosPassados.setLayoutManager(new LinearLayoutManager(this));
        recyclerEventosFuturos.setLayoutManager(new LinearLayoutManager(this));
    }

    public void retornar(View v){
        finish();
    }

    public void cadastrarEventos(View v){
        Intent i = new Intent(this, CadastrarEventos.class);
        startActivity(i);
    }
}