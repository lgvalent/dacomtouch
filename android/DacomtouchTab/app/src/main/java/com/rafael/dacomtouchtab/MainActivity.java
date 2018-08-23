package com.rafael.dacomtouchtab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView pagina = (WebView) findViewById(R.id.view);

        //Tela ligada
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Habilita o JavaScript nas paginas web
        pagina.getSettings().setJavaScriptEnabled(true);

        //Habilita o zoom nas páginas
        pagina.getSettings().setSupportZoom(true);

        //fará o browser carregar a pagina
        pagina.loadUrl("http://valentin.com.br/dacom/");
        pagina.setVerticalScrollBarEnabled(true);
        pagina.setHorizontalScrollBarEnabled(true);
        pagina.setWebViewClient(new WebViewClient());
    }
}
