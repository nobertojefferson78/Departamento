package br.com.jeff3.departamento;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import br.com.jeff3.departamento.app.MensageBox;
import br.com.jeff3.departamento.app.ViewHelper;
import br.com.jeff3.departamento.database.DataBase;
import br.com.jeff3.departamento.dominio.RepositorioDepartamento;
import br.com.jeff3.departamento.modelo.Departamento;


public class CadastroProfessorActivity extends ActionBarActivity {
    private EditText edtNome;
    private EditText edtDisciplina;
    private Spinner spnDepProf;

    private ArrayAdapter<String> adpDepProf;
    private ArrayList<Departamento> depList;
    private DataBase dataBase;
    private SQLiteDatabase connection;
    private RepositorioDepartamento rpDepartamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_professor);

        edtNome = (EditText)findViewById(R.id.edtNomeProfessor);
        edtDisciplina = (EditText)findViewById(R.id.edtDisciplina);
        spnDepProf = (Spinner)findViewById(R.id.spnDepartamentoProfessor);

        try{
            dataBase = new DataBase(this);
            connection = dataBase.getReadableDatabase();
            rpDepartamento =  new RepositorioDepartamento(connection);

            adpDepProf = ViewHelper.createArrayAdapter(this, spnDepProf);


        }catch (SQLException ex){
            MensageBox.show(this, "Erro no banco: " + ex.getMessage(), "ERRO!");
        }

        depList = rpDepartamento.buscaListaDepartamentos(this);

        Cursor cursor = connection.query(Departamento.TABELA, null, null, null, null, null, null);

        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do{

                Departamento departamento = new Departamento();

                departamento.setId(cursor.getLong(cursor.getColumnIndex(departamento.ID)));
                departamento.setNome(cursor.getString(cursor.getColumnIndex(departamento.NOME)));
                departamento.setSigla(cursor.getString(cursor.getColumnIndex(departamento.SIGLA)));

                adpDepProf.add(departamento.getSigla());

            }while (cursor.moveToNext());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_salvar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
