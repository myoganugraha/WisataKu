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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.itk.yaf.wisataku.Activity.Login;
import id.itk.yaf.wisataku.Model.User;
import id.itk.yaf.wisataku.R;
import id.itk.yaf.wisataku.Utility.SessionManager;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Context mContext;
    private SessionManager session;
    private Unbinder unbinder;

    @BindView(R.id.fullnameProfile)
    TextView fullnameProfile;

    SharedPreferences  mPrefs;

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    String json_response;

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


        /*GSONSharedPreferences gsonSharedPreferences = new GSONSharedPreferences(mContext, "user");
        User user= null;

        try {
            user = (User) gsonSharedPreferences.getObject(new User());
            Log.i("test", user.getUsername());
        } catch (ParsingException e) {
            e.printStackTrace();
        }


        Gson gson = new Gson();
        json_response = mPrefs.getString("UserResponse", "");
        User obj = gson.fromJson(json_response, User.class);

        //User user = new Gson().fromJson(json_response, User.class);
        //Log.d("test", obj.getUsername());

        fullnameProfile.setText(obj.getName());*/

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("dummyUsernameHere");
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
