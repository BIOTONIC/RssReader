package ca.wlu.tianran.rssreader.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ca.wlu.tianran.rssreader.R;
import ca.wlu.tianran.rssreader.data.NewsRepository;

import java.io.Serializable;

public class NewsActivity extends AppCompatActivity {

    private NewsContract.Presenter presenter;
    private NewsListFragment listFragment;
    private NewsDetailFragment detailFragment;
    private NewsWebFragment webFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // presenter has the the same life cycle with activity
        presenter = new NewsPresenter(NewsRepository.getInstance());

        // orientation changed event will be handled by fragments, not the activity
        if (savedInstanceState != null) {
            return;
        }

        // create CardListFragment
        listFragment = (NewsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_news_list);
        if (listFragment == null) {
            listFragment = NewsListFragment.newInstance((Serializable) presenter);
        }

        if (findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listFragment).commit();
        }
    }
}
