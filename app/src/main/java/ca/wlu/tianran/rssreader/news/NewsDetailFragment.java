package ca.wlu.tianran.rssreader.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ca.wlu.tianran.rssreader.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class NewsDetailFragment extends Fragment implements NewsContract.DetailView, View.OnClickListener {
    private ActionBar actionBar;
    private ImageView imageView;
    private TextView descText;
    private Button moreBtn;
    private int position;

    private NewsContract.Presenter presenter;

    private final static String PRESENTER = "presenter";
    public static String ITEM_POSITION = "item_position";

    public NewsDetailFragment() {
    }

    public static NewsDetailFragment newInstance(Serializable presenter, int position) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRESENTER, presenter);
        args.putInt(ITEM_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        // find view
        imageView = view.findViewById(R.id.image_view);
        descText = view.findViewById(R.id.desc_text);
        moreBtn = view.findViewById(R.id.more_btn);

        // set listener
        moreBtn.setOnClickListener(this);

        // change action bar
        setHasOptionsMenu(true);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // return to last saved state
        if (savedInstanceState != null) {
            // also can get instance state in onRestoreInstanceState()
            position = savedInstanceState.getInt(ITEM_POSITION);
            presenter = (NewsContract.Presenter) savedInstanceState.getSerializable(PRESENTER);
            presenter.setDetailView(this);
            presenter.loadNews(position);
        } else {
            position = getArguments().getInt(ITEM_POSITION);
            presenter = (NewsContract.Presenter) getArguments().getSerializable(PRESENTER);
            presenter.setDetailView(this);
            presenter.loadNews(position);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(PRESENTER, (Serializable) presenter);
        outState.putInt(ITEM_POSITION, position);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // when more button is clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_btn:
                presenter.loadMore(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void showImg(String image) {
        if (image == null) {
            imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.error_img));
            return;
        }
        Picasso.with(getContext()).load(image).error(R.drawable.error_img).into(imageView);
    }

    @Override
    public void showDesc(String desc) {
        descText.setText(desc);
    }
}
