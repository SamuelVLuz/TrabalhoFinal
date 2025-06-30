package com.example.trabalhofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListaEventos extends AppCompatActivity {
    public static final int TODOS = 0;
    public static final int ANTIGOS = 1;
    public static final int FUTUROS = 2;
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

    @Override
    protected void onResume() {
        super.onResume();
        carregarEventosFuturos();
        carregarEventosPassados();
    }

    private void carregarEventos(){
        List<Evento> eventos = eventoDAO.listar(TODOS);
        adapter = new EventoAdapter(eventos, evento -> {
            Toast.makeText(this, "Selecionado: " + evento.getNome(), Toast.LENGTH_SHORT).show();
        });
        recyclerEventosFuturos.setAdapter(adapter);
    }
    private void carregarEventosFuturos(){
        List<Evento> eventos = eventoDAO.listar(FUTUROS);
        adapter = new EventoAdapter(eventos, evento -> {
            Intent intent = new Intent(ListaEventos.this, EventoDetalhes.class);
            intent.putExtra("evento", evento);
            startActivity(intent);
        });
        recyclerEventosFuturos.setAdapter(adapter);
    }

    private void carregarEventosPassados(){
        List<Evento> eventos = eventoDAO.listar(ANTIGOS);
        adapter = new EventoAdapter(eventos, evento -> {
            Intent intent = new Intent(ListaEventos.this, EventoDetalhes.class);
            intent.putExtra("evento", evento);
            startActivity(intent);
        });
        recyclerEventosPassados.setAdapter(adapter);
    }

    public void retornar(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void cadastrarEventos(View v){
        Intent i = new Intent(this, CadastrarEventos.class);
        startActivity(i);
    }
}