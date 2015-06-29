package br.com.jeff3.departamento.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.jeff3.departamento.R;
import br.com.jeff3.departamento.modelo.Professor;


public class ProfessorArrayAdapter extends ArrayAdapter<Professor>{


    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;

    public ProfessorArrayAdapter(Context context, int resource){
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

        Professor professor = getItem(position);


        viewHolder.txtNome.setText(professor.getNome());
        viewHolder.txtSigla.setText(professor.getDepartamento().getSigla());
        return view;
    }

    static class ViewHolder
    {
        TextView txtNome, txtSigla;
    }
}