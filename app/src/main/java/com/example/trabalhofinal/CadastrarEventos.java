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

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;

public class CadastrarEventos extends AppCompatActivity {
    private EditText editNome, editDesc, editData, editInicio, editFinal, editParticipacao;
    private EventoDAO eventoDAO;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_eventos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editNome = findViewById(R.id.editTextNomeEvento);
        editDesc = findViewById(R.id.editTextDescricao);
        editData = findViewById(R.id.editTextData);
        editInicio = findViewById(R.id.editTextInicio);
        editFinal = findViewById(R.id.editTextFinal);
        editParticipacao = findViewById(R.id.editTextParticipacao);

        eventoDAO = new EventoDAO(this);
    }

    public void cadastrarEvento(View view){
        String nome = editNome.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        String dataStr = editData.getText().toString().trim();
        String inicioStr = editInicio.getText().toString().trim();
        String fimStr = editFinal.getText().toString().trim();
        String participacaoStr = editParticipacao.getText().toString().trim();

        if(!nome.isEmpty()){
            try {
                // Parse date from dd/MM/yyyy to java.sql.Date
                SimpleDateFormat userDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date parsedDate = userDateFormat.parse(dataStr);
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                // Parse time from HH:mm to java.sql.Time
                SimpleDateFormat userTimeFormat = new SimpleDateFormat("HH:mm");
                java.util.Date parsedInicio = userTimeFormat.parse(inicioStr);
                java.util.Date parsedFim = userTimeFormat.parse(fimStr);
                java.util.Date parsedParticipacao = userTimeFormat.parse(participacaoStr);

                java.sql.Time sqlInicio = new java.sql.Time(parsedInicio.getTime());
                java.sql.Time sqlFim = new java.sql.Time(parsedFim.getTime());
                Duration sqlParticipacao = Duration.parse(participacaoStr); // e.g., "04:00"


                String[] parts = participacaoStr.split(":");
                long hours = Long.parseLong(parts[0]);
                long minutes = Long.parseLong(parts[1]);
                Duration participacao = Duration.ofHours(hours).plusMinutes(minutes);

                // Create and insert Evento
                Evento evento = new Evento(nome, desc, sqlDate, sqlInicio, sqlFim, sqlParticipacao);
                long id = eventoDAO.inserir(evento);

                Log.d("cadastro de evento", "Passou " + id);
                Toast.makeText(this, "Evento cadastrado com ID: " + id, Toast.LENGTH_SHORT).show();

                // Clear inputs
                editNome.setText("");
                editDesc.setText("");
                editData.setText("");
                editInicio.setText("");
                editFinal.setText("");
                editParticipacao.setText("");

                // Go to list activity
                Intent i = new Intent(this, ListaEventos.class);
                startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Formato inválido. Use: dd/MM/yyyy para data e HH:mm para hora.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Por favor, digite um nome", Toast.LENGTH_SHORT).show();
        }
    }

    public void retornar(View v){
        finish();
    }
}