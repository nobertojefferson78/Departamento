package br.com.jeff3.departamento.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.jeff3.departamento.R;
import br.com.jeff3.departamento.modelo.Departamento;
import br.com.jeff3.departamento.util.DepartamentoArrayAdapter;

/**
 * Created by jefferson on 28/06/2015.
 */
public class RepositorioDepartamento {

    private SQLiteDatabase conection;

    public RepositorioDepartamento(SQLiteDatabase conection){

        this.conection = conection;
    }

    private ContentValues preencheContato(Departamento departamento){
        ContentValues values = new ContentValues();

        values.put(Departamento.NOME, departamento.getNome());
        values.put(Departamento.SIGLA, departamento.getSigla());

        return values;
    }

    public void inserir(Departamento departamento){
        ContentValues values = preencheContato(departamento);

        conection.insertOrThrow(Departamento.TABELA, null, values);
    }

    public void alterar(Departamento departamento){
        ContentValues values = preencheContato(departamento);

        //declarando o id para atualizar o banco de dados
        conection.update(Departamento.TABELA, values, "_id = ? ", new String[]{String.valueOf(departamento.getId())});
    }

    public void excluir(long id){
        conection.delete(Departamento.TABELA, "_id = ? ", new String[]{String.valueOf(id)});
    }

    public ArrayList<Departamento> buscaListaDepartamentos(Context context){
        ArrayList<Departamento> departamentos = new ArrayList<Departamento>();

        Cursor cursor = conection.query(Departamento.TABELA, null, null, null, null, null, null);

        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do{

                Departamento departamento = new Departamento();

                departamento.setId(cursor.getLong(cursor.getColumnIndex(departamento.ID)));
                departamento.setNome(cursor.getString(cursor.getColumnIndex(departamento.NOME)));
                departamento.setSigla(cursor.getString(cursor.getColumnIndex(departamento.SIGLA)));

                departamentos.add(departamento);

            }while (cursor.moveToNext());
        }

        return departamentos;
    }

    public DepartamentoArrayAdapter buscaDepartamentos(Context context){
        DepartamentoArrayAdapter adpContatos = new DepartamentoArrayAdapter(context, R.layout.item_lista);

        Cursor cursor = conection.query(Departamento.TABELA, null, null, null, null, null, null);

        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do {

                Departamento departamento = new Departamento();

                departamento.setId(cursor.getLong(cursor.getColumnIndex(departamento.ID)));
                departamento.setNome(cursor.getString(cursor.getColumnIndex(departamento.NOME)));
                departamento.setSigla(cursor.getString(cursor.getColumnIndex(departamento.SIGLA)));

                adpContatos.add(departamento);
            }while(cursor.moveToNext()); //percorrer as colunas no banco de dados
        }
        return adpContatos;
    }
}
