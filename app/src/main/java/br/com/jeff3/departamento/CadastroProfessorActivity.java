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
import br.com.jeff3.departamento.dominio.RepositorioProfessor;
import br.com.jeff3.departamento.modelo.Departamento;
import br.com.jeff3.departamento.modelo.Professor;


public class CadastroProfessorActivity extends ActionBarActivity {
    private EditText edtNome;
    private EditText edtDisciplina;
    private Spinner spnDepProf;

    private ArrayAdapter<String> adpDepProf;
    private ArrayList<Departamento> depList;
    private DataBase dataBase;
    private SQLiteDatabase connection;
    private RepositorioDepartamento rpDepartamento;
    private Professor professor;
    private RepositorioProfessor rpProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_professor);

        edtNome = (EditText)findViewById(R.id.edtNomeProfessor);
        edtDisciplina = (EditText)findViewById(R.id.edtDisciplina);
        spnDepProf = (Spinner)findViewById(R.id.spnDepartamentoProfessor);

        Bundle bundle = getIntent().getExtras();

        //verifica se recuperou e o parametro eh departmento
        if((bundle != null) && (bundle.containsKey(ProfessorActivity.PAR_PROFESSOR))){
            professor = (Professor)bundle.getSerializable(ProfessorActivity.PAR_PROFESSOR);
            preencheDados();

        }else  professor = new Professor();

        try{
            dataBase = new DataBase(this);
            connection = dataBase.getReadableDatabase();
            rpDepartamento =  new RepositorioDepartamento(connection);
            rpProfessor = new RepositorioProfessor(connection);

            adpDepProf = ViewHelper.createArrayAdapter(this, spnDepProf);


        }catch (SQLException ex){
            MensageBox.show(this, "Erro no banco: " + ex.getMessage(), "ERRO!");
        }

        depList = rpDepartamento.buscaListaDepartamentos(this);

        for(int i = 0; i < depList.size(); i++){
            Departamento dep = depList.get(i);
            adpDepProf.add(dep.getSigla());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);

        if(professor.getId() != 0)
            menu.getItem(2).setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.action_salvar:
                salvar();

                finish();
                break;
            case R.id.action_cancelar:

                finish();
                break;
            case R.id.action_excluir:
                excluir();

                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void preencheDados(){
        edtNome.setText(professor.getNome());
        edtDisciplina.setText(professor.getDisciplina());


    }

    public void salvar(){
        try{
            Departamento dep = depList.get(spnDepProf.getSelectedItemPosition());

            professor.setNome(edtNome.getText().toString());
            professor.setDisciplina(edtDisciplina.getText().toString());
            professor.setDepartamento(dep.getSigla());


            if(professor.getId() == 0) {
                rpProfessor.inserir(professor);
            }else{
                rpProfessor.alterar(professor);
            }
        }catch (Exception ex){
            MensageBox.show(this, "Erro ao inserir contato: " +ex.getMessage(), "ERRO!");
        }
    }

    public void excluir(){
        try{
            rpProfessor.excluir(professor.getId());
        }catch (Exception ex){
            MensageBox.show(this, "Erro ao excluir contato: " +ex.getMessage(), "ERRO!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connection != null){
            connection.close();
        }
    }
}
