package br.com.jeff3.departamento.database;

import android.content.Context;
import android.database.sqlite.*;

/**
 * Created by jefferson on 28/06/2015.
 */
public class DataBase extends SQLiteOpenHelper{

    public static final String DB_DEPARTAMENTO = "AGENDA";


    public DataBase(Context context){

        super(context, DB_DEPARTAMENTO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateDepartamento());
        db.execSQL(ScriptSQL.getCreateProfessor());
        db.execSQL(ScriptSQL.getCreateAgenda());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}