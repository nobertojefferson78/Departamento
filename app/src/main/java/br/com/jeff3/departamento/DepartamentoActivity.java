package br.com.jeff3.departamento;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import br.com.jeff3.departamento.app.MensageBox;
import br.com.jeff3.departamento.database.DataBase;
import br.com.jeff3.departamento.dominio.RepositorioDepartamento;
import br.com.jeff3.departamento.modelo.Departamento;


public class DepartamentoActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener{
    public static String PAR_DEPARTAMENTO = "DEPARTAMENTO";

    private ImageButton btiCadastrarDepartamento;
    private ListView lstDepartamento;
    private EditText edtPesquisaDepartamento;

    private ArrayAdapter<Departamento> adpDepartamento;
    private ActionBar ab;
    //variaveis para conececao com o banco de dados
    private DataBase dataBase;
    private SQLiteDatabase conection;
    private RepositorioDepartamento rpDepartamento;

    private FiltraDados filtraDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departamento);

        ab = getSupportActionBar();
        ab.setTitle("Departamentos");
        ab.setSubtitle("lista");
        ab.setBackgroundDrawable(getResources().getDrawable(R.color.material_blue_grey_800));

        btiCadastrarDepartamento = (ImageButton)findViewById(R.id.btiCadastrarDepartamento);
        lstDepartamento = (ListView)findViewById(R.id.lstDepartamento);
        edtPesquisaDepartamento = (EditText)findViewById(R.id.edtPesquisaDepartamento);

        lstDepartamento.setOnItemClickListener(this);

        btiCadastrarDepartamento.setOnClickListener(this);

        try {
            dataBase = new DataBase(this);
            //usando o metodo para escrever no banco de dados
            conection = dataBase.getWritableDatabase();
            //realizar a conection com o banco
            rpDepartamento = new RepositorioDepartamento(conection);
            //adaptador de contatos para exibir na tela a lista de contatos salvas
            adpDepartamento = rpDepartamento.buscaDepartamentos(this);
            //usando o adaptador na listView
            lstDepartamento.setAdapter(adpDepartamento);


        }catch (SQLException ex){
            MensageBox.show(this, "Erro no banco: " + ex.getMessage(), "ERRO!");
        }


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SearchView sv = new SearchView(this);

        filtraDados = new FiltraDados(adpDepartamento);

        MenuItem m1 = menu.add(0, 0, 0, "Item 1");
        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        m1.setActionView(sv);


        getMenuInflater().inflate(R.menu.menu_departamento, menu);
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
    public void onClick(View v){
        Intent it = new Intent(this, CadastroDepartamentoActivity.class);
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
        Departamento departamento = adpDepartamento.getItem(position);
        //MensageBox.show(this, "O nome do boy; ", departamento.getNome());
        Intent it = new Intent(this, CadastroDepartamentoActivity.class);
        it.putExtra("DEPARTAMENTO", departamento);

        startActivityForResult(it, 0);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adpDepartamento = rpDepartamento.buscaDepartamentos(this);
        lstDepartamento.setAdapter(adpDepartamento);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private class FiltroDados implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            Log.i("Sricpt", "onQueryTextSubmit -> " + query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Log.i("Sricpt", "onQueryTextChange -> " + newText);
            return false;
        }
    }

    private class FiltraDados implements TextWatcher {

        //cria-se um arrayAdapter do tipo que eh usado na interface
        private ArrayAdapter<Departamento> arrayAdapter;

        //inicializa com o construtor recebendo  o parametro do array que utilizou
        private FiltraDados(ArrayAdapter<Departamento> arrayAdapter){
            this.arrayAdapter = arrayAdapter;
        }

        public void setArrayAdapter(ArrayAdapter<Departamento> arrayAdapter){
            this.arrayAdapter = arrayAdapter;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //metodo usado para pesquisar de acordo com o texto digitado
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            arrayAdapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
