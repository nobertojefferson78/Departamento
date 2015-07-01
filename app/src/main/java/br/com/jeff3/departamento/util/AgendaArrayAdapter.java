package br.com.jeff3.departamento.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.jeff3.departamento.R;
import br.com.jeff3.departamento.modelo.Agenda;

/**
 * Created by jefferson on 30/06/2015.
 */
public class AgendaArrayAdapter extends ArrayAdapter<Agenda> {


    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;

    public AgendaArrayAdapter(Context context, int resource){
        super(context, resource);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(resource, parent, false);

            viewHolder.txtNome = (TextView)view.findViewById(R.id.txtNome);
            viewHolder.txtSigla = (TextView)view.findViewById(R.id.txtSigla);
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            view = convertView;
        }

        Agenda agenda = getItem(position);


        viewHolder.txtNome.setText(agenda.getDepartamento());
        viewHolder.txtSigla.setText(agenda.getProfessor());
        return view;
    }

    static class ViewHolder
    {
        TextView txtNome, txtSigla;
    }
}