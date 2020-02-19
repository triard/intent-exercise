package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView nameOutput, emailOutput, homePageOutput, abotYouOutput;
    ImageView img;
    Button btnHomepage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameOutput = findViewById(R.id.label_fullname);
        emailOutput = findViewById(R.id.label_email);
        homePageOutput = findViewById(R.id.label_homepage);
        abotYouOutput = findViewById(R.id.label_about);
        img = findViewById(R.id.image_profile);
        btnHomepage = findViewById(R.id.button_homepage);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            nameOutput.setText(extras.getString("name"));
            emailOutput.setText(extras.getString("email"));
            homePageOutput.setText(extras.getString("homepage"));
            abotYouOutput.setText(extras.getString("aboutyou"));

            btnHomepage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://"+homePageOutput.getText().toString();
                    Intent Browser = new Intent(Intent.ACTION_VIEW);
                    Browser.setData(Uri.parse(url));
                    startActivity(Browser);
                }
            });


        }
    }

}
