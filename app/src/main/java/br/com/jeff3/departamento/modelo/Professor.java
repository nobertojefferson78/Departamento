package br.com.jeff3.departamento.modelo;

import java.io.Serializable;

/**
 * Created by jefferson on 28/06/2015.
 */
public class Professor implements Serializable{
    public static String ID = "_id";
    public static String TABELA = "PROFESSOR";
    public static String NOME = "NOME";
    public static String DISCIPLINA = "DISCIPLINA";
    public static String DEPARTAMENTO = "DEPARTAMENTO";

    private long id;
    private String nome;
    private String disciplina;
    private String departamento;

    public Professor(){
        id = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString(){
        return nome + " " + departamento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
