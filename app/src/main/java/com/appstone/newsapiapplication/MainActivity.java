package com.appstone.newsapiapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NewsCategoryAdapter.newsCategoryClickListner
//        , AdapterView.OnItemSelectedListener
{

//    http://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=e8da45f5458e4543b17ed712bed1581e
//    http://newsapi.org/v2/everything?q=bitcoin&from=2020-06-17&sortBy=publishedAt&apiKey=e8da45f5458e4543b17ed712bed1581e


    private RecyclerView mRcView;
    private ProgressDialog progressDialog;
    private EditText metSearch;
    private Adapter newsAdapter;
    private Toolbar mToolBar;
    private Spinner spinner;
//    private ActionMode navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRcView = findViewById(R.id.rc_news);
        mRcView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("getting News");
        metSearch = findViewById(R.id.et_search);
        ImageView mivClearSearch = findViewById(R.id.iv_clear);
        Toolbar mtoolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mtoolbar);


        RecyclerView mRcNewsCategory = findViewById(R.id.mrc_news_category);
        mRcNewsCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));

        NewsCategoryAdapter newsCategoryAdapter = new NewsCategoryAdapter(MainActivity.this);
        newsCategoryAdapter.setListner(this);
        mRcNewsCategory.setAdapter(newsCategoryAdapter);

        getNews();


        mToolBar = findViewById(R.id.tool_bar);
        spinner = findViewById(R.id.spinner_toolBar);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.select_country));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,spinner.getSelectedItem().toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Spinner spinner = (Spinner) navigationView.getMenu().findItem(R.id.navigation_drawer_item3).getActionView();
//        ArrayAdapter<CharSequence> charSequenceArrayAdapter = ArrayAdapter.createFromResource(this,R.array.select_country,
//                android.R.layout.simple_spinner_item);
//        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(charSequenceArrayAdapter);
//        spinner.setOnItemSelectedListener(this);


        metSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    mivClearSearch.setVisibility(View.VISIBLE);
                    newsAdapter.getFilter().filter(s);
                } else {
                    mivClearSearch.setVisibility(View.GONE);
                }
            }
        });
        mivClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                metSearch.setText("");
                newsAdapter.clearsearch();
                mivClearSearch.setVisibility(View.GONE);


            }

        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main);
//        return true;
//    }

    private void getNews() {
        progressDialog.show();
        APIinterface apIinterface = APIclient.getClient().create(APIinterface.class);

        Map<String, Object> prams = new HashMap<>();
        prams.put("sources", "techcrunch");
        prams.put("apiKey", "e8da45f5458e4543b17ed712bed1581e");
        Call<Result> getnews = apIinterface.getNews(prams);
        getnews.enqueue(new Callback<Result>() {


            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result responseValue = response.body();
                ArrayList<Articles> articlesList = responseValue.articlesArrayList;

                progressDialog.hide();

                newsAdapter = new Adapter(MainActivity.this, articlesList);
                mRcView.setAdapter(newsAdapter);


            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.hide();

            }
        });


    }

    protected void getNewsByCategory(String category) {

        progressDialog.show();
        APIinterface apIinterface = APIclient.getClient().create(APIinterface.class);
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        params.put("apiKey", "e8da45f5458e4543b17ed712bed1581e");
        Call<Result> geCATNews = apIinterface.getNews(params);
        geCATNews.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result responseValue = response.body();
                ArrayList<Articles> articlesList = responseValue.articlesArrayList;

                progressDialog.hide();

                newsAdapter = new Adapter(MainActivity.this, articlesList);
                mRcView.setAdapter(newsAdapter);

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.hide();

            }
        });


    }

    @Override
    public void newsCategoryClicked(String category) {
        getNewsByCategory(category);

    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String text = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(),text,Toast.LENGTH_LONG).show();
//
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}





