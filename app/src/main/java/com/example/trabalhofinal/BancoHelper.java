package com.example.trabalhofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "agenda.db";
    private static final int DATABASE_VERSION = 1;

    public BancoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUsuario = "CREATE TABLE Usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "cpf VARCHAR (11) NOT NULL, " +
                "email TEXT NOT NULL, " +
                "telefone TEXT NOT NULL)";

        db.execSQL(sqlUsuario);

        String sqlEvento = "CREATE TABLE Evento (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "descricao TEXT NOT NULL, " +
                "data DATE NOT NULL, " +
                "inicio TIME NOT NULL, " +
                "fim TIME NOT NULL, " +
                "participacao TIME NOT NULL)";

        db.execSQL(sqlEvento);

        String sqlPresenca = "CREATE TABLE Presenca (" +
                "Usuario_id INT NOT NULL, " +
                "Evento_id INT NOT NULL, " +
                "entrada TIME NOT NULL, " +
                "saida TIME NOT NULL, " +
                "PRIMARY KEY (Evento_id, Usuario_id)," +
                "FOREIGN KEY (Usuario_id) REFERENCES Usuario(id) ON DELETE NO ACTION ON UPDATE NO ACTION, " +
                "FOREIGN KEY (Evento_id) REFERENCES Evento(id) ON DELETE NO ACTION ON UPDATE NO ACTION)";

        db.execSQL(sqlPresenca);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Usuario");
        db.execSQL("DROP TABLE IF EXISTS Evento");
        db.execSQL("DROP TABLE IF EXISTS Presenca");
        onCreate(db);
    }
}
