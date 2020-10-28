package com.jackoza.labomba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private ArrayList<Player> players;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_entry, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public PlayerAdapter(ArrayList<Player> players) {
        this.players = players;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Player currentPlayer = players.get(position);
        final int _position = position;
        final ViewHolder _holder = holder;
        holder.playerName.setText(currentPlayer.getName());
        holder.bombs.setText("x"+currentPlayer.getBomb());
        holder.addBombs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer.increaseBomb();
                _holder.bombs.setText("x"+currentPlayer.getBomb());
            }
        });
        holder.removeBombs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer.decreaseBomb();
                _holder.bombs.setText("x"+currentPlayer.getBomb());
            }
        });
        holder.playerLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (players.)
                players.remove(_position);
                notifyItemRemoved(_position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout row;
        public TextView playerName;
        public TextView bombs;
        public TextView addBombs;
        public TextView removeBombs;
        public ConstraintLayout playerLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            row = itemView.findViewById(R.id.PlayerRow);
            playerName = itemView.findViewById(R.id.PlayerName);
            addBombs = itemView.findViewById(R.id.PlayerBombAdd);
            removeBombs = itemView.findViewById(R.id.PlayerBombRemove);
            bombs = itemView.findViewById(R.id.PlayerBombNumber);
            playerLayout = itemView.findViewById(R.id.PlayerRow);
        }
    }

}