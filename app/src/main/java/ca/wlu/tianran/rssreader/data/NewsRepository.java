package ca.wlu.tianran.rssreader.data;

import me.toptas.rssconverter.RssItem;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsRepository {
    private static NewsRepository INSTANCE = null;
    private NewsService service;
    private ArrayList<RssItem> newsList;

    private NewsRepository() {
        service = new NewsService();
    }

    public static synchronized NewsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NewsRepository();
        }
        return INSTANCE;
    }

    public void loadNews(NewsLoadListener listener) {
        service.getNews(listener);
    }

    public void setNewsList(ArrayList<RssItem> list) {
        newsList = list;
    }

    public String getTitle(int pos) {
        return newsList.get(pos).getTitle();
    }

    public String getImage(int pos) {
        String desc = newsList.get(pos).getDescription();
        Pattern p = Pattern.compile("img src='(.*)' alt");
        Matcher m = p.matcher(desc);
        while (m.find()) {
            return m.group(1);
        }
        return null;

    }

    public String getDesc(int pos) {
        String desc = newsList.get(pos).getDescription();
        String result = "";
        Pattern p = Pattern.compile("title='(.*)' hei");
        Matcher m = p.matcher(desc);
        while (m.find()) {
            result = m.group(1);
        }
        p = Pattern.compile("<p>(.*)</p>");
        m = p.matcher(desc);
        while (m.find()) {
            result += m.group(1);
        }
        return result;
    }

    public String getLink(int pos) {
        return newsList.get(pos).getLink();
    }

    public int getSize() {
        if (newsList == null) {
            return 0;
        }
        return newsList.size();
    }
}

