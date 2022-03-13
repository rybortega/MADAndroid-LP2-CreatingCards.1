package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        String question = getIntent().getStringExtra("questionValue");
        String answer = getIntent().getStringExtra("answerValue");
        if (answer != null && question != null){
            ((EditText)findViewById(R.id.question_editText)).setText(question);
            ((EditText)findViewById(R.id.answer_editText)).setText(answer);
        }

        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = ((EditText)findViewById(R.id.question_editText)).getText().toString();
                String answer = ((EditText)findViewById(R.id.answer_editText)).getText().toString();
                if (question.isEmpty() || answer.isEmpty()){
                    Toast.makeText(getApplicationContext() , "Must enter both question and answer!" , Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent data = new Intent();
                data.putExtra("questionValue" , question);
                data.putExtra("answerValue" , answer);

                setResult(RESULT_OK , data);
                finish();
            }
        });
    }
}