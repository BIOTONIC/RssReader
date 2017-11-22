package ca.wlu.tianran.rssreader.data;

import me.toptas.rssconverter.RssItem;

import java.util.List;

public interface NewsLoadListener {
    void newsReady(List<RssItem> newsList);
}
