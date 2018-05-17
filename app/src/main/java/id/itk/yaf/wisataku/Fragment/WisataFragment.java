package id.itk.yaf.wisataku.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.itk.yaf.wisataku.API.BaseAPIService;
import id.itk.yaf.wisataku.API.RetrofitClient;
import id.itk.yaf.wisataku.Adapter.RecyclerviewListWisataAdapter;
import id.itk.yaf.wisataku.JSONResponse.JSONResponseWisata;
import id.itk.yaf.wisataku.Model.Wisata;
import id.itk.yaf.wisataku.R;
import id.itk.yaf.wisataku.Utility.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WisataFragment extends Fragment {



    @BindView(R.id.recycler_view_fragment_wisata_alam)
    RecyclerView recyclerviewAlamFragmentWisata;

    @BindView(R.id.recycler_view_fragment_wisata_belanja)
    RecyclerView recyclerviewBelanjaFragmentWisata;

    @BindView(R.id.recycler_view_fragment_wisata_kuliner)
    RecyclerView recyclerviewKulinerFragmentWisata;


    private RecyclerviewListWisataAdapter recyclerviewListWisataAdapter;
    private List<Wisata> wisataList = new ArrayList<Wisata>();;

    private Unbinder unbinder;
    private Context mContext;

    private SessionManager session;

    public static WisataFragment newInstance(){
        return new WisataFragment();
    }

    @Override
    public void onAttach(Context mContext) {
        super.onAttach(mContext);
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wisata, container, false);
        unbinder = ButterKnife.bind(this, view);

        session = new SessionManager(getActivity());

        //RECYCLERVIEW ALAM
        recyclerviewAlamFragmentWisata.setHasFixedSize(true);
        recyclerviewAlamFragmentWisata.setNestedScrollingEnabled(false);
        LinearLayoutManager llmAlam = new LinearLayoutManager(getActivity());
        llmAlam.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewAlamFragmentWisata.setLayoutManager(llmAlam);
        loadJSONAlam();
        ///////////////////////////////////////////////////////////////////////////////////////

        //RECYCLERVIEW BELANJA
        recyclerviewBelanjaFragmentWisata.setHasFixedSize(true);
        recyclerviewBelanjaFragmentWisata.setNestedScrollingEnabled(false);
        LinearLayoutManager llmBelanja = new LinearLayoutManager(getActivity());
        llmBelanja.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewBelanjaFragmentWisata.setLayoutManager(llmBelanja);
        loadJSONBelanja();
        ///////////////////////////////////////////////////////////////////////////////////////

        //RECYCLERBIEW KULINER
        recyclerviewKulinerFragmentWisata.setHasFixedSize(true);
        recyclerviewKulinerFragmentWisata.setNestedScrollingEnabled(false);
        LinearLayoutManager llmKuliner = new LinearLayoutManager(getActivity());
        llmKuliner.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewKulinerFragmentWisata.setLayoutManager(llmKuliner);
        loadJSONKuliner();


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

    private void loadJSONAlam() {
        BaseAPIService baseAPIService = RetrofitClient.getClient("http://sicentang.xyz/ppb/").create(BaseAPIService.class);
        Call<JSONResponseWisata> call = baseAPIService.getJSONAlam();

        call.enqueue(new Callback<JSONResponseWisata>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponseWisata> call, @NonNull Response<JSONResponseWisata> response) {

                JSONResponseWisata jsonResponseWisata = response.body();

                Log.d("tag","hasil -> "+ Arrays.toString(jsonResponseWisata.getData()));
                wisataList = new ArrayList<>(Arrays.asList(jsonResponseWisata.getData()));
                recyclerviewListWisataAdapter = new RecyclerviewListWisataAdapter(getActivity(), wisataList);
                recyclerviewAlamFragmentWisata.setAdapter(recyclerviewListWisataAdapter);
                recyclerviewListWisataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    private void loadJSONBelanja() {
        BaseAPIService baseAPIService = RetrofitClient.getClient("http://sicentang.xyz/ppb/").create(BaseAPIService.class);
        Call<JSONResponseWisata> call = baseAPIService.getJSONBelanja();

        call.enqueue(new Callback<JSONResponseWisata>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponseWisata> call, @NonNull Response<JSONResponseWisata> response) {

                JSONResponseWisata jsonResponseWisata = response.body();

                Log.d("tag","hasil -> "+ Arrays.toString(jsonResponseWisata.getData()));
                wisataList = new ArrayList<>(Arrays.asList(jsonResponseWisata.getData()));
                recyclerviewListWisataAdapter = new RecyclerviewListWisataAdapter(getActivity(), wisataList);
                recyclerviewBelanjaFragmentWisata.setAdapter(recyclerviewListWisataAdapter);
                recyclerviewListWisataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

    private void loadJSONKuliner() {
        BaseAPIService baseAPIService = RetrofitClient.getClient("http://sicentang.xyz/ppb/").create(BaseAPIService.class);
        Call<JSONResponseWisata> call = baseAPIService.getJSONKuliner();

        call.enqueue(new Callback<JSONResponseWisata>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponseWisata> call, @NonNull Response<JSONResponseWisata> response) {

                JSONResponseWisata jsonResponseWisata = response.body();

                Log.d("tag","hasil -> "+ Arrays.toString(jsonResponseWisata.getData()));
                wisataList = new ArrayList<>(Arrays.asList(jsonResponseWisata.getData()));
                recyclerviewListWisataAdapter = new RecyclerviewListWisataAdapter(getActivity(), wisataList);
                recyclerviewKulinerFragmentWisata.setAdapter(recyclerviewListWisataAdapter);
                recyclerviewListWisataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                Log.e("tag", t.getMessage());
            }
        });
    }

}
