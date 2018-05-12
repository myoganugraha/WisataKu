package id.itk.yaf.wisataku.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import id.itk.yaf.wisataku.JSONResponse.JSONResponseWisata;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.itk.yaf.wisataku.API.BaseAPIService;
import id.itk.yaf.wisataku.API.RetrofitClient;
import id.itk.yaf.wisataku.Adapter.RecyclerviewListWisataAdapter;
import id.itk.yaf.wisataku.Model.Wisata;
import id.itk.yaf.wisataku.R;

public class GuestPage extends AppCompatActivity {

    @BindView(R.id.recycler_view_guest_alam)
    RecyclerView recyclerViewAlam;

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
    }

    private void loadRecyclerviewAlam() {
        recyclerViewAlam.setNestedScrollingEnabled(false);
        recyclerViewAlam.setHasFixedSize(true);
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
                wisataList = new ArrayList<>(Arrays.asList(jsonResponseWisata.getData()));
                recyclerviewListWisataAdapter = new RecyclerviewListWisataAdapter(mContext, wisataList);
                recyclerViewAlam.setAdapter(recyclerviewListWisataAdapter);
                recyclerviewListWisataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

}
