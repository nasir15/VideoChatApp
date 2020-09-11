package com.example.videochatapp4;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    //    private Button button;
    private CircleImageView call;
    ImageView simpleImageView;
    Button firebase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toast.makeText(MainActivity.this,"Firebase connection success",Toast.LENGTH_LONG).show();
//        button= (Button) findViewById(R.id.button);
        call=(CircleImageView) findViewById(R.id.calls);


//        simpleImageView=(ImageView) findViewById(R.id.imageView);
//        simpleImageView.setImageResource(R.drawable.cncd2);




        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }
    public void openActivity2() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
    public void openActivity3() {
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }
}
