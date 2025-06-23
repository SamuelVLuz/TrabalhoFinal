package com.example.trabalhofinal;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Time;
import java.time.Duration;
import java.util.Date;

public class Presenca implements Parcelable {
    private int Usuario_id;
    private int Evento_id;

    private Time entrada;
    private Time saida;
    public Presenca(int Usuario_id, int Evento_id, Time entrada, Time saida) {
        this.Usuario_id = Usuario_id;
        this.Evento_id = Evento_id;
        this.entrada = entrada;
        this.saida = saida;
    }

    public Presenca(int Usuario_id, int Evento_id, Time entrada) {
        this.Usuario_id = Usuario_id;
        this.Evento_id = Evento_id;
        this.entrada = entrada;
    }
    public Presenca(Time entrada, Time saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

    protected Presenca(Parcel in) {
        Usuario_id = in.readInt();
        Evento_id = in.readInt();
        entrada = new Time(in.readLong());
        saida = new Time(in.readLong());
    }

    public static final Creator<Presenca> CREATOR = new Creator<Presenca>() {
        @Override
        public Presenca createFromParcel(Parcel in) {
            return new Presenca(in);
        }

        @Override
        public Presenca[] newArray(int size) {
            return new Presenca[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Usuario_id);
        dest.writeInt(Evento_id);
        dest.writeLong(entrada.getTime());
        dest.writeLong(saida.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getUsuario_id() {
        return Usuario_id;
    }

    public int getEvento_id() {
        return Evento_id;
    }

    public Time getEntrada() {
        return entrada;
    }

    public Time getSaida() {
        return saida;
    }

    public void setUsuario_id(int usuario_id) {
        Usuario_id = usuario_id;
    }

    public void setEvento_id(int evento_id) {
        Evento_id = evento_id;
    }

    public void setEntrada(Time entrada) {
        this.entrada = entrada;
    }

    public void setSaida(Time saida) {
        this.saida = saida;
    }
}