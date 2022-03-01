package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View possibleAnswer1 = findViewById(R.id.possible_answer1);
        View possibleAnswer2 = findViewById(R.id.possible_answer2);
        View possibleAnswer3 = findViewById(R.id.possible_answer3);

        View flashcardQuestion = findViewById(R.id.flashcard_question);
        View flashcardAnswer = findViewById(R.id.flashcard_answer);

        View.OnClickListener flipFlashcard = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int questionVisibility = flashcardQuestion.getVisibility();
                int answerVisibility = flashcardAnswer.getVisibility();

                flashcardQuestion.setVisibility(answerVisibility);
                flashcardAnswer.setVisibility(questionVisibility);

            }
        };
        flashcardAnswer.setOnClickListener(flipFlashcard);
        flashcardQuestion.setOnClickListener(flipFlashcard);

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

        final boolean[] isShowingAnswers = {false};
        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                possibleAnswer1.setVisibility(4 - possibleAnswer1.getVisibility());
                possibleAnswer2.setVisibility(4 - possibleAnswer2.getVisibility());
                possibleAnswer3.setVisibility(4 - possibleAnswer3.getVisibility());
                if (isShowingAnswers[0])
                    ((ImageView)view).setImageResource(R.drawable.show_icon);
                else ((ImageView)view).setImageResource(R.drawable.hide_icon);
                isShowingAnswers[0] = !isShowingAnswers[0];
            }
        });
        findViewById(R.id.reset_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                possibleAnswer1.setVisibility(View.INVISIBLE);
                possibleAnswer2.setVisibility(View.INVISIBLE);
                possibleAnswer3.setVisibility(View.INVISIBLE);

                possibleAnswer1.setBackground(getDrawable(R.drawable.possible_answer_background));
                possibleAnswer2.setBackground(getDrawable(R.drawable.possible_answer_background));
                possibleAnswer3.setBackground(getDrawable(R.drawable.possible_answer_background));


                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);

                ((ImageView)findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.show_icon);
                isShowingAnswers[0] = false;
            }
        });

    }


}