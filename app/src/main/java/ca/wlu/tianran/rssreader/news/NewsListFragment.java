package ca.wlu.tianran.rssreader.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import ca.wlu.tianran.rssreader.R;
import ca.wlu.tianran.rssreader.util.SimpleDividerItemDecoration;
import ca.wlu.tianran.rssreader.util.SwipeItemLayout;

import java.io.Serializable;

public class NewsListFragment extends Fragment implements NewsContract.ListView, NewsContract.ItemClickListener {

    private final static String PRESENTER = "presenter";
    private NewsContract.Presenter presenter;
    private ActionBar actionBar;
    private NewsRecyclerAdapter recyclerAdapter;

    public NewsListFragment() {
    }

    public static NewsListFragment newInstance(Serializable presenter) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putSerializable(PRESENTER, presenter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        // get presenter
        if (savedInstanceState != null) {
            presenter = (NewsContract.Presenter) savedInstanceState.getSerializable(PRESENTER);
            presenter.setListView(this);
        } else {
            presenter = (NewsContract.Presenter) getArguments().getSerializable(PRESENTER);
            presenter.setListView(this);
        }

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(R.string.app_name);

        // RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.news_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(recyclerLayoutManager);

        // RecyclerView Adapter & Divider
        recyclerAdapter = new NewsRecyclerAdapter(getContext(),presenter, this);
        recyclerView.setAdapter(recyclerAdapter);
        SimpleDividerItemDecoration decoration = new SimpleDividerItemDecoration(getContext());
        recyclerView.addItemDecoration(decoration);

        // set item touch listener for swiping
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));

        return view;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(PRESENTER, (Serializable) presenter);
        super.onSaveInstanceState(outState);
    }

    // when item of recycler view is clicked
    @Override
    public void onItemClick(View view, int position) {
        NewsDetailFragment newsDetailFragment = NewsDetailFragment.newInstance((Serializable) presenter, position);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, newsDetailFragment).addToBackStack(null).commit();
    }

    @Override
    public void showLoadingError(){
        Toast.makeText(getContext(), "Load News Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public NewsRecyclerAdapter getRecyclerAdapter(){
        return recyclerAdapter;
    }

    @Override
    public void openLink(String link){
        NewsWebFragment newsWebFragment = NewsWebFragment.newInstance((Serializable)presenter, link);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newsWebFragment).addToBackStack(null).commit();
    }
}
