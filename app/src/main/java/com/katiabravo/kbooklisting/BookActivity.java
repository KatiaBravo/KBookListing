package com.katiabravo.kbooklisting;

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

    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private String searchTerm;
    private static final int LOADER_ID = 1;
    final ListView bookListView = (ListView) findViewById(R.id.list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        final EditText searchBar = (EditText) findViewById(R.id.search_bar);
        final Button searchButton = (Button) findViewById(R.id.search_button);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    android.app.LoaderManager loaderManager = getLoaderManager();
                    loaderManager.initLoader(LOADER_ID, null, com.katiabravo.kbooklisting.BookActivity.this);
                } else {
                    View loadingIndicator = findViewById(R.id.loading_spinner);
                    loadingIndicator.setVisibility(View.GONE);
                    bookListView.setEmptyView(mEmptyStateTextView);
                    mEmptyStateTextView.setText(R.string.no_internet);
                }

                searchTerm = searchBar.getText().toString().toLowerCase();
                mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            }
        });


    }

    @Override
    public android.content.Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, BOOK_REQUEST_URL + searchTerm);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        } else {
            bookListView.setEmptyView(mEmptyStateTextView);
            mEmptyStateTextView.setText(R.string.no_books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }


}
