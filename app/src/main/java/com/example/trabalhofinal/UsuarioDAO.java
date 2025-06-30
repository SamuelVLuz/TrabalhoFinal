package com.example.trabalhofinal;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
public class UsuarioDAO {
    private SQLiteDatabase db;
    public UsuarioDAO(Context context) {
        BancoHelper helper = new BancoHelper(context);
        db = helper.getWritableDatabase();
    }
    public long inserir(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("cpf", usuario.getCpf());
        values.put("email", usuario.getEmail());
        values.put("telefone", usuario.getTelefone());
        return db.insert("Usuario", null, values);
    }
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Usuario", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            String cpf = cursor.getString(2);
            String email = cursor.getString(3);
            String telefone = cursor.getString(4);
            usuarios.add(new Usuario(id, nome, cpf, email, telefone));
        }
        cursor.close();
        return usuarios;
    }

    public Usuario getById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM Usuario WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            String nome = cursor.getString(1);
            String cpf = cursor.getString(2);
            String email = cursor.getString(3);
            String telefone = cursor.getString(4);
            cursor.close();
            return new Usuario(id, nome, cpf, email, telefone);
        }
        cursor.close();
        return null;
    }

    public Usuario getByCpf(String cpf) {
        Cursor cursor = db.rawQuery("SELECT * FROM Usuario WHERE cpf = ?", new String[]{cpf});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            cursor.close();
            return new Usuario(id, nome, cpf);
        }
        cursor.close();
        return null;
    }
}