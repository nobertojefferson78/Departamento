package br.com.jeff3.departamento;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import br.com.jeff3.departamento.app.MensageBox;


public class AgendaActivity extends ActionBarActivity implements View.OnClickListener{

    private Button btnAgendar;
    private Button btnListarAgendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        btnAgendar = (Button)findViewById(R.id.btnCadastrarAgenda);
        btnListarAgendas = (Button)findViewById(R.id.btnListar);

        btnListarAgendas.setOnClickListener(this);
        btnAgendar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agenda, menu);
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
        Intent it;
        if(v == btnAgendar) {
            it = new Intent(this, CadastroAgendaActivity.class);
            startActivityForResult(it, 0);
        }else{
            //MensageBox.show(this, "Atencao", "Em manutencao");
            it = new Intent(this, ListaAgendaActivity.class);
            startActivityForResult(it, 0);
        }
    }
}
