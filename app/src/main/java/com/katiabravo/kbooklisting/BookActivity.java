package com.katiabravo.kbooklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<Book>> {
    private static String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private String searchTerm;
    private static final int LOADER_ID = 1;
    private ListView bookListView;
    private EditText searchBar;
    private Button searchButton;
    private View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        searchBar = (EditText) findViewById(R.id.search_bar);
        searchButton = (Button) findViewById(R.id.search_button);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView = (ListView) findViewById(R.id.list);
        bookListView.setEmptyView(mEmptyStateTextView);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            searchTerm = searchBar.getText().toString().replaceAll(" ", "+");
            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            loadingIndicator.setVisibility(View.VISIBLE);

            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                mEmptyStateTextView.setVisibility(View.GONE);
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(LOADER_ID, null, BookActivity.this);
            } else {
                loadingIndicator.setVisibility(View.GONE);
                bookListView.setEmptyView(mEmptyStateTextView);
                mEmptyStateTextView.setText(R.string.no_internet);
            }

            if(getLoaderManager().getLoader(LOADER_ID) != null){
                getLoaderManager().restartLoader(LOADER_ID, null, BookActivity.this);
            }else{
                getLoaderManager().initLoader(LOADER_ID, null, BookActivity.this);
            }
            }
        });
    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState = ;
    }*/

    @Override
    public android.content.Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, BOOK_REQUEST_URL + searchTerm);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_books);
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}

