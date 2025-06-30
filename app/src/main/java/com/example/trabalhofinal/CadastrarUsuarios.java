package com.example.trabalhofinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CadastrarUsuarios extends AppCompatActivity {
    private EditText editNome, editCPF, editEmail, editTelefone;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_usuarios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editNome = findViewById(R.id.editTextNomeUsuario);
        editCPF = findViewById(R.id.editTextCPF);
        editEmail = findViewById(R.id.editTextEmail);
        editTelefone = findViewById(R.id.editTextTelefone);

        usuarioDAO = new UsuarioDAO(this);
    }

    public void cadastrarUsuario(View view){
        String nome = editNome.getText().toString().trim();
        String cpf = editCPF.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String telefone = editTelefone.getText().toString().trim();

        if(nome.isEmpty() || cpf.isEmpty()){
            Toast.makeText(this, "Nome e CPF são obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se o CPF já existe
        Usuario existente = usuarioDAO.getByCpf(cpf);
        if (existente != null) {
            Toast.makeText(this, "Já existe um usuário com esse CPF.", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario(nome, cpf, email, telefone);
        long id = usuarioDAO.inserir(usuario);
        Log.d("cadastro de usuario", "Passou " + id);
        Toast.makeText(this, "Usuário cadastrado com ID: " + id, Toast.LENGTH_SHORT).show();

        editNome.setText("");
        editCPF.setText("");
        editEmail.setText("");
        editTelefone.setText("");

        Intent i = new Intent(this, ListaUsuarios.class);
        startActivity(i);
    }

    public void retornar(View v){
        Intent i = new Intent(this, ListaUsuarios.class);
        startActivity(i);
    }
}