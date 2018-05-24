package id.itk.yaf.wisataku.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import id.itk.yaf.wisataku.Activity.Login;
import id.itk.yaf.wisataku.Model.User;
import id.itk.yaf.wisataku.R;
import id.itk.yaf.wisataku.Utility.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Context mContext;
    private SessionManager session;
    private Unbinder unbinder;

    SharedPreferences mPrefs;

    @BindView(R.id.fullnameProfile)
    TextView fullnameProfile;

    @BindView(R.id.profile_website)
    TextView profileWebsite;

    @BindView(R.id.profile_email)
    TextView profileEmail;

    @BindView(R.id.displayPictureProfile)
    CircleImageView profilePicture;


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

        mPrefs = this.getActivity().getSharedPreferences(Login.MY_PREFS, Context.MODE_PRIVATE);

        Log.d("hallo", "sudah masuk profile fragment");



        Gson gson = new Gson();
        String json_response = mPrefs.getString("UserResponse", "");
        json_response = json_response.substring(22, json_response.length() - 1);
        User user = gson.fromJson(json_response, User.class);

        fullnameProfile.setText(user.getName());
        profileWebsite.setText(user.getWebsite());
        profileEmail.setText(user.getEmail());

        Glide.with(mContext)
                .load(user.getProfile_picture())
                .into(profilePicture);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(user.getUsername());
        return view;
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

}
