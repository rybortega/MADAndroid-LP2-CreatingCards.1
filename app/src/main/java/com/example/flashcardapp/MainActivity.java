package com.example.flashcardapp;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean selected = false;

        View possibleAnswer1 = findViewById(R.id.possible_answer1);
        View possibleAnswer2 = findViewById(R.id.possible_answer2);
        View possibleAnswer3 = findViewById(R.id.possible_answer3);

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (selected){
//                    possibleAnswer1.setBackgroundColor(getColor(R.color.red));
//                    pos
//                }
            }
        });

        View.OnClickListener wrongAnswer = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getColor(R.color.red));
                possibleAnswer3.setBackgroundColor(getColor(R.color.lime));
            }
        };

        View.OnClickListener rightAnswer = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getColor(R.color.lime));
            }
        };

        possibleAnswer1.setOnClickListener(wrongAnswer);
        possibleAnswer2.setOnClickListener(wrongAnswer);
        possibleAnswer3.setOnClickListener(rightAnswer);


    }


}