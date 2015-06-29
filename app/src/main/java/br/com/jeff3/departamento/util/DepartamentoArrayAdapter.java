package br.com.jeff3.departamento.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.jeff3.departamento.R;
import br.com.jeff3.departamento.modelo.Departamento;

/**
 * Created by jefferson on 25/06/2015.
 * extendendendo do ArrayAdapter é obrigatoriamente necessario criar um construtor para ele
 */
public class DepartamentoArrayAdapter extends ArrayAdapter<Departamento>{

    //necessaria por causa que sobrescreveu o metodo getView
    private int resource = 0;
    //armazenar a interface
    private LayoutInflater inflater;
    //usado para recuperar informações da pasta recurso
    private Context context;
    /**
     *
     * @param context - quem chama
     * @param resource - id do layout
     */
    public DepartamentoArrayAdapter(Context context, int resource){
        //chamando o construtor do arrayAdapter
        super(context, resource);
        /**
         * Utiliza primeiramente do context para carregar a interface
         */
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    //responsavel pela visualizacao do listView para o arrayAdapter, ou seja, ele que faz a magica
    //do listView pegar os dados do arrayAdapter

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;
        if(convertView == null){
            //criando objeto do tipo viewHolder
            viewHolder = new ViewHolder();
            //CARREGANDO A MINHA INTERFACE
            view = inflater.inflate(resource, parent, false);

            //CHAMANDO CADA OBJETO DO LAYOUT
            viewHolder.txtNome = (TextView)view.findViewById(R.id.txtNome);
            viewHolder.txtSigla = (TextView)view.findViewById(R.id.txtSigla);
            //ASSOCIANDO O VIEWhOLDER AO VIEW, ou seja guardando
            view.setTag(viewHolder);

        }else{
            //pegando o objeto guardado anteriormente
            viewHolder = (ViewHolder)convertView.getTag();
            view = convertView;
        }

        Departamento departamento = getItem(position);


        viewHolder.txtNome.setText(departamento.getNome());
        viewHolder.txtSigla.setText(departamento.getSigla());
        return view;
    }

    //USADA PARA OTIMIZAR A CONEXÃO COM A INTERFACE E NAO FICAR CHAMANDO O METODO TODA HORA
    static class ViewHolder
    {
        TextView txtNome, txtSigla;
    }
}
