package com.example.multichoicesquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";
    TextView textViewHighScore;
    Button buttonTryAgain, buttonGoToStart;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Bundle extras = getIntent().getExtras();
        String categoryName = extras.getString("category");
        Integer categoryId = extras.getInt("categoryId");
        String difficulty = extras.getString("difficulty");

        final int score = extras.getInt("score");


        QuizDbHelper.getInstance(this).addHighscore(String.valueOf(score), MainActivity.name, categoryId, difficulty, categoryName);

        textViewHighScore = findViewById(R.id.text_view_highscore);
        textViewHighScore.setText("Your HighScore: " + score);
        buttonTryAgain = findViewById(R.id.try_again);
        buttonGoToStart = findViewById(R.id.start);
        image = findViewById(R.id.slika);
        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goBack();

            }
        });

        buttonGoToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStart();
            }
        });

    }

    public  void goBack(){
        Intent intent = new Intent(HighScoreActivity.this, SecondActivity.class);
        startActivity(intent);

    }

    public void goToStart(){
        Intent intent = new Intent(HighScoreActivity.this, MainActivity.class);
        startActivity(intent);

    }


}
