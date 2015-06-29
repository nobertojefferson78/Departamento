package br.com.jeff3.departamento.modelo;

import java.io.Serializable;

/**
 * Created by jefferson on 28/06/2015.
 */
public class Departamento implements Serializable{
    public static String ID = "_id";
    public static String TABELA = "DEPARTAMENTO";

    public static String NOME = "NOME";
    public static String SIGLA = "SIGLA";


    private long id;
    private String nome;
    private String sigla;

    public Departamento(){
        id = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public String toString(){
        return nome + " " +sigla;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
