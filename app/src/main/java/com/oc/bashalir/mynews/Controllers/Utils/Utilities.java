package com.oc.bashalir.mynews.Controllers.Utils;

import android.util.Log;

import com.oc.bashalir.mynews.Models.ArticleSearch;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class Utilities {

    public String DateShortFormatter(String dateSend,String pattern){

       SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        ParsePosition pos=new ParsePosition(0);
        Date dateNews = formatter.parse(dateSend,pos);

        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(dateNews);

    }

    public String DateShortFormatterSearch(ArticleSearch.Response.Doc articleSearch ){
        String articleDate="";
        if (articleSearch.getPubDate().contains("Z")){
            Log.e("TAG","KOUKOU" );
            articleDate = new Utilities().DateShortFormatter(articleSearch.getPubDate(), "yyyy-MM-dd'T'HH:mm:ss'Z'");}
        else{
            articleDate = new Utilities().DateShortFormatter(articleSearch.getPubDate(), "yyyy-MM-dd'T'HH:mm:ssZZZZZ");}

            return articleDate;
    }
    public okhttp3.OkHttpClient.Builder debugRetrofit(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        return httpClient;
    };

}
