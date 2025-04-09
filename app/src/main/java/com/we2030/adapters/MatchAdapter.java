package com.we2030.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.we2030.R;
import com.we2030.models.Match;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {
    private static final String TAG = "MatchAdapter";
    private List<Match> matches;
    private SimpleDateFormat dateFormat;

    public MatchAdapter(List<Match> matches) {
        this.matches = matches;
        this.dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.FRANCE);
        Log.d(TAG, "MatchAdapter créé avec " + (matches != null ? matches.size() : 0) + " matches");
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
        try {
            Match match = matches.get(position);
            Log.d(TAG, "Liaison du match: " + match.getTeam1() + " vs " + match.getTeam2());
            
            // Information du match
            holder.stageTextView.setText(match.getStage());
            holder.dateTextView.setText(match.getDate());
            holder.timeTextView.setText(match.getTime());
            holder.team1TextView.setText(match.getTeam1());
            holder.team2TextView.setText(match.getTeam2());
            holder.stadiumTextView.setText(match.getStadium());
            
            // Réinitialiser la visibilité des vues
            holder.score1TextView.setVisibility(View.VISIBLE);
            holder.score2TextView.setVisibility(View.VISIBLE);
            holder.vsTextView.setVisibility(View.VISIBLE);
            
            // Gérer l'affichage selon le statut du match
            String status = match.getStatus();
            holder.statusTextView.setText(status);
            
            switch (status) {
                case "Terminé":
                    holder.statusTextView.setTextColor(Color.RED);
                    holder.score1TextView.setText(String.valueOf(match.getTeam1Score()));
                    holder.score2TextView.setText(String.valueOf(match.getTeam2Score()));
                    holder.vsTextView.setVisibility(View.GONE);
                    break;
                    
                case "En cours":
                    holder.statusTextView.setTextColor(Color.GREEN);
                    holder.score1TextView.setText(String.valueOf(match.getTeam1Score()));
                    holder.score2TextView.setText(String.valueOf(match.getTeam2Score()));
                    holder.vsTextView.setVisibility(View.GONE);
                    break;
                    
                default: // À venir
                    holder.statusTextView.setTextColor(Color.BLUE);
                    holder.score1TextView.setVisibility(View.GONE);
                    holder.score2TextView.setVisibility(View.GONE);
                    holder.vsTextView.setVisibility(View.VISIBLE);
                    break;
            }
            
            // Charger les drapeaux
            holder.flag1ImageView.setImageResource(match.getTeam1Flag());
            holder.flag2ImageView.setImageResource(match.getTeam2Flag());
            
        } catch (Exception e) {
            Log.e(TAG, "Erreur dans onBindViewHolder: " + e.getMessage(), e);
        }
    }

    @Override
    public int getItemCount() {
        return matches != null ? matches.size() : 0;
    }

    public void updateMatches(List<Match> newMatches) {
        this.matches = newMatches;
        notifyDataSetChanged();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView stageTextView;
        TextView dateTextView;
        TextView timeTextView;
        TextView statusTextView;
        ImageView flag1ImageView;
        TextView team1TextView;
        TextView score1TextView;
        TextView vsTextView;
        TextView score2TextView;
        TextView team2TextView;
        ImageView flag2ImageView;
        TextView stadiumTextView;

        MatchViewHolder(View itemView) {
            super(itemView);
            try {
                stageTextView = itemView.findViewById(R.id.stageTextView);
                dateTextView = itemView.findViewById(R.id.dateTextView);
                timeTextView = itemView.findViewById(R.id.timeTextView);
                statusTextView = itemView.findViewById(R.id.statusTextView);
                flag1ImageView = itemView.findViewById(R.id.flag1ImageView);
                team1TextView = itemView.findViewById(R.id.team1TextView);
                score1TextView = itemView.findViewById(R.id.score1TextView);
                vsTextView = itemView.findViewById(R.id.vsTextView);
                score2TextView = itemView.findViewById(R.id.score2TextView);
                team2TextView = itemView.findViewById(R.id.team2TextView);
                flag2ImageView = itemView.findViewById(R.id.flag2ImageView);
                stadiumTextView = itemView.findViewById(R.id.stadiumTextView);
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de l'initialisation des vues: " + e.getMessage());
            }
        }
    }
} 