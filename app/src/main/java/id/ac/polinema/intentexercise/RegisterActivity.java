package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;
    EditText nameInput, emailInput, passwordInput, confirmPassInput,HomePageInput, aboutYouInput;
    ImageView imageProfile, imageEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.text_fullname);
        emailInput = findViewById(R.id.text_email);
        passwordInput = findViewById(R.id.text_password);
        confirmPassInput = findViewById(R.id.text_confirm_password);
        HomePageInput = findViewById(R.id.text_homepage);
        aboutYouInput = findViewById(R.id.text_about);
        imageProfile = findViewById(R.id.image_profile);
        imageEdit = findViewById(R.id.imageView);


        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(RegisterActivity.this);
            }
        });
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GALLERY_REQUEST_CODE);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageProfile.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageProfile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                            if (data != null) {
                                try {
                                    Uri imageUri = data.getData();
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                                    imageProfile.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        }

                    }
                    break;
            }
        }
    }

    public void handleRegister(View view) {
        if(nameInput.getText().toString().length()==0 && emailInput.getText().toString().length()==0 && passwordInput.getText().toString().length()==0 && confirmPassInput.getText().toString().length()==0 && HomePageInput.getText().toString().length()==0 && aboutYouInput.getText().toString().length()==0){
            nameInput.setError("isi nama ");
            emailInput.setError("isi");
            passwordInput.setError("isi");
            confirmPassInput.setError("isi");
            HomePageInput.setError("isi");
            aboutYouInput.setError("isi");

        }
        else{
            String name = nameInput.getText().toString();
            String email = emailInput.getText().toString();
            String pass = passwordInput.getText().toString();
            String passCon = confirmPassInput.getText().toString();
            String homePage = HomePageInput.getText().toString();
            String aboutYou = aboutYouInput.getText().toString();
//        String img = imageProfile.getScaleType().toString();



            Intent intent = new Intent(this, ProfileActivity.class);
//        Bitmap bmp = null;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.PNG, 50, baos);
//        intent.putExtra("img", img);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("pass", pass);
            intent.putExtra("conPass", passCon);
            intent.putExtra("homepage", homePage);
            intent.putExtra("aboutyou", aboutYou);
            startActivity(intent);
        }

    }
}
