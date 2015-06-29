package br.com.jeff3.departamento.modelo;

import java.io.Serializable;

/**
 * Created by jefferson on 28/06/2015.
 */
public class Professor implements Serializable{

    private long id;
    private String nome;
    private String disciplina;
    private Departamento departamento;

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

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString(){
        return nome + " " + departamento.getSigla();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
