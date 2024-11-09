package com.example.flashcardproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlashcardAdapter flashcardAdapter;
    private List<Flashcard> flashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        flashcards = new ArrayList<>();
        flashcardAdapter = new FlashcardAdapter(flashcards, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(flashcardAdapter);

        // Fetch flashcards from Firebase
        fetchFlashcards();

        // Add new flashcard
        findViewById(R.id.btnAddFlashcard).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FlashcardCreationActivity.class);
            startActivity(intent);
        });
    }

    // Change visibility from private to public or protected
    public void fetchFlashcards() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Flashcards");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flashcards.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Flashcard flashcard = snapshot.getValue(Flashcard.class);
                    flashcard.setId(snapshot.getKey());
                    flashcards.add(flashcard);
                }
                flashcardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Failed to load flashcards.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
