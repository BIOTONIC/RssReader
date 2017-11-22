package ca.wlu.tianran.rssreader.data;

import me.toptas.rssconverter.RssConverterFactory;
import me.toptas.rssconverter.RssFeed;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class NewsService {
    private static String BASE_URL = "http://www.cbc.ca/";
    private NewsLoadListener callback;

    public interface RssService {
        @GET("cmlink/rss-topstories")
        Call<RssFeed> getRss();
    }

    public NewsService() {
    }

    public void getNews(NewsLoadListener listener) {
        callback = listener;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(RssConverterFactory.create())
                .build();

        RssService service = retrofit.create(RssService.class);
        service.getRss().enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                if (!response.isSuccessful()) {
                    callback.newsReady(null);
                    return;
                }
                callback.newsReady(response.body().getItems());
            }

            @Override
            public void onFailure(Call<RssFeed> call, Throwable t) {
                callback.newsReady(null);
            }
        });
    }
}
