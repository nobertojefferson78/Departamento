package br.com.jeff3.departamento;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.*;

import br.com.jeff3.departamento.app.MensageBox;
import br.com.jeff3.departamento.database.DataBase;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private Button btnAgenda;
    private Button btnProfessor;
    private Button btnDepartamento;

    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAgenda       = (Button)findViewById(R.id.btnAgendamento);
        btnProfessor    = (Button)findViewById(R.id.btnProfessor);
        btnDepartamento = (Button)findViewById(R.id.btnDepartamento);

        btnDepartamento.setOnClickListener(this);
        btnAgenda.setOnClickListener(this);
        btnProfessor.setOnClickListener(this);

        try {
            //criando a base de dados
            dataBase = new DataBase(this);

        }catch (SQLException ex){
            MensageBox.show(this, "Erro no banco: " + ex.getMessage(), "ERRO!");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        Intent it;
        if(v == btnDepartamento){
            it = new Intent(this, DepartamentoActivity.class);
            startActivity(it);
        }else if(v == btnAgenda){
            it = new Intent(this, AgendaActivity.class);
            startActivity(it);
        }else if(v == btnProfessor){
            it = new Intent(this, ProfessorActivity.class);
            startActivity(it);
        }
    }
}
