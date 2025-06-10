package com.example.trabalhofinal;

import java.sql.Time;
import java.time.Duration;
import java.util.Date;

public class Evento {
    private int id;
    private String nome;
    private String descricao;
    private Date data;
    private Time inicio;
    private Time fim;
    private Duration participacao;
    public Evento(int id, String nome, String descricao, Date data, Time inicio, Time fim, Duration participacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.inicio = inicio;
        this.fim = fim;
        this.participacao = participacao;
    }
    public Evento(String nome, String descricao, Date data, Time inicio, Time fim, Duration participacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.inicio = inicio;
        this.fim = fim;
        this.participacao = participacao;
    }
    public Evento(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }

    public Date getData() {
        return data;
    }

    public Time getInicio() {
        return inicio;
    }

    public Time getFim() {
        return fim;
    }

    public Duration getParticipacao() {
        return participacao;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setInicio(Time inicio) {
        this.inicio = inicio;
    }

    public void setFim(Time fim) {
        this.fim = fim;
    }

    public void setParticipacao(Duration participacao) {
        this.participacao = participacao;
    }
}