package com.we2030;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchViewHolder> {
    private List<Match> matches;

    public MatchesAdapter(List<Match> matches) {
        this.matches = matches;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matches.get(position);
        holder.team1TextView.setText(match.getTeam1());
        holder.team2TextView.setText(match.getTeam2());
        holder.dateTextView.setText(match.getDate());
        holder.timeTextView.setText(match.getTime());
        holder.stadiumTextView.setText(match.getStadium());
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView team1TextView;
        TextView team2TextView;
        TextView dateTextView;
        TextView timeTextView;
        TextView stadiumTextView;

        MatchViewHolder(View itemView) {
            super(itemView);
            team1TextView = itemView.findViewById(R.id.team1TextView);
            team2TextView = itemView.findViewById(R.id.team2TextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            stadiumTextView = itemView.findViewById(R.id.stadiumTextView);
        }
    }
} 