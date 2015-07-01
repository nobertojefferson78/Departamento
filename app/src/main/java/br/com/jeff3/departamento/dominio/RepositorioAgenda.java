package br.com.jeff3.departamento.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import br.com.jeff3.departamento.R;
import br.com.jeff3.departamento.modelo.Agenda;
import br.com.jeff3.departamento.modelo.Professor;
import br.com.jeff3.departamento.util.AgendaArrayAdapter;
import br.com.jeff3.departamento.util.ProfessorArrayAdapter;

/**
 * Created by jefferson on 28/06/2015.
 */
public class RepositorioAgenda {

    private SQLiteDatabase conection;

    public RepositorioAgenda(SQLiteDatabase conection){

        this.conection = conection;
    }

    private ContentValues preencheContato(Agenda agenda){
        ContentValues values = new ContentValues();

        values.put(Agenda.DEPARTAMENTO, agenda.getDepartamento());
        values.put(Agenda.PROFESSOR, agenda.getProfessor());
        values.put(Agenda.RECURSO, agenda.getRecurso());
        values.put(Agenda.HORARIO, agenda.getHorario());
        values.put(Agenda.OBSERVACOES, agenda.getObservacoes());
        values.put(Agenda.TURNO, agenda.getTurno());
        values.put(Agenda.DATA, agenda.getData().getTime());

        return values;
    }

    private Agenda carregaAgenda(Cursor cursor){
        Agenda agenda = new Agenda();

        agenda.setId(cursor.getLong(cursor.getColumnIndex(agenda.ID)));
        agenda.setDepartamento(cursor.getString(cursor.getColumnIndex(agenda.DEPARTAMENTO)));
        agenda.setProfessor(cursor.getString(cursor.getColumnIndex(agenda.PROFESSOR)));
        agenda.setTurno(cursor.getString(cursor.getColumnIndex(agenda.TURNO)));
        agenda.setData(new Date(cursor.getLong(cursor.getColumnIndex(agenda.DATA))));
        agenda.setHorario(cursor.getString(cursor.getColumnIndex(agenda.HORARIO)));
        agenda.setRecurso(cursor.getString(cursor.getColumnIndex(agenda.RECURSO)));
        agenda.setObservacoes(cursor.getString(cursor.getColumnIndex(agenda.OBSERVACOES)));

        return agenda;
    }

    public void inserir(Agenda agenda){
        ContentValues values = preencheContato(agenda);

        conection.insertOrThrow(Agenda.TABELA, null, values);
    }

    public void alterar(Agenda agenda){
        ContentValues values = preencheContato(agenda);

        //declarando o id para atualizar o banco de dados
        conection.update(Agenda.TABELA, values, "_id = ? ", new String[]{String.valueOf(agenda.getId())});
    }

    public void excluir(long id){
        conection.delete(Agenda.TABELA, "_id = ? ", new String[]{String.valueOf(id)});
    }

    public ArrayList<Agenda> buscaListaAgendas(Context context){
        ArrayList<Agenda> agendas = new ArrayList<Agenda>();

        Cursor cursor = conection.query(Agenda.TABELA, null, null, null, null, null, null);

        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do{

                Agenda agenda = new Agenda();

                agenda = carregaAgenda(cursor);

                agendas.add(agenda);

            }while (cursor.moveToNext());
        }

        return agendas;
    }

    public AgendaArrayAdapter buscaAgendas(Context context){
        AgendaArrayAdapter adpAgendas = new AgendaArrayAdapter(context, R.layout.item_lista);

        Cursor cursor = conection.query(Agenda.TABELA, null, null, null, null, null, null);

        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do {

                Agenda agenda = new Agenda();

                agenda = carregaAgenda(cursor);

                adpAgendas.add(agenda);
            }while(cursor.moveToNext()); //percorrer as colunas no banco de dados
        }
        return adpAgendas;
    }
}