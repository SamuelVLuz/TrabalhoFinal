package com.example.trabalhofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

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

        Evento evento = getIntent().getParcelableExtra("evento");

        if (evento != null) {
            ((TextView) findViewById(R.id.textViewEventoNome)).setText("Nome: " + evento.getNome());
            ((TextView) findViewById(R.id.textViewEventoDescricao)).setText("Descrição: " + evento.getDescricao());
            ((TextView) findViewById(R.id.textViewEventoData)).setText("Data: " + evento.getData().toString());
            ((TextView) findViewById(R.id.textViewEventoInicio)).setText("Início: " + evento.getInicio().toString());
            ((TextView) findViewById(R.id.textViewEventoFim)).setText("Fim: " + evento.getFim().toString());
            ((TextView) findViewById(R.id.textViewEventoParticipacao)).setText("Participação: " + evento.getParticipacao().toMinutes() + " min");
        }

        MaterialButton btnPresenca = findViewById(R.id.button_registrar_presenca);

        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();

        LocalDate dataEvento = evento.getData().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        LocalTime inicio = LocalTime.of(
                evento.getInicio().getHours(),
                evento.getInicio().getMinutes(),
                evento.getInicio().getSeconds()
        );

        LocalTime fim = LocalTime.of(
                evento.getFim().getHours(),
                evento.getFim().getMinutes(),
                evento.getFim().getSeconds()
        );

        boolean eventoHoje = dataEvento.isEqual(hoje);
        boolean dentroDoHorario = !agora.isBefore(inicio) && !agora.isAfter(fim);

        if (eventoHoje && dentroDoHorario) {
            btnPresenca.setVisibility(View.VISIBLE);
            btnPresenca.setOnClickListener(v -> {
                // redirecionar para tela de digitar CPF, passando evento se precisar
                Intent i = new Intent(this, RegistrarPresenca.class);
                i.putExtra("evento", evento);
                startActivity(i);
            });
        } else {
            btnPresenca.setVisibility(View.GONE);
        }
    }

    public void retornar(View v){
        finish();
    }
}