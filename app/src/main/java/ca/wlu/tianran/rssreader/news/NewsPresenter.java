package ca.wlu.tianran.rssreader.news;

import android.view.View;
import ca.wlu.tianran.rssreader.data.NewsLoadListener;
import ca.wlu.tianran.rssreader.data.NewsRepository;
import me.toptas.rssconverter.RssItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsPresenter implements NewsContract.Presenter, Serializable {
    private transient NewsContract.ListView listView;
    private transient NewsContract.DetailView detailView;
    private transient NewsContract.WebView webView;
    private transient NewsRepository repository;

    public NewsPresenter(final NewsRepository repository) {
        this.repository = repository;
        this.repository.loadNews(new NewsLoadListener() {
            @Override
            public void newsReady(List<RssItem> newsList) {
                if (newsList == null || newsList.size() == 0) {
                    listView.showLoadingError();
                    return;
                }
                repository.setNewsList((ArrayList<RssItem>) newsList);
                listView.getRecyclerAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setListView(NewsContract.ListView listView) {
        this.listView = listView;
    }

    @Override
    public void setDetailView(NewsContract.DetailView detailView) {
        this.detailView = detailView;
    }

    @Override
    public void setWebView(NewsContract.WebView webView) {
        this.webView = webView;
    }

    @Override
    public void deleteNews(View view, int pos) {
    }

    @Override
    public void loadNews(int pos) {
        detailView.showImg(repository.getImage(pos));
        detailView.showDesc(repository.getDesc(pos));
    }

    @Override
    public void loadMore(int pos){
        String link = repository.getLink(pos);
        listView.openLink(link);
    }

    @Override
    public void onBindListViewHolder(NewsRecyclerAdapter.ViewHolder holder, int position) {
        holder.setTitle(repository.getTitle(position));
        holder.setImage(repository.getImage(position));
    }

    @Override
    public int getListItemCount() {
        return repository.getSize();
    }
}
