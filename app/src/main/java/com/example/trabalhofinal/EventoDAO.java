package com.example.trabalhofinal;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class EventoDAO {
    private SQLiteDatabase db;
    public EventoDAO(Context context) {
        BancoHelper helper = new BancoHelper(context);
        db = helper.getWritableDatabase();
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public long inserir(Evento evento) {
        ContentValues values = new ContentValues();
        values.put("nome", evento.getNome());
        values.put("descricao", evento.getDescricao());
        values.put("data", dateFormat.format(evento.getData()));
        values.put("inicio", timeFormat.format(evento.getInicio()));
        values.put("fim", timeFormat.format(evento.getFim()));
        values.put("participacao", evento.getParticipacao().getSeconds());
        return db.insert("Evento", null, values);
    }
    public List<Evento> listar() {
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Evento", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            String descricao = cursor.getString(2);
            Date data = dateFormat.parse(cursor.getString(3));
            Time inicio = new Time(timeFormat.parse(cursor.getString(4)).getTime());
            Time fim = new Time(timeFormat.parse(cursor.getString(5)).getTime());
            long participacaoSeconds = cursor.getLong(6);
            Duration participacao = Duration.ofSeconds(participacaoSeconds);
            eventos.add(new Evento(id, nome, descricao, data, inicio, fim, participacao));
        }
        cursor.close();
        return eventos;
    }
}