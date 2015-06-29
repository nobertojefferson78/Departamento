package br.com.jeff3.departamento.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.jeff3.departamento.R;
import br.com.jeff3.departamento.modelo.Professor;
import br.com.jeff3.departamento.util.ProfessorArrayAdapter;

/**
 * Created by jefferson on 28/06/2015.
 */
public class RepositorioProfessor {

    private SQLiteDatabase conection;

    public RepositorioProfessor(SQLiteDatabase conection){

        this.conection = conection;
    }

    private ContentValues preencheContato(Professor professor){
        ContentValues values = new ContentValues();

        values.put(Professor.NOME, professor.getNome());
        values.put(Professor.DISCIPLINA, professor.getDisciplina());
        values.put(Professor.DEPARTAMENTO, String.valueOf(professor.getDepartamento()));

        return values;
    }

    public void inserir(Professor professor){
        ContentValues values = preencheContato(professor);

        conection.insertOrThrow(Professor.TABELA, null, values);
    }

    public void alterar(Professor professor){
        ContentValues values = preencheContato(professor);

        //declarando o id para atualizar o banco de dados
        conection.update(Professor.TABELA, values, "_id = ? ", new String[]{String.valueOf(professor.getId())});
    }

    public void excluir(long id){
        conection.delete(Professor.TABELA, "_id = ? ", new String[]{String.valueOf(id)});
    }

    public ArrayList<Professor> buscaListaProfessors(Context context){
        ArrayList<Professor> professors = new ArrayList<Professor>();

        Cursor cursor = conection.query(Professor.TABELA, null, null, null, null, null, null);

        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do{

                Professor professor = new Professor();

                professor.setId(cursor.getLong(cursor.getColumnIndex(professor.ID)));
                professor.setNome(cursor.getString(cursor.getColumnIndex(professor.NOME)));
                professor.setDisciplina(cursor.getString(cursor.getColumnIndex(professor.DISCIPLINA)));
                professor.setDepartamento(cursor.getString(cursor.getColumnIndex(professor.DEPARTAMENTO)));

                professors.add(professor);

            }while (cursor.moveToNext());
        }

        return professors;
    }

    public ProfessorArrayAdapter buscaProfessores(Context context){
        ProfessorArrayAdapter adpContatos = new ProfessorArrayAdapter(context, R.layout.item_lista);

        Cursor cursor = conection.query(Professor.TABELA, null, null, null, null, null, null);

        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do {

                Professor professor = new Professor();

                professor.setId(cursor.getLong(cursor.getColumnIndex(professor.ID)));
                professor.setNome(cursor.getString(cursor.getColumnIndex(professor.NOME)));
                professor.setDisciplina(cursor.getString(cursor.getColumnIndex(professor.DISCIPLINA)));
                professor.setDepartamento(cursor.getString(cursor.getColumnIndex(professor.DEPARTAMENTO)));

                adpContatos.add(professor);
            }while(cursor.moveToNext()); //percorrer as colunas no banco de dados
        }
        return adpContatos;
    }

    public ArrayList<Professor> buscaListaDepartamentos(Context context){
        ArrayList<Professor> professores = new ArrayList<Professor>();

        Cursor cursor = conection.query(Professor.TABELA, null, null, null, null, null, null);

        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do{

                Professor professor = new Professor();

                professor.setId(cursor.getLong(cursor.getColumnIndex(professor.ID)));
                professor.setNome(cursor.getString(cursor.getColumnIndex(professor.NOME)));
                professor.setDisciplina(cursor.getString(cursor.getColumnIndex(professor.DISCIPLINA)));
                professor.setDepartamento(cursor.getString(cursor.getColumnIndex(professor.DEPARTAMENTO)));

                professores.add(professor);

            }while (cursor.moveToNext());
        }

        return professores;
    }
}
