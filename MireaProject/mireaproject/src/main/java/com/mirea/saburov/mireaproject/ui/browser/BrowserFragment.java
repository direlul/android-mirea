package com.mirea.saburov.mireaproject.ui.browser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.mirea.saburov.mireaproject.R;

public class BrowserFragment extends Fragment implements View.OnClickListener {

    private WebView webView;
    private EditText site;
    private Button forward;
    private Button back;
    private Button goToSite;

    public BrowserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_browser, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        webView = view.findViewById(R.id.web);
        site = view.findViewById(R.id.site);
        forward = view.findViewById(R.id.forward);
        back = view.findViewById(R.id.back);
        goToSite = view.findViewById(R.id.go);

        forward.setOnClickListener(this);
        back.setOnClickListener(this);
        goToSite.setOnClickListener(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());

        webView.loadUrl("http://www.google.com");

        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.go:
                String url = site.getText().toString();
                if (url.length() != 0) {
                    webView.loadUrl(url);
                }
                break;
            case R.id.forward:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                break;
            case R.id.back:
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                break;
        }
    }

}