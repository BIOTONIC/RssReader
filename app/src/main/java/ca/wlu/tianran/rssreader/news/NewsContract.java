package ca.wlu.tianran.rssreader.news;

import android.view.View;

public interface NewsContract {
    interface Presenter {
        void setListView(ListView listView);

        void setDetailView(DetailView detailView);

        void setWebView(WebView webView);

        void deleteNews(View view, int pos);

        void loadNews(int pos);

        void loadMore(int pos);

        void onBindListViewHolder(NewsRecyclerAdapter.ViewHolder holder, int position);

        int getListItemCount();
    }

    interface ListView {
        void showLoadingError();

        NewsRecyclerAdapter getRecyclerAdapter();

        void openLink(String link);
    }

    interface DetailView {

        void showImg(String img);

        void showDesc(String title);
    }

    interface WebView {

    }

    interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
