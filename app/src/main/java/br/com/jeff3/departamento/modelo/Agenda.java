package br.com.jeff3.departamento.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jefferson on 28/06/2015.
 */
public class Agenda implements Serializable{
    public static String TABELA = "AGENDA";
    public static String ID = "_id";
    public static String DEPARTAMENTO = "DEPARTAMENTO";
    public static String PROFESSOR = "PROFESSOR";
    public static String DATA = "DATA";
    public static String RECURSO = "RECURSO";
    public static String HORARIO = "HORARIO";
    public static String TURNO = "TURNO";
    public static String OBSERVACOES = "OBSERVACOES";

    private long id;
    private String departamento;
    private String professor;
    private Date data;
    private String recurso;
    private String horario;
    private String turno;
    private String observacoes;



    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }


    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
