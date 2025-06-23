package com.example.trabalhofinal;

import static android.widget.Toast.makeText;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class RegistrarPresenca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_presenca);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void registrarPresenca(View v) {
        EditText editText = findViewById(R.id.editTextPresenca);
        String cpf = editText.getText().toString().trim();

        if (cpf.isEmpty()) {
            makeText(this, "Digite um CPF", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = usuarioDAO.getByCpf(cpf);

        if (usuario == null) {
            makeText(this, "CPF inválido: usuário não encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        Evento evento = getIntent().getParcelableExtra("evento");
        if (evento == null) {
            makeText(this, "Evento não encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        PresencaDAO presencaDAO = new PresencaDAO(this);
        Presenca presencaExistente = presencaDAO.getUsuario_Evento(usuario.getId(), evento.getId());
        Time agora = new Time(System.currentTimeMillis());

        if (presencaExistente == null) {
            // Registrar entrada
            Presenca novaPresenca = new Presenca(usuario.getId(), evento.getId(), agora, null);
            ContentValues values = new ContentValues();
            values.put("Usuario_id", novaPresenca.getUsuario_id());
            values.put("Evento_id", novaPresenca.getEvento_id());
            values.put("entrada", new SimpleDateFormat("HH:mm:ss").format(agora));
            values.put("saida", (String) null);  // null saida
            presencaDAO.getDb().insert("Presenca", null, values);
            makeText(this, "Entrada registrada com sucesso!", Toast.LENGTH_SHORT).show();
        } else if (presencaExistente.getSaida() == null) {
            // Atualizar saída
            ContentValues values = new ContentValues();
            values.put("saida", new SimpleDateFormat("HH:mm:ss").format(agora));
            presencaDAO.getDb().update(
                    "Presenca",
                    values,
                    "Usuario_id = ? AND Evento_id = ?",
                    new String[]{String.valueOf(usuario.getId()), String.valueOf(evento.getId())}
            );
            makeText(this, "Saída registrada com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            makeText(this, "Presença já encerrada", Toast.LENGTH_SHORT).show();
        }

        editText.setText(""); // limpa campo
    }

    public void retornar(View v){
        finish();
    }
}