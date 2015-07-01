package br.com.jeff3.departamento;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.jeff3.departamento.app.MensageBox;
import br.com.jeff3.departamento.app.ViewHelper;
import br.com.jeff3.departamento.database.DataBase;
import br.com.jeff3.departamento.dominio.RepositorioAgenda;
import br.com.jeff3.departamento.dominio.RepositorioDepartamento;
import br.com.jeff3.departamento.dominio.RepositorioProfessor;
import br.com.jeff3.departamento.modelo.Agenda;
import br.com.jeff3.departamento.modelo.Departamento;
import br.com.jeff3.departamento.modelo.Professor;
import br.com.jeff3.departamento.util.DatesUtil;


public class CadastroAgendaActivity extends ActionBarActivity {
    private EditText edtDataAgenda;
    private EditText edtObservacoes;

    private RadioGroup rgTurno;

    private CheckBox cboxDataShow;
    private CheckBox cboxNoteBook;
    private CheckBox cboxHorario12;
    private CheckBox cboxHorario34;
    private CheckBox cboxHorario56;

    private Spinner spnDepartamento;
    private Spinner spnProfessor;

    private ArrayAdapter<String> adpDepartamentoAgenda;
    private ArrayList<Departamento> departamentoLista;
    private ArrayAdapter<String> adpProfessorAgenda;
    private ArrayList<Professor> professorLista;

    private DataBase dataBase;
    private SQLiteDatabase connection_read;
    private SQLiteDatabase connection_wirte;
    private RepositorioDepartamento rpDepartamento;
    private RepositorioAgenda rpAgenda;
    private Agenda agenda;
    private RepositorioProfessor rpProfessor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_agenda);
        conectarInerface();
        agenda = new Agenda();

        try{
            dataBase = new DataBase(this);
            connection_read = dataBase.getReadableDatabase();
            connection_wirte = dataBase.getWritableDatabase();

            rpAgenda = new RepositorioAgenda(connection_wirte);
            rpDepartamento =  new RepositorioDepartamento(connection_read);
            rpProfessor = new RepositorioProfessor(connection_read);

            adpDepartamentoAgenda = ViewHelper.createArrayAdapter(this, spnDepartamento);

            departamentoLista = rpDepartamento.buscaListaDepartamentos(this);

            for(int i = 0; i < departamentoLista.size(); i++){
                Departamento dep = departamentoLista.get(i);
                adpDepartamentoAgenda.add(dep.getSigla());
            }

            adpProfessorAgenda = ViewHelper.createArrayAdapter(this, spnProfessor);

            professorLista = rpProfessor.buscaListaProfessors(this);

            for (int i = 0; i < professorLista.size(); i++){
                Professor p = professorLista.get(i);
                adpProfessorAgenda.add(p.getNome() + "(" + p.getDepartamento()+")");
            }


        }catch (SQLException ex){
            MensageBox.show(this, "Erro no banco: " + ex.getMessage(), "ERRO!");
        }



    }

    @Override
    protected void onDestroy() {
        connection_wirte.close();
        connection_read.close();
        super.onDestroy();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);

        MenuItem m1 = menu.add(0, 0, 0, "Salvar");
        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        m1.setIcon(R.drawable.abc_ic_menu_paste_mtrl_am_alpha);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_salvar) {
            salvar();
            finish();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public void salvar(){
        try {
            agenda.setDepartamento(String.valueOf(spnDepartamento.getSelectedItem().toString()));
            agenda.setProfessor(String.valueOf(spnProfessor.getSelectedItem().toString()));

            if(cboxHorario12.isChecked() && cboxHorario34.isChecked() && cboxHorario56.isChecked()) {
                agenda.setHorario(cboxHorario12.getText().toString() + cboxHorario34.getText().toString() + cboxHorario56.getText().toString());
            }else if(cboxHorario12.isChecked() && cboxHorario34.isChecked()){
                agenda.setHorario(cboxHorario12.getText().toString() + cboxHorario34.getText().toString());
            }else if(cboxHorario12.isChecked() && cboxHorario56.isChecked()){
                agenda.setHorario(cboxHorario12.getText().toString() + cboxHorario34.getText().toString());
            }else if(cboxHorario34.isChecked() && cboxHorario56.isChecked()){
                agenda.setHorario(cboxHorario12.getText().toString() + cboxHorario56.getText().toString());
            }else if(cboxHorario12.isChecked()){
                agenda.setHorario(cboxHorario12.getText().toString());
            }else if(cboxHorario34.isChecked()){
                agenda.setHorario(cboxHorario34.getText().toString());
            }else{
                agenda.setHorario(cboxHorario56.getText().toString());
            }

            if(cboxNoteBook.isChecked() && cboxDataShow.isChecked()){
                agenda.setRecurso(cboxDataShow.getText().toString() + " e " + cboxNoteBook.getText().toString());
            }else if(cboxNoteBook.isChecked()){
                agenda.setRecurso(cboxNoteBook.getText().toString());
            }else{
                agenda.setRecurso(cboxDataShow.getText().toString());
            }

            agenda.setObservacoes(edtObservacoes.getText().toString());

            switch (rgTurno.getCheckedRadioButtonId()) {
                case R.id.rbMatutino:
                    agenda.setTurno("Matutino");
                    break;
                case R.id.rbVespertino:
                    agenda.setTurno("Vespertino");
                    break;
                case R.id.rbNoturno:
                    agenda.setTurno("Noturno");
                    break;
            }


            if(agenda.getId() == 0) {
                rpAgenda.inserir(agenda);
            }else{
                rpAgenda.alterar(agenda);
            }
        }catch (Exception ex){
            MensageBox.show(this, "Erro ao inserir agenda: " +ex.getMessage(), "ERRO!");
        }
    }

    public void conectarInerface(){
        edtDataAgenda = (EditText)findViewById(R.id.edtData);
        edtObservacoes = (EditText)findViewById(R.id.edtObservacoes);

        rgTurno = (RadioGroup)findViewById(R.id.rgTurno);

        ExibeDataListener edl = new ExibeDataListener();
        edtDataAgenda.setOnClickListener(edl);
        //edtDataAgenda.setOnFocusChangeListener(edl);
        edtDataAgenda.setKeyListener(null);

        spnDepartamento = (Spinner)findViewById(R.id.spnDepartamentoAgenda);
        spnProfessor = (Spinner)findViewById(R.id.spnProfessorAgenda);

        cboxDataShow = (CheckBox)findViewById(R.id.cboxDataShow);
        cboxNoteBook = (CheckBox)findViewById(R.id.cboxNote);
        cboxHorario12 = (CheckBox)findViewById(R.id.cboxHorario12);
        cboxHorario34 = (CheckBox)findViewById(R.id.cboxHorario34);
        cboxHorario56 = (CheckBox)findViewById(R.id.cboxHorario56);
    }

    private void exibeData(){
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(calendar.YEAR);
        int mes = calendar.get(calendar.MONTH);
        int dia = calendar.get(calendar.DAY_OF_MONTH);

        //usado para exibir o calendario
        DatePickerDialog dlg = new DatePickerDialog(this, new SelecionaDataListener(), ano, mes, dia);
        dlg.show();
    }

    private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener{

        @Override
        public void onClick(View v) {
            exibeData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                exibeData();
            }
        }
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String dataFormatada = DatesUtil.dateToString(year, monthOfYear, dayOfMonth);
            Date data = DatesUtil.getDate(year, monthOfYear, dayOfMonth);

            edtDataAgenda.setText(dataFormatada);
            agenda.setData(data);
        }
    }
}
