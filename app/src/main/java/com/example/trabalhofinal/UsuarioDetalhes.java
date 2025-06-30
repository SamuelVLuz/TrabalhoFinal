package com.example.trabalhofinal;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.DialogInterface;
import android.widget.Toast;
import android.content.Intent;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AlertDialog;

public class UsuarioDetalhes extends AppCompatActivity {

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario_detalhes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usuario = getIntent().getParcelableExtra("usuario");

        if (usuario != null) {
            ((TextView) findViewById(R.id.textViewusuarioNome)).setText("Nome: " + usuario.getNome());
            ((TextView) findViewById(R.id.textViewusuarioCPF)).setText("CPF: " + usuario.getCpf());
            ((TextView) findViewById(R.id.textViewusuarioEmail)).setText("E-mail: " + usuario.getEmail());
            ((TextView) findViewById(R.id.textViewusuarioTelefone)).setText("Telefone: " + usuario.getTelefone());
        }

    }

    public void excluirUsuario(View view) {
        if (usuario != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmação")
                    .setMessage("Deseja realmente excluir o usuário?")
                    .setPositiveButton("Sim", (dialogInterface, i) -> {
                        UsuarioDAO dao = new UsuarioDAO(this);
                        dao.excluir(usuario.getId());

                        Toast.makeText(this, "Usuário excluído com sucesso!", Toast.LENGTH_SHORT).show();

                        // Retorna para a tela anterior
                        setResult(RESULT_OK);
                        finish();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }
    }


    public void retornar(View v){
        finish();
    }
}