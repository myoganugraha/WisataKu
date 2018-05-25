package id.itk.yaf.wisataku.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonParser;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import id.itk.yaf.wisataku.API.BaseAPIService;
import id.itk.yaf.wisataku.API.UtilsAPI;
import id.itk.yaf.wisataku.Activity.EditProfile;
import id.itk.yaf.wisataku.Activity.Login;
import id.itk.yaf.wisataku.Adapter.RecyclerviewFragmentProfileAdapter;
import id.itk.yaf.wisataku.JSONResponse.JSONResponseWisata;
import id.itk.yaf.wisataku.Model.User;
import id.itk.yaf.wisataku.Model.Wisata;
import id.itk.yaf.wisataku.R;
import id.itk.yaf.wisataku.Utility.SessionManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Context mContext;
    private SessionManager session;
    private Unbinder unbinder;
    private BaseAPIService baseAPIService;

    private RecyclerviewFragmentProfileAdapter recyclerviewFragmentProfileAdapter;
    private List<Wisata> wisataList = new ArrayList<Wisata>();

    SharedPreferences mPrefs;
    User user;

    @BindView(R.id.fullnameProfile)
    TextView fullnameProfile;

    @BindView(R.id.profile_website)
    TextView profileWebsite;

    @BindView(R.id.profile_email)
    TextView profileEmail;

    @BindView(R.id.displayPictureProfile)
    CircleImageView profilePicture;

    @BindView(R.id.recycler_view_fragment_profile)
    RecyclerView recyclerViewProfile;

    @BindView(R.id.swipeToRefreshProfile)
    SwipeRefreshLayout swipeRefreshLayoutProfile;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public void onAttach(Context mContext) {
        super.onAttach(mContext);
        this.mContext = mContext;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        session = new SessionManager(getActivity());
        unbinder = ButterKnife.bind(this, view);

        baseAPIService = UtilsAPI.getAPIService();

        mPrefs = this.getActivity().getSharedPreferences(Login.MY_PREFS, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json_response = mPrefs.getString("UserResponse", "");
        json_response = json_response.substring(22, json_response.length() - 1);
        user = gson.fromJson(json_response, User.class);

        fullnameProfile.setText(user.getName());
        profileWebsite.setText(user.getWebsite());
        profileEmail.setText(user.getEmail());

        Glide.with(mContext)
                .load(user.getProfile_picture())
                .into(profilePicture);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(user.getUsername());


        //RECYCLERVIEW PROFILE
        recyclerViewProfile.setHasFixedSize(true);
        recyclerViewProfile.setNestedScrollingEnabled(false);
        LinearLayoutManager llmProfile = new LinearLayoutManager(getActivity());
        llmProfile.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewProfile.setLayoutManager(llmProfile);
        loadJSONWisataProfile();

        swipeRefreshLayoutProfile.setOnRefreshListener(this);

        return view;
    }

    private void loadJSONWisataProfile() {
        baseAPIService.getPostById(user.getId_user())
                .enqueue(new Callback<JSONResponseWisata>() {

                    @Override
                    public void onResponse(Call<JSONResponseWisata> call, Response<JSONResponseWisata> response) {
                        JSONResponseWisata jsonResponseWisataProfile = response.body();
                        Log.d("cek data wisata : ", Arrays.toString(jsonResponseWisataProfile.getData()));

                        wisataList = new ArrayList<>(Arrays.asList(jsonResponseWisataProfile.getData()));
                        recyclerviewFragmentProfileAdapter = new RecyclerviewFragmentProfileAdapter(wisataList, getActivity());
                        recyclerViewProfile.setAdapter(recyclerviewFragmentProfileAdapter);
                        recyclerviewFragmentProfileAdapter.notifyDataSetChanged();

                        swipeRefreshLayoutProfile.setRefreshing(false);

                    }

                    @Override
                    public void onFailure(@NonNull Call<JSONResponseWisata> call, @NonNull Throwable t) {
                        Log.e("tag", t.getMessage());

                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout_profile_fragment:
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog
                        .setTitle("Logout")
                        .setMessage("Are you sure to logout ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                session.logoutUser();
                                Toast.makeText(getActivity(), "You've Been Logout", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), Login.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Noooo, take me back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        loadJSONWisataProfile();
    }

    @OnClick(R.id.edit_profile)
    public void editProfie(){
        Intent intent = new Intent(getActivity(), EditProfile.class);
        startActivity(intent);
    }
}
