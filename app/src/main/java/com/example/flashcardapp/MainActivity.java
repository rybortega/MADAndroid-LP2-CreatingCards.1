package com.example.flashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    final boolean[] isShowingAnswers = {false};
    boolean wrongAnswers = false;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        initFlashcard();

        TextView possibleAnswer1 = findViewById(R.id.possible_answer1);
        TextView possibleAnswer2 = findViewById(R.id.possible_answer2);
        TextView possibleAnswer3 = findViewById(R.id.possible_answer3);
        TextView [] possibleAnswer = {possibleAnswer1 , possibleAnswer2 , possibleAnswer3};
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

        View.OnClickListener wrongAnswerView = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getColor(R.color.red));
                possibleAnswer3.setBackgroundColor(getColor(R.color.lime));
            }
        };

        View.OnClickListener rightAnswerView = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(getColor(R.color.lime));
            }
        };

//        Random random = new Random();
//        int rightAnswer = random.nextInt(3)+1; // [1-3]
//        for (int i = 0; i < possibleAnswer.length ; i++){
//            if (i == rightAnswer){
//                possibleAnswer[i].setOnClickListener(rightAnswerView);
//            }
//            else possibleAnswer[i].setOnClickListener(wrongAnswerView);
//        }
        possibleAnswer1.setOnClickListener(wrongAnswerView);
        possibleAnswer2.setOnClickListener(wrongAnswerView);
        possibleAnswer3.setOnClickListener(rightAnswerView);


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
                ResetActivity();
            }
        });

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AddCardActivity.class);
                intent.putExtra("actionType" , "add");
                MainActivity.this.startActivityForResult(intent, 100);

            }
        });
        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , AddCardActivity.class);
                intent.putExtra("questionValue" , flashcardQuestion.getText().toString());
                intent.putExtra("answerValue" , flashcardAnswer.getText().toString());
                intent.putExtra("actionType" , "edit");
                MainActivity.this.startActivityForResult(intent, 100);

            }
        });

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardQuestion.setText(allFlashcards.get(++currentCardDisplayedIndex).getQuestion());
                flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());

                ResetActivity();
            }
        });
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardQuestion.setText(allFlashcards.get(--currentCardDisplayedIndex).getQuestion());
                flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());

                ResetActivity();
            }
        });
        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardDatabase.deleteCard(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                allFlashcards.remove(currentCardDisplayedIndex);

                if (currentCardDisplayedIndex >= allFlashcards.size()) currentCardDisplayedIndex = allFlashcards.size()-1;
                ResetActivity();

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
            String wrongAnswer1 = data.getExtras().getString("wrongAnswer1");
            String wrongAnswer2 = data.getExtras().getString("wrongAnswer2");

            wrongAnswers = !wrongAnswer1.isEmpty() && !wrongAnswer2.isEmpty();

            Flashcard newFlashcard = new Flashcard(questionValue, answerValue);
            String text = data.getExtras().getString("actionType");
            if (text.compareTo("add") == 0) {
                flashcardDatabase.insertCard(newFlashcard);
                allFlashcards.add(newFlashcard);
            }
            if (text.compareTo("edit") == 0){
                flashcardDatabase.updateCard(newFlashcard);
                allFlashcards.set(currentCardDisplayedIndex, newFlashcard);
            }
            ResetActivity();

        }
    }
    // Set the flashcard to default
    // All possible answers (INVISIBLE by default)
    // Update the next and back button
    public void ResetActivity(){ // Reset to default
        TextView possibleAnswer1 = findViewById(R.id.possible_answer1);
        TextView possibleAnswer2 = findViewById(R.id.possible_answer2);
        TextView possibleAnswer3 = findViewById(R.id.possible_answer3);

        possibleAnswer1.setVisibility(View.INVISIBLE);
        possibleAnswer2.setVisibility(View.INVISIBLE);
        possibleAnswer3.setVisibility(View.INVISIBLE);

        possibleAnswer1.setBackground(getDrawable(R.drawable.possible_answer_background));
        possibleAnswer2.setBackground(getDrawable(R.drawable.possible_answer_background));
        possibleAnswer3.setBackground(getDrawable(R.drawable.possible_answer_background));


        findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
        ((TextView)findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());


        ((ImageView)findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.icon_show);
//        if (allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1().isEmpty() ||
//                allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2().isEmpty()) wrongAnswers = false;
        ((ImageView)findViewById(R.id.toggle_choices_visibility)).setVisibility(wrongAnswers ? View.VISIBLE : View.INVISIBLE);
        isShowingAnswers[0] = false;

        updateNextAndBackBotton();
        ((ImageView)findViewById(R.id.delete_button)).setVisibility((allFlashcards.size() <= 1) ? View.INVISIBLE : View.VISIBLE);
    }

    public void updateNextAndBackBotton(){
        ((ImageView)findViewById(R.id.next_button)).setVisibility(currentCardDisplayedIndex < allFlashcards.size()-1 ? View.VISIBLE : View.INVISIBLE);
        ((ImageView)findViewById(R.id.back_button)).setVisibility(currentCardDisplayedIndex > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    public void initFlashcard(){
        flashcardDatabase.initFirstCard();
        allFlashcards = flashcardDatabase.getAllCards();

        ResetActivity();

    }

}