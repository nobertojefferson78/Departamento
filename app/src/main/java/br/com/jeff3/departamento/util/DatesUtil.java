package br.com.jeff3.departamento.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jefferson on 25/06/2015.
 */
public class DatesUtil {

    public static String dateToString(int year, int monthOfYear, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        Date data = calendar.getTime();

        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dataFormatada = format.format(data);

        return dataFormatada;
    }

    public static String dateToString(Date data){
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String dataFormatada = format.format(data);
        return dataFormatada;
    }

    public static Date getDate(int year, int monthOfYear, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        Date data = calendar.getTime();

        return data;
    }


}