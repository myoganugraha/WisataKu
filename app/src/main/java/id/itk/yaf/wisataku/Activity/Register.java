package id.itk.yaf.wisataku.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.itk.yaf.wisataku.API.BaseAPIService;
import id.itk.yaf.wisataku.API.UtilsAPI;
import id.itk.yaf.wisataku.R;
import id.itk.yaf.wisataku.Utility.ImageUtility;
import id.itk.yaf.wisataku.Utility.MD5Encode;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    @BindView(R.id.btnSelectImageRegister)
    Button btnSelectImageRegister;

    @BindView(R.id.nameRegister)
    EditText name;

    @BindView(R.id.emailRegister)
    EditText email;

    @BindView(R.id.usernameRegister)
    EditText username;

    @BindView(R.id.passwordRegister)
    EditText password;

    @BindView(R.id.webRegister)
    EditText website;

    @BindView(R.id.displayPictureRegister)
    CircleImageView displayPictureRegister;

    @BindView(R.id.linearLayoutSelectImageRegister)
    LinearLayout linearLayoutSelectImageRegister;

    private static final int REQUEST_CHOOSE_IMAGE = 3;

    private Context mContext;
    private BaseAPIService baseAPIService;
    private ProgressDialog progressDialog;
    Bitmap imageSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (shouldAskPermissions()) {
            askPermissions();
        }

        ButterKnife.bind(this);
        Nammu.init(this);
        mContext = this;
        baseAPIService = UtilsAPI.getAPIService();
        EasyImage.configuration(this)
                .setImagesFolderName("WisataKu") // images folder name, default is "EasyImage"
                .saveInRootPicturesDirectory(); // if you want to use internal memory for storying images - default

    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @OnClick(R.id.btnToLoginActivity)
    public void toLoginActivity(){
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnSelectImageRegister)
    public void selectImage(){
        EasyImage.openChooserWithGallery(Register.this, "Choose Picture", REQUEST_CHOOSE_IMAGE);
    }

    @OnClick(R.id.btnPRocedRegister)
    public void procedRegister(){
        progressDialog = ProgressDialog.show(mContext, null, "Please Wait ...", true, false);

        baseAPIService.registerRequest(
                name.getText().toString(),
                email.getText().toString(),
                username.getText().toString(),
                MD5Encode.md5(password.getText().toString()),
                website.getText().toString(),
                ImageUtility.convert(imageSelected)
        )
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonResult = new JSONObject(response.body().string());

                                if (jsonResult.getString("error").equals("false")){
                                    Toast.makeText(mContext, "Register Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, Login.class);
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
                        .start(Register.this);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        .into(displayPictureRegister);

                linearLayoutSelectImageRegister.setVisibility(View.VISIBLE);
                btnSelectImageRegister.setVisibility(View.GONE);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
