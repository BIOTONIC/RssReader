package ca.wlu.tianran.rssreader.news;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ca.wlu.tianran.rssreader.R;
import com.squareup.picasso.Picasso;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final NewsContract.Presenter presenter;
    private final NewsContract.ItemClickListener listener;

    public NewsRecyclerAdapter(Context context, NewsContract.Presenter presenter, NewsContract.ItemClickListener listener) {
        this.context = context;
        this.presenter = presenter;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_news_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        presenter.onBindListViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getListItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CardView cardView;
        private final TextView titleView;
        private final ImageView imageView;
        private final Button swipeBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.item_news);
            titleView = itemView.findViewById(R.id.item_title);
            imageView = itemView.findViewById(R.id.item_image);
            swipeBtn = itemView.findViewById(R.id.swipe_btn);

            cardView.setOnClickListener(this);
            swipeBtn.setOnClickListener(this);
        }

        public void setTitle(String ques) {
            titleView.setText(ques);
        }

        public void setImage(String image) {
            if (image == null) {
                imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.error_img));
                return;
            }
            Picasso.with(context).load(image).error(R.drawable.error_img).into(imageView);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_news:
                    listener.onItemClick(v, getLayoutPosition());
                    break;

                case R.id.swipe_btn:
                    int pos = getAdapterPosition();
                    presenter.deleteNews(v, pos);
                    notifyItemRemoved(pos);
                    break;
            }
        }
    }
}
