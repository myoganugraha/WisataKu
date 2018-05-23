package id.itk.yaf.wisataku.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.itk.yaf.wisataku.API.BaseAPIService;
import id.itk.yaf.wisataku.API.UtilsAPI;
import id.itk.yaf.wisataku.Model.User;
import id.itk.yaf.wisataku.R;
import id.itk.yaf.wisataku.Utility.MD5Encode;
import id.itk.yaf.wisataku.Utility.SessionManager;
import okhttp3.ResponseBody;
import pl.tajchert.nammu.Nammu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.loopj.android.http.AsyncHttpClient.log;

public class Login extends AppCompatActivity {

    @BindView(R.id.usernameLogin)
    EditText usernameLogin;

    @BindView(R.id.passwordLogin)
    EditText passwordLogin;

    @BindView(R.id.btnProcedLogin)
    Button procedLogin;

    @BindView(R.id.linearLayoutLogin)
    LinearLayout linearLayoutLogin;


    private BaseAPIService baseAPIService;
    private ProgressDialog progressDialog;
    private Context mContext;
    SessionManager session;

    User user = new User();

    SharedPreferences mPrefs;
    String json_response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mPrefs = getPreferences(MODE_PRIVATE);
        ButterKnife.bind(this);
        Nammu.init(this);
        mContext = this;
        baseAPIService = UtilsAPI.getAPIService();
        initSession();
    }

    private void initSession() {
        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

    }

    @OnClick (R.id.btnToRegisterActivity)
    public void toRegisterActivity(){
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnLoginAsGuest)
    public void loginAsGuest(){
        Intent intent = new Intent(Login.this, GuestPage.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnProcedLogin)
    public void procedLogin(){
        progressDialog = ProgressDialog.show(mContext, null, "Please Wait ...", true, false);

        baseAPIService.loginRequest(
                usernameLogin.getText().toString(),
                MD5Encode.md5(passwordLogin.getText().toString())
        )
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonResult = new JSONObject(response.body().string());

                                if(jsonResult.getString("error").equals("false")){

                                    session.createLoginSession(usernameLogin.getText().toString());
                                    Toast.makeText(mContext, "Welcome Back, " + usernameLogin.getText().toString(), Toast.LENGTH_SHORT).show();

                                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                    Gson gson = new Gson();
                                    json_response = gson.toJson(user);
                                    prefsEditor.putString("UserResponse", json_response);
                                    prefsEditor.apply();

                                    //Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                                    //json_response = gson.toJson(user);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                else {
                                    String error_message = jsonResult.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        log.e("debug", "onFailure: ERROR > " + t.toString());
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
