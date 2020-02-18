package com.example.multichoicesquizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    private Spinner spinnerDifficulty;
    Button button;
    ListView listView;
    String mTitle[] = {"Povijest", "Zemljopis", "Biologija", "Računala", "Matematika"};
    int Images[] = {R.drawable.povijest, R.drawable.zemljopis, R.drawable.biologija, R.drawable.tehnologija, R.drawable.matematika};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        List<Category> categories = dbHelper.getAllCategories();

        listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, mTitle, Images, spinnerDifficulty);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Toast.makeText(SecondActivity.this, "Povijest", Toast.LENGTH_SHORT).show();
                }
                if (position == 1) {
                    Toast.makeText(SecondActivity.this, "Zemljopis", Toast.LENGTH_SHORT).show();
                }
                if (position == 2) {
                    Toast.makeText(SecondActivity.this, "Biologija", Toast.LENGTH_SHORT).show();
                }
                if (position == 3) {
                    Toast.makeText(SecondActivity.this, "Računala", Toast.LENGTH_SHORT).show();
                }
                if (position == 4) {
                    Toast.makeText(SecondActivity.this, "Matematika", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }




    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        int rImage[];
        Spinner rSpinner;

        MyAdapter(Context context, String title[], int Imgs[], Spinner spinner) {
            super(context, R.layout.row, title);
            this.context = context;
            this.rTitle = title;
            this.rImage = Imgs;
            this.rSpinner = spinner;
        }

        @Override
        public View getView(final int position,  View convertView,  ViewGroup parent) {


            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView);
            button = row.findViewById(R.id.Ok);
            final Spinner spinnerDifficulty = row.findViewById(R.id.spinner_difficulty);

            String[] difficultyLevels = Question.getAllDifficultyLevels();
            ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, difficultyLevels);
            adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDifficulty.setAdapter(adapterDifficulty);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String difficulty = spinnerDifficulty.getSelectedItem().toString();

                    if (position == 0) {
                        Intent intent = new Intent(SecondActivity.this, QuizActivity.class);
                        intent.putExtra(EXTRA_CATEGORY_ID, 1);
                        intent.putExtra(EXTRA_CATEGORY_NAME, "Povijest");
                        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
                        startActivity(intent);

                    }




                    if (position == 1) {
                        Intent intent = new Intent(SecondActivity.this, QuizActivity.class);
                        intent.putExtra(EXTRA_CATEGORY_ID, 2);
                        intent.putExtra(EXTRA_CATEGORY_NAME, "Zemljopis");
                        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
                        startActivity(intent);
                    }

                    if (position == 2) {
                        Intent intent = new Intent(SecondActivity.this, QuizActivity.class);
                        intent.putExtra(EXTRA_CATEGORY_ID, 3);
                        intent.putExtra(EXTRA_CATEGORY_NAME, "Biologija");
                        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
                        startActivity(intent);
                    }

                    if (position == 3) {
                        Intent intent = new Intent(SecondActivity.this, QuizActivity.class);
                        intent.putExtra(EXTRA_CATEGORY_ID, 4);
                        intent.putExtra(EXTRA_CATEGORY_NAME, "Računala");
                        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
                        startActivity(intent);
                    }

                    if (position == 4) {
                        Intent intent = new Intent(SecondActivity.this, QuizActivity.class);
                        intent.putExtra(EXTRA_CATEGORY_ID, 5);
                        intent.putExtra(EXTRA_CATEGORY_NAME, "Matematika");
                        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
                        startActivity(intent);
                    }


                }
            });

            images.setImageResource(rImage[position]);
            myTitle.setText(rTitle[position]);
            return row;
        }


    }
}
