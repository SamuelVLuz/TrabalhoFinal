package com.example.trabalhofinal;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    public Usuario(int id, String nome, String cpf, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }
    public Usuario(String nome, String cpf, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    public Usuario(int id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }
    public Usuario(String nome) {
        this.nome = nome;
    }

    protected Usuario(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        cpf = in.readString();
        email = in.readString();
        telefone = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(cpf);
        dest.writeString(email);
        dest.writeString(telefone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}