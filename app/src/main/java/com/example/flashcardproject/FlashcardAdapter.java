package com.example.flashcardproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.ViewHolder> {

    private List<Flashcard> flashcards;
    private HomeActivity homeActivity;

    public FlashcardAdapter(List<Flashcard> flashcards, HomeActivity homeActivity) {
        this.flashcards = flashcards;
        this.homeActivity = homeActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flashcard flashcard = flashcards.get(position);
        holder.tvQuestion.setText(flashcard.getQuestion());

        // Handle edit click
        holder.imgEdit.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity, FlashcardCreationActivity.class);
            intent.putExtra("flashcard_id", flashcard.getId());
            homeActivity.startActivity(intent);
        });

        // Handle delete click
        holder.imgDelete.setOnClickListener(v -> deleteFlashcard(flashcard.getId()));
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion;
        ImageView imgEdit, imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            imgEdit = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
        }
    }

    private void deleteFlashcard(String flashcardId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Flashcards");
        database.child(flashcardId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                homeActivity.fetchFlashcards();
            }
        });
    }
}
