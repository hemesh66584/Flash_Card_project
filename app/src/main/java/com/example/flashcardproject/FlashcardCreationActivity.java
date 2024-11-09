package com.example.flashcardproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FlashcardCreationActivity extends AppCompatActivity {

    private EditText editTextQuestion, editTextAnswer;
    private Button btnSave;
    private String flashcardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_creation);

        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        btnSave = findViewById(R.id.btnSave);

        flashcardId = getIntent().getStringExtra("flashcard_id");

        if (flashcardId != null) {
            // Edit existing flashcard
            DatabaseReference database = FirebaseDatabase.getInstance().getReference("Flashcards").child(flashcardId);
            database.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    Flashcard flashcard = task.getResult().getValue(Flashcard.class);
                    editTextQuestion.setText(flashcard.getQuestion());
                    editTextAnswer.setText(flashcard.getAnswer());
                }
            });
        }

        btnSave.setOnClickListener(v -> saveFlashcard());
    }

    private void saveFlashcard() {
        String question = editTextQuestion.getText().toString().trim();
        String answer = editTextAnswer.getText().toString().trim();

        if (TextUtils.isEmpty(question) || TextUtils.isEmpty(answer)) {
            Toast.makeText(this, "Please fill in both fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Flashcard flashcard = new Flashcard(question, answer);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Flashcards");

        if (flashcardId != null) {
            // Edit existing flashcard
            database.child(flashcardId).setValue(flashcard).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Flashcard updated.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to update flashcard.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Create new flashcard
            database.push().setValue(flashcard).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Flashcard added.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to add flashcard.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
