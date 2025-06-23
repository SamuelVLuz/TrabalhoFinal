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

public class ListaUsuarios extends AppCompatActivity {
    private RecyclerView recyclerUsuarios;
    private UsuarioDAO usuarioDAO;
    private UsuarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_usuarios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerUsuarios = findViewById(R.id.recyclerViewUsuarios);
        usuarioDAO = new UsuarioDAO(this);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarUsuarios();
    }
    private void carregarUsuarios(){
        List<Usuario> usuarios = usuarioDAO.listar();
        adapter = new UsuarioAdapter(usuarios, usuario -> {
            Intent intent = new Intent(ListaUsuarios.this, UsuarioDetalhes.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        });/*
        adapter = new UsuarioAdapter(usuarios, usuario -> {
            Toast.makeText(this, "Selecionado: " + usuario.getNome(), Toast.LENGTH_SHORT).show();
        });*/
        recyclerUsuarios.setAdapter(adapter);
    }

    public void retornar(View v){
        finish();
    }

    public void cadastrarUsuarios(View v){
        Intent i = new Intent(this, CadastrarUsuarios.class);
        startActivity(i);
    }
}