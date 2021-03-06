package com.example.qldathangsanpham.ui.product.chart;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class SanPhamChart extends AppCompatActivity {
    private final String TAG = SanPhamChart.class.getName();

    WebView webView;
    List<SanPham> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_chart);

        if (getIntent().hasExtra("LIST")) {
            list = (List<SanPham>) getIntent().getSerializableExtra("LIST");
        }

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new JavaScriptInterface(), "Android");

        webView.loadUrl("file:///android_asset/GeoChart.html");
    }

    private class JavaScriptInterface {
        Context context;

        @JavascriptInterface
        public String getCountryData() throws JSONException {
            JSONArray array = new JSONArray();

            HashMap<String, Integer> count = new HashMap<>();

            for (SanPham sp : list) {
                String key = sp.getXuatXu();
                if (count.containsKey(key)) {
                    count.put(sp.getXuatXu(), count.get(key) + 1);
                } else {
                    count.put(key, 1);
                }
            }

            for (String s : count.keySet()) {
                if (count.get(s) <= 0) {
                    continue;
                }
                JSONObject jObject = new JSONObject();
                jObject.put("country", s);
                jObject.put("count", count.get(s));

                array.put(jObject);
            }
            return array.toString();
        }
    }
}