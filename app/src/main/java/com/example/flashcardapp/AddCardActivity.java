package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
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

                Intent data = new Intent();
                data.putExtra("questionValue" , question);
                data.putExtra("answerValue" , answer);

                setResult(RESULT_OK , data);
                finish();
            }
        });
    }
}