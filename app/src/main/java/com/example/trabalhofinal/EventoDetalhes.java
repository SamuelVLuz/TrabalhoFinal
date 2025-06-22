package com.example.trabalhofinal;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EventoDetalhes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_evento_detalhes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textNome = findViewById(R.id.textViewEventoNome);
        TextView textDescricao = findViewById(R.id.textViewEventoDescricao);
        TextView textData = findViewById(R.id.textViewEventoData);
        TextView textInicio = findViewById(R.id.textViewEventoInicio);
        TextView textFim = findViewById(R.id.textViewEventoFim);
        TextView textParticipacao = findViewById(R.id.textViewEventoParticipacao);

        if (tarefa != null) {
            textNome.setText(tarefa.getTitulo());
            textDescricao.setText(tarefa.getDescricao());
            textData.setText(tarefa.getData());
        }
    }
}