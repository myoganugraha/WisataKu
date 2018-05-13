package id.itk.yaf.wisataku.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.itk.yaf.wisataku.API.BaseAPIService;
import id.itk.yaf.wisataku.API.RetrofitClient;
import id.itk.yaf.wisataku.Adapter.RecyclerviewListWisataAdapter;
import id.itk.yaf.wisataku.JSONResponse.JSONResponseWisata;
import id.itk.yaf.wisataku.Model.Wisata;
import id.itk.yaf.wisataku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestPage extends AppCompatActivity {

    @BindView(R.id.recycler_view_guest_alam)
    RecyclerView recyclerViewAlam;

    @BindView(R.id.recycler_view_guest_belanja)
    RecyclerView recyclerViewBelanja;

    @BindView(R.id.recycler_view_guest_kuliner)
    RecyclerView recyclerViewKuliner;

    @BindView(R.id.linearLayoutGuest)
    LinearLayout linearLayoutGuestWrapper;


    private Context mContext;
    private RecyclerviewListWisataAdapter recyclerviewListWisataAdapter;
    private List<Wisata> wisataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_page);

        mContext = this;
        ButterKnife.bind(this);

        loadRecyclerviewAlam();
        loadRecyclerviewBelanja();
        loadRecyclerviewKuliner();
    }


    //Recyclerview Alam
    private void loadRecyclerviewAlam() {
        recyclerViewAlam.setHasFixedSize(true);
        recyclerViewAlam.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAlam.setLayoutManager(layoutManager);

        loadDataAlam();
    }

    private void loadDataAlam() {
        BaseAPIService baseAPIService = RetrofitClient.getClient("http://sicentang.xyz/ppb/").create(BaseAPIService.class);
        Call<JSONResponseWisata> call = baseAPIService.getJSONAlam();

        call.enqueue(new Callback<JSONResponseWisata>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponseWisata> call, @NonNull Response<JSONResponseWisata> response) {

                JSONResponseWisata jsonResponseWisata = response.body();

                Log.d("tag","hasil -> "+ Arrays.toString(jsonResponseWisata.getData()));
                wisataList = new ArrayList<>(Arrays.asList(jsonResponseWisata.getData()));
                recyclerviewListWisataAdapter = new RecyclerviewListWisataAdapter(mContext, wisataList);
                recyclerViewAlam.setAdapter(recyclerviewListWisataAdapter);
                recyclerviewListWisataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    //Recyclerview Belanja
    private void loadRecyclerviewBelanja(){
        recyclerViewBelanja.setHasFixedSize(true);
        recyclerViewBelanja.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBelanja.setLayoutManager(layoutManager);

        loadDataBelanja();

    }

    private void loadDataBelanja() {
        BaseAPIService baseAPIService = RetrofitClient.getClient("http://sicentang.xyz/ppb/").create(BaseAPIService.class);
        Call<JSONResponseWisata> call = baseAPIService.getJSONBelanja();

        call.enqueue(new Callback<JSONResponseWisata>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponseWisata> call, @NonNull Response<JSONResponseWisata> response) {

                JSONResponseWisata jsonResponseWisata = response.body();

                Log.d("tag","hasil -> "+ Arrays.toString(jsonResponseWisata.getData()));
                wisataList = new ArrayList<>(Arrays.asList(jsonResponseWisata.getData()));
                recyclerviewListWisataAdapter = new RecyclerviewListWisataAdapter(mContext, wisataList);
                recyclerViewBelanja.setAdapter(recyclerviewListWisataAdapter);
                recyclerviewListWisataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }


    //Recyclerview Kuliner
    private void loadRecyclerviewKuliner(){
        recyclerViewKuliner.setHasFixedSize(true);
        recyclerViewKuliner.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewKuliner.setLayoutManager(layoutManager);

        loadDataKuliner();

    }

    private void loadDataKuliner() {
        BaseAPIService baseAPIService = RetrofitClient.getClient("http://sicentang.xyz/ppb/").create(BaseAPIService.class);
        Call<JSONResponseWisata> call = baseAPIService.getJSONKuliner();

        call.enqueue(new Callback<JSONResponseWisata>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponseWisata> call, @NonNull Response<JSONResponseWisata> response) {

                JSONResponseWisata jsonResponseWisata = response.body();

                Log.d("tag","hasil -> "+ Arrays.toString(jsonResponseWisata.getData()));
                wisataList = new ArrayList<>(Arrays.asList(jsonResponseWisata.getData()));
                recyclerviewListWisataAdapter = new RecyclerviewListWisataAdapter(mContext, wisataList);
                recyclerViewKuliner.setAdapter(recyclerviewListWisataAdapter);
                recyclerviewListWisataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guest_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.goto_login:
                Intent intent = new Intent(GuestPage.this, Login.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
