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
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

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
public class WisataFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {



    @BindView(R.id.recycler_view_fragment_wisata_alam)
    RecyclerView recyclerviewAlamFragmentWisata;

    @BindView(R.id.recycler_view_fragment_wisata_belanja)
    RecyclerView recyclerviewBelanjaFragmentWisata;

    @BindView(R.id.recycler_view_fragment_wisata_kuliner)
    RecyclerView recyclerviewKulinerFragmentWisata;

    @BindView(R.id.shimmer_layout_alam)
    ShimmerFrameLayout shimmerFrameLayoutAlam;

    @BindView(R.id.shimmer_layout_belanja)
    ShimmerFrameLayout shimmerFrameLayoutBelanja;

    @BindView(R.id.shimmer_layout_kuliner)
    ShimmerFrameLayout shimmerFrameLayoutKuliner;

    @BindView(R.id.fragmentWisataNatureHeader)
    TextView natureHeader;

    @BindView(R.id.fragmentWisataShoppingHeader)
    TextView shoppingHeader;

    @BindView(R.id.fragmentWisataCulinaryHeader)
    TextView culinaryHeader;

    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

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
    public void onPause() {
        super.onPause();
        shimmerFrameLayoutAlam.stopShimmerAnimation();
        shimmerFrameLayoutBelanja.stopShimmerAnimation();
        shimmerFrameLayoutKuliner.stopShimmerAnimation();
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


        shimmerFrameLayoutAlam.setVisibility(View.VISIBLE);
        shimmerFrameLayoutAlam.startShimmerAnimation();
        loadJSONAlam();
        ///////////////////////////////////////////////////////////////////////////////////////

        //RECYCLERVIEW BELANJA
        recyclerviewBelanjaFragmentWisata.setHasFixedSize(true);
        recyclerviewBelanjaFragmentWisata.setNestedScrollingEnabled(false);
        LinearLayoutManager llmBelanja = new LinearLayoutManager(getActivity());
        llmBelanja.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewBelanjaFragmentWisata.setLayoutManager(llmBelanja);


        shimmerFrameLayoutBelanja.setVisibility(View.VISIBLE);
        shimmerFrameLayoutBelanja.startShimmerAnimation();
        loadJSONBelanja();
        ///////////////////////////////////////////////////////////////////////////////////////

        //RECYCLERBIEW KULINER
        recyclerviewKulinerFragmentWisata.setHasFixedSize(true);
        recyclerviewKulinerFragmentWisata.setNestedScrollingEnabled(false);
        LinearLayoutManager llmKuliner = new LinearLayoutManager(getActivity());
        llmKuliner.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewKulinerFragmentWisata.setLayoutManager(llmKuliner);


        shimmerFrameLayoutKuliner.setVisibility(View.VISIBLE);
        shimmerFrameLayoutKuliner.startShimmerAnimation();
        loadJSONKuliner();


        swipeRefreshLayout.setOnRefreshListener(this);

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

                shimmerFrameLayoutAlam.stopShimmerAnimation();
                shimmerFrameLayoutAlam.setVisibility(View.GONE);
                natureHeader.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                Log.e("tag", t.getMessage());

                shimmerFrameLayoutAlam.stopShimmerAnimation();
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

                shimmerFrameLayoutBelanja.stopShimmerAnimation();
                shimmerFrameLayoutBelanja.setVisibility(View.GONE);
                shoppingHeader.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                Log.e("tag", t.getMessage());

                shimmerFrameLayoutBelanja.stopShimmerAnimation();
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

                shimmerFrameLayoutKuliner.stopShimmerAnimation();
                shimmerFrameLayoutKuliner.setVisibility(View.GONE);
                culinaryHeader.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                Log.e("tag", t.getMessage());

                shimmerFrameLayoutKuliner.stopShimmerAnimation();
            }
        });
    }

    @Override
    public void onRefresh() {
        loadJSONAlam();
        loadJSONBelanja();
        loadJSONKuliner();
    }
}
