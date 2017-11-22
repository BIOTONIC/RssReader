package ca.wlu.tianran.rssreader.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import ca.wlu.tianran.rssreader.R;

import java.io.Serializable;

public class NewsWebFragment extends Fragment implements NewsContract.WebView {
    private final static String PRESENTER = "presenter";
    private final static String LINK = "link";
    private NewsContract.Presenter presenter;
    private String link;
    private WebView webView;
    private ActionBar actionBar;

    public NewsWebFragment() {
    }

    public static NewsWebFragment newInstance(Serializable presenter, String link) {
        NewsWebFragment fragment = new NewsWebFragment();
        Bundle args = new Bundle();
        args.putString(LINK, link);
        args.putSerializable(PRESENTER, presenter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_web, container, false);

        // get presenter
        if (savedInstanceState != null) {
            link = savedInstanceState.getString(LINK);
            presenter = (NewsContract.Presenter) savedInstanceState.getSerializable(PRESENTER);
            presenter.setWebView(this);
        } else {
            link = getArguments().getString(LINK);
            presenter = (NewsContract.Presenter) getArguments().getSerializable(PRESENTER);
            presenter.setWebView(this);
        }

        // change action bar
        setHasOptionsMenu(true);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        webView = view.findViewById(R.id.webview);
        webView.loadUrl(link);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(LINK, link);
        outState.putSerializable(PRESENTER, (Serializable) presenter);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
