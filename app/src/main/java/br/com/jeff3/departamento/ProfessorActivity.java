package br.com.jeff3.departamento;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import br.com.jeff3.departamento.app.MensageBox;
import br.com.jeff3.departamento.database.DataBase;
import br.com.jeff3.departamento.dominio.RepositorioProfessor;
import br.com.jeff3.departamento.modelo.Professor;


public class ProfessorActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    public static final String PAR_PROFESSOR = "PROFESSOR";
    private ImageButton btiCadastrarProfessor;
    private ListView lstProfessor;
    private EditText edtPesquisaProfessor;

    private ArrayAdapter<Professor> adpProfessor;
    private ActionBar ab;
    //variaveis para conececao com o banco de dados
    private DataBase dataBase;
    private SQLiteDatabase conection;
    private RepositorioProfessor rpProfessor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        btiCadastrarProfessor = (ImageButton)findViewById(R.id.btiCadastrarProfessor);

        btiCadastrarProfessor.setOnClickListener(this);

        ab = getSupportActionBar();
        ab.setTitle("Professors");
        ab.setSubtitle("lista");
        ab.setBackgroundDrawable(getResources().getDrawable(R.color.material_blue_grey_800));

        lstProfessor = (ListView)findViewById(R.id.lstProfessor);
        edtPesquisaProfessor = (EditText)findViewById(R.id.edtPesquisaProfessor);

        lstProfessor.setOnItemClickListener(this);

        btiCadastrarProfessor.setOnClickListener(this);

        try {
            dataBase = new DataBase(this);
            //usando o metodo para escrever no banco de dados
            conection = dataBase.getWritableDatabase();
            //realizar a conection com o banco
            rpProfessor = new RepositorioProfessor(conection);
            //adaptador de contatos para exibir na tela a lista de contatos salvas
            adpProfessor = rpProfessor.buscaProfessores(this);
            //usando o adaptador na listView
            lstProfessor.setAdapter(adpProfessor);


        }catch (SQLException ex){
            MensageBox.show(this, "Erro no banco: " + ex.getMessage(), "ERRO!");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_professor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, CadastroProfessorActivity.class);
        startActivityForResult(it, 0);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(conection != null){
            conection.close();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Professor professor = adpProfessor.getItem(position);
        Intent it = new Intent(this, CadastroProfessorActivity.class);
        it.putExtra(PAR_PROFESSOR, professor);

        startActivityForResult(it, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        adpProfessor = rpProfessor.buscaProfessores(this);
        lstProfessor.setAdapter(adpProfessor);
    }
}
