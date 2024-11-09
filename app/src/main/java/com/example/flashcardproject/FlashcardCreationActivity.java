package com.example.flashcardproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;

public class FlashcardCreationActivity extends AppCompatActivity {

    private EditText editTextQuestion, editTextAnswer;
    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_creation);

        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(view -> {
            String question = editTextQuestion.getText().toString();
            String answer = editTextAnswer.getText().toString();

            if (!question.isEmpty() && !answer.isEmpty()) {
                Flashcard flashcard = new Flashcard(question, answer);
                FirebaseDatabase.getInstance().getReference("Flashcards")
                        .push().setValue(flashcard); // Save to Firebase
                finish();
            }
        });
    }
}
