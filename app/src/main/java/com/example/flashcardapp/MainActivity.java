package com.example.flashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View possibleAnswer1 = findViewById(R.id.possible_answer1);
        View possibleAnswer2 = findViewById(R.id.possible_answer2);
        View possibleAnswer3 = findViewById(R.id.possible_answer3);

        TextView flashcardQuestion = findViewById(R.id.flashcard_question);
        TextView flashcardAnswer = findViewById(R.id.flashcard_answer);

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
                    ((ImageView)view).setImageResource(R.drawable.icon_show);
                else ((ImageView)view).setImageResource(R.drawable.icon_hide);
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

                ((ImageView)findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.icon_show);
                isShowingAnswers[0] = false;
            }
        });

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);



            }
        });
        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AddCardActivity.class);
                intent.putExtra("questionValue" , flashcardQuestion.getText().toString());
                intent.putExtra("answerValue" , flashcardAnswer.getText().toString());
                MainActivity.this.startActivityForResult(intent, 100);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null){
            Snackbar.make(findViewById(R.id.flashcard_question),
                    "Card successfully created",
                    Snackbar.LENGTH_SHORT)
                    .show();
            String questionValue = data.getExtras().getString("questionValue");
            String answerValue = data.getExtras().getString("answerValue");
            ((TextView) findViewById(R.id.flashcard_question)).setText(questionValue);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(answerValue);
        }
    }
}