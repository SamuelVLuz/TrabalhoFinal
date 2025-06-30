package com.example.trabalhofinal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.net.Uri;
import androidx.core.content.FileProvider;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RelatorioEvento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_evento);

        Evento evento = getIntent().getParcelableExtra("evento");
        if (evento == null) return;

        long tempoMinimo = evento.getParticipacao().toMillis();

        PresencaDAO presencaDAO = new PresencaDAO(this);
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        List<Presenca> presencas = presencaDAO.listar(0);
        List<Presenca> presentes = new ArrayList<>();
        List<Presenca> insuficientes = new ArrayList<>();
        List<Presenca> semSaida = new ArrayList<>();

        for (Presenca p : presencas) {
            if (p.getEvento_id() != evento.getId()) continue;

            Usuario u = usuarioDAO.getById(p.getUsuario_id());
            if (u == null) continue;

            Time entrada = p.getEntrada();
            Time saida = p.getSaida();
            long permanencia;

            if (saida != null) {
                permanencia = saida.getTime() - entrada.getTime();
                if (permanencia >= tempoMinimo) {
                    presentes.add(p);
                } else {
                    insuficientes.add(p);
                }
            } else {
                semSaida.add(p);
            }
        }

// Conectar o botão ao método exportarPdf
        MaterialButton btnExportar = findViewById(R.id.button_exportar_pdf);
        btnExportar.setOnClickListener(v -> {
            exportarPdf(evento, presentes, insuficientes, semSaida, usuarioDAO);
        });

        LinearLayout layout = findViewById(R.id.linearLayoutRelatorioEvento); // Certifique-se de ter um ID no layout interno

        for (Presenca p : presencas) {
            if (p.getEvento_id() != evento.getId()) continue;

            Usuario u = usuarioDAO.getById(p.getUsuario_id());
            if (u == null) continue;

            Time entrada = p.getEntrada();
            Time saida = p.getSaida();
            long permanencia;

            String categoria;
            if (saida != null) {
                permanencia = saida.getTime() - entrada.getTime();
                if (permanencia >= tempoMinimo) {
                    categoria = "Presente";
                } else {
                    categoria = "Tempo insuficiente";
                }
            } else {
                saida = evento.getFim();
                permanencia = saida.getTime() - entrada.getTime();
                categoria = "Sem saída";
            }

            // Criar visualização de cada presença
            TextView tv = new TextView(this);
            tv.setText(String.format(Locale.getDefault(),
                    "%s - %s\nEntrada: %s | Saída: %s\nTempo: %d min\nCategoria: %s\n",
                    u.getCpf(),
                    u.getNome(),
                    entrada.toString(),
                    saida != null ? saida.toString() : "N/A",
                    permanencia / 60000,
                    categoria
            ));
            tv.setPadding(12, 12, 12, 24);
            layout.addView(tv);
        }
    }

    private void exportarPdf(Evento evento, List<Presenca> presentes, List<Presenca> insuficientes, List<Presenca> semSaida, UsuarioDAO usuarioDAO) {
        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();
        paint.setTextSize(12);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create(); // A4
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        int y = 40;
        int marginLeft = 40;

        paint.setFakeBoldText(true);
        paint.setTextSize(16);
        canvas.drawText("Relatório de Presença - " + evento.getNome(), marginLeft, y, paint);
        y += 30;
        paint.setFakeBoldText(false);
        paint.setTextSize(12);

        // Presente
        canvas.drawText("Presente:", marginLeft, y, paint);
        y += 20;
        for (Presenca p : presentes) {
            Usuario u = usuarioDAO.getById(p.getUsuario_id());
            String linha = u.getNome() + " - CPF: " + u.getCpf() +
                    " - Entrada: " + p.getEntrada().toString() +
                    " - Saída: " + p.getSaida().toString() +
                    " - Tempo: " + getDuracaoTexto(p.getEntrada(), p.getSaida());
            canvas.drawText(linha, marginLeft, y, paint);
            y += 20;
        }

        y += 20;
        canvas.drawText("Tempo insuficiente:", marginLeft, y, paint);
        y += 20;
        for (Presenca p : insuficientes) {
            Usuario u = usuarioDAO.getById(p.getUsuario_id());
            String linha = u.getNome() + " - CPF: " + u.getCpf() +
                    " - Entrada: " + p.getEntrada().toString() +
                    " - Saída: " + p.getSaida().toString() +
                    " - Tempo: " + getDuracaoTexto(p.getEntrada(), p.getSaida());
            canvas.drawText(linha, marginLeft, y, paint);
            y += 20;
        }

        y += 20;
        canvas.drawText("Sem saída:", marginLeft, y, paint);
        y += 20;
        for (Presenca p : semSaida) {
            Usuario u = usuarioDAO.getById(p.getUsuario_id());
            Time fim = evento.getFim();
            String linha = u.getNome() + " - CPF: " + u.getCpf() +
                    " - Entrada: " + p.getEntrada().toString() +
                    " - Saída: ---" +
                    " - Tempo: " + getDuracaoTexto(p.getEntrada(), fim);
            canvas.drawText(linha, marginLeft, y, paint);
            y += 20;
        }

        document.finishPage(page);

        File file = new File(getExternalFilesDir(null), "relatorio_evento_" + evento.getId() + ".pdf");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();

            Toast.makeText(this, "PDF salvo com sucesso", Toast.LENGTH_LONG).show();

            // Abrir PDF
            Uri uri = FileProvider.getUriForFile(
                    this,
                    getApplicationContext().getPackageName() + ".provider",
                    file
            );

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                startActivity(intent);
            } catch (Exception e2) {
                Toast.makeText(this, "Nenhum aplicativo de PDF encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao gerar PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private String getDuracaoTexto(Time entrada, Time saida) {
        long duracaoMillis = saida.getTime() - entrada.getTime();
        long minutos = duracaoMillis / 60000;
        long horas = minutos / 60;
        minutos = minutos % 60;
        return String.format("%02dh %02dm", horas, minutos);
    }




    public void retornar(View v) {
        finish();
    }
}
