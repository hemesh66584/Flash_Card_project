package com.example.flashcardproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout linearLayout;  // LinearLayout for dynamic views
    private FlashcardAdapter adapter;
    private FloatingActionButton fabAdd;
    private List<Flashcard> flashcardList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        linearLayout = findViewById(R.id.linearLayout);  // Updated to match XML ID
        fabAdd = findViewById(R.id.fab_add);

        // Initialize flashcard list
        flashcardList = new ArrayList<>();

        // Setup for adapter if needed, or add views directly to linearLayout

        loadFlashcards();

        // Add button to create a new flashcard
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, FlashcardCreationActivity.class);
            startActivity(intent);
        });
    }

    private void loadFlashcards() {
        // Code to load flashcards from Firebase or another source
    }
}
