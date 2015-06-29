package br.com.jeff3.departamento;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.com.jeff3.departamento.app.MensageBox;
import br.com.jeff3.departamento.database.DataBase;
import br.com.jeff3.departamento.dominio.RepositorioDepartamento;
import br.com.jeff3.departamento.modelo.Departamento;


public class CadastroDepartamentoActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText edtNome;
    private EditText edtSigla;
    private ActionBar ab;

    private DataBase dataBase;
    private SQLiteDatabase conection;
    private RepositorioDepartamento rpDepartamento;
    private Departamento departamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_departamento);

        ab = getSupportActionBar();
        ab.setTitle("Departamento");
        ab.setSubtitle("cadastrar");
        ab.setBackgroundDrawable(getResources().getDrawable(R.color.material_blue_grey_800));

        edtNome = (EditText)findViewById(R.id.edtNomeDepartamento);
        edtSigla = (EditText)findViewById(R.id.edtSiglaDepartamento);

        Bundle bundle = getIntent().getExtras();

        //verifica se recuperou e o parametro eh departmento
        if((bundle != null) && (bundle.containsKey(DepartamentoActivity.PAR_DEPARTAMENTO))){
            this.departamento = (Departamento)bundle.getSerializable("DEPARTAMENTO");
            preencheDados();

        }else  departamento = new Departamento();



        try {
            dataBase = new DataBase(this);
            conection = dataBase.getWritableDatabase();
            rpDepartamento = new RepositorioDepartamento(conection);
        }catch (SQLException ex){
            MensageBox.show(this, "Erro no banco: " +ex.getMessage(), "ERRO!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);

        if(departamento.getId() != 0){
            menu.getItem(2).setVisible(true);
            ab.setSubtitle("Editar");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_salvar:
                salvar();
                conection.close();
                finish();
                break;
            case R.id.action_cancelar:
                conection.close();
                finish();
                break;
            case R.id.action_excluir:
                excluir();
                conection.close();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    public void salvar(){
        try {


            departamento.setNome(edtNome.getText().toString());
            departamento.setSigla(edtSigla.getText().toString());

            if(departamento.getId() == 0) {
                rpDepartamento.inserir(departamento);
            }else{
                rpDepartamento.alterar(departamento);
            }
        }catch (Exception ex){
            MensageBox.show(this, "Erro ao inserir departamento: " + ex.getMessage(), "ERRO!");
        }
    }

    private void excluir(){
        try{
            rpDepartamento.excluir(departamento.getId());
        }catch (Exception ex){
            MensageBox.show(this, "Erro ao excluir contato: " +ex.getMessage(), "ERRO!");
        }
    }

    private void preencheDados(){
        edtNome.setText(this.departamento.getNome());
        edtSigla.setText(this.departamento.getSigla());

    }
}
