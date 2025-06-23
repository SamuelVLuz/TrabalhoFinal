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
public class PresencaDAO {
    private SQLiteDatabase db;
    public PresencaDAO(Context context) {
        BancoHelper helper = new BancoHelper(context);
        db = helper.getWritableDatabase();
    }
    
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public long inserir(Presenca presenca) {
        ContentValues values = new ContentValues();
        values.put("Usuario_id", presenca.getUsuario_id());
        values.put("Evento_id", presenca.getEvento_id());
        values.put("entrada", timeFormat.format(presenca.getEntrada()));
        values.put("saida", timeFormat.format(presenca.getSaida()));
        return db.insert("Evento", null, values);
    }
    public List<Presenca> listar(int tipo) {
        String sqlBusca;
        List<Presenca> presencas = new ArrayList<>();


        sqlBusca = "SELECT * FROM Presenca";
        Cursor cursor = db.rawQuery(sqlBusca, null);

        while (cursor.moveToNext()) {
            try {
                int Usuario_id = cursor.getInt(0);
                int Evento_id = cursor.getInt(1);
                Time entrada = new Time(timeFormat.parse(cursor.getString(2)).getTime());
                Time saida = new Time(timeFormat.parse(cursor.getString(3)).getTime());
                presencas.add(new Presenca(Usuario_id, Evento_id, entrada, saida));
            } catch (Exception e) {
                e.printStackTrace();
                // Você pode adicionar um Toast ou log adicional aqui, se quiser indicar erro na conversão de um registro.
            }
        }
        cursor.close();
        return presencas;
    }

    public Presenca getUsuario_Evento(int userId, int eventId) {
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Presenca WHERE Usuario_id = ? AND Evento_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(eventId)}
        );
        if (cursor.moveToFirst()) {
            Time entrada = Time.valueOf(cursor.getString(2));
            Time saida = cursor.isNull(3) ? null : Time.valueOf(cursor.getString(3));
            cursor.close();
            return new Presenca(userId, eventId, entrada, saida);
        }
        cursor.close();
        return null;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}