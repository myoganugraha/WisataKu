package id.itk.yaf.wisataku.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import id.itk.yaf.wisataku.API.BaseAPIService;
import id.itk.yaf.wisataku.API.UtilsAPI;
import id.itk.yaf.wisataku.Model.User;
import id.itk.yaf.wisataku.R;
import id.itk.yaf.wisataku.Utility.ImageUtility;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    private Context mContext;
    private BaseAPIService baseAPIService;
    private ProgressDialog progressDialog;

    SharedPreferences mPrefs;
    User user;
    Bitmap imageSelected;

    @BindView(R.id.displayPictureEditProfile)
    CircleImageView displayPictureEditProfile;

    @BindView(R.id.nameEditProfile)
    EditText nameEditProfile;

    @BindView(R.id.emailEditProfile)
    EditText emailEditProfile;


    @BindView(R.id.webEditProfile)
    EditText websiteEditProfile;

    @BindView(R.id.btnProcedEditProfile)
    Button btnProcedEdit;

    private static final int REQUEST_CHOOSE_IMAGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mContext = this;
        ButterKnife.bind(this);

        initComponents();
        getUserData();
        initForm();
        formVerification();
    }

    private void formVerification() {
        btnProcedEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEditProfile.getText().toString().length() < 3){
                    FancyToast.makeText(mContext, "Give use your fullname, hooman", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                }
                else if(emailEditProfile.getText().toString().length() < 7){
                    FancyToast.makeText(mContext, "Real email hooman", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();;
                }
                else if(!isValidEmail(emailEditProfile.getText().toString())){
                    FancyToast.makeText(mContext, "Nah, you get caught using fake email", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();;
                }
                else  if(!Patterns.WEB_URL.matcher(websiteEditProfile.getText().toString()).matches()){
                    FancyToast.makeText(mContext, "Just give your link, hooman", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                }
                else {
                    procedEdit();
                }
            }
        });
    }

    private void initForm() {
        nameEditProfile.setText(user.getName());
        emailEditProfile.setText(user.getEmail());
        websiteEditProfile.setText(user.getWebsite());

        Glide.with(this)
                .load(user.getProfile_picture())
                .into(displayPictureEditProfile);

        displayPictureEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openChooserWithGallery(EditProfile.this, "Choose Picture", REQUEST_CHOOSE_IMAGE);
            }
        });
    }


    private void initComponents() {
        mPrefs = getSharedPreferences(Login.MY_PREFS, Context.MODE_PRIVATE);
        baseAPIService = UtilsAPI.getAPIService();
        getSupportActionBar().setTitle("Edit Profile");
    }

    private void getUserData() {
        Gson gson = new Gson();
        String json_response = mPrefs.getString("UserResponse", "");
        json_response = json_response.substring(22, json_response.length() - 1);
        Log.d("json_response", json_response);
        user = gson.fromJson(json_response, User.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                CropImage.activity(Uri.fromFile(imageFile))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setFixAspectRatio(true)
                        .start(EditProfile.this);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(mContext);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageSelected = null;
                try {
                    imageSelected = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e){
                    e.printStackTrace();
                }

                Glide.with(this)
                        .load(new File(resultUri.getPath()))
                        .apply(new RequestOptions().circleCrop())
                        .into(displayPictureEditProfile);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    public static boolean isValidEmail(String email) {
        boolean validate;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            validate = true;
        } else if (email.matches(emailPattern2)) {
            validate = true;
        } else {
            validate = false;
        }
        return validate;
    }

    private void procedEdit() {
        progressDialog = ProgressDialog.show(mContext, null, "Please Wait ...", true, false);

        baseAPIService.editProfile(
                user.getId_user(),
                nameEditProfile.getText().toString(),
                emailEditProfile.getText().toString(),
                websiteEditProfile.getText().toString(),
                ImageUtility.convert(imageSelected)
        )
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonResult = new JSONObject(response.body().string());

                                if (jsonResult.getString("error").equals("false")){
                                    Toast.makeText(mContext, "Profile Saved", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditProfile.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    String error_message = jsonResult.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Log.i("debug", "onResponse: FAILED");
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.e("debug", "onFailure: ERROR >" + t.getMessage());
                        Toast.makeText(mContext, "Bad Network", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
