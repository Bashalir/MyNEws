package com.oc.bashalir.mynews.Controllers.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    public String DateShortFormatter(String dateSend,String pattern){

       SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        ParsePosition pos=new ParsePosition(0);
        Date dateNews = formatter.parse(dateSend,pos);

        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(dateNews);

    }


}
