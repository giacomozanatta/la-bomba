package com.jackoza.labomba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public String[] rules = new String[]{"NON ALLA FINE", "NON ALL'INIZIO", "OVUNQUE","NON ALLA FINE", "NON ALL'INIZIO", "OVUNQUE","NON ALLA FINE", "NON ALL'INIZIO", "OVUNQUE"};
    public String[] words = new String[]{
            "IPO",
            "MA",
            "AM",
            "FRA",
            "BA",
            "FE",
            "STO",
            "ITO",
            "TO",
            "TRO",
            "DO",
            "ZU",
            "CO",
            "LO",
            "LA",
            "ME",
            "MA",
            "CA",
            "NE",
            "NA",
            "NO",
            "ZA",
            "PA",
            "PE",
            "GHE",
            "GHI",
            "GI",
            "CI",
            "TE",
            "STA",
            "LE",
            "LI",
            "GIO",
            "FRO",
            "FRE",
            "ES",
            "SIA",
            "VO",
            "VE",
            "TRE",
            "FA",
            "FRA",
            "DE",
            "ETO",
            "ATO",
            "ITO",
            "GRE",
            "ETA",
            "ETO",
            "RO",
            "SA",
            "SE",
            "RE",
            "ZO",
            "PO"};
    int counter = 0;
    TextView ruleText;
    TextView wordText;
    TextView newGame;
    Random rn = new Random();
    MediaPlayer alarm01;
    MediaPlayer alarm02;
    MediaPlayer explosion;
    TextView exit;
    CountDownTimer countdown1;
    CountDownTimer countdown2;
    ImageView imageBomb;
    RecyclerView playerListRecyclerView;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayoutManager;
    TextView addPlayer;
    ConstraintLayout addNewPlayerContainer;
    TextView savePlayer;
    TextView backButtonNewPlayer;
    EditText newPlayerName;
    LinearLayout playerListContainer;
    // players
    ArrayList<Player> players = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(imageBomb);
        Glide.with(this).load(R.raw.bomba).into(imageViewTarget);

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPlayerName.setText("");
                addNewPlayerContainer.setVisibility(View.VISIBLE);
                savePlayer.setVisibility(View.INVISIBLE);
            }
        });
        newPlayerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    savePlayer.setVisibility(View.VISIBLE);
                } else {
                    savePlayer.setVisibility(View.INVISIBLE);
                }
            }
        });
        backButtonNewPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide keyboard
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                // hide container
                addNewPlayerContainer.setVisibility(View.INVISIBLE);
                savePlayer.setVisibility(View.INVISIBLE);
            }
        });
        savePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide keyboard
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                // hide container
                addNewPlayerContainer.setVisibility(View.INVISIBLE);
                savePlayer.setVisibility(View.INVISIBLE);
                // add new player to the list
                players.add(new Player(newPlayerName.getText().toString()));
            }
        });
        instantiateAudio();
        shuffleWords();
        counter = 0;

        explosion.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                exitCurrentGame();
            }
        });
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitCurrentGame();
            }
        });
        // Player list

        rvLayoutManager = new LinearLayoutManager(this);
        playerListRecyclerView.setHasFixedSize(false);
        rvAdapter = new PlayerAdapter(players);
        playerListRecyclerView.setLayoutManager(rvLayoutManager);
        playerListRecyclerView.setAdapter(rvAdapter);

    }

    private void newGame() {
        playerListContainer.setVisibility(View.INVISIBLE);
        imageBomb.setVisibility(View.VISIBLE);
        ruleText.setText(rules[rn.nextInt(9)]);
        alarm01.start();
        newGame.setVisibility(View.INVISIBLE);
        exit.setVisibility(View.VISIBLE);
        wordText.setText(words[(counter+1)%words.length]);
        counter++;
        countdown1 = new CountDownTimer(rn.nextInt ((60000 - 10000 + 1)) + 10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            public void onFinish() {
                alarm01.pause();
                alarm01.seekTo(0);
                alarm02.start();
                countdown2 = new CountDownTimer(rn.nextInt ((5000 + 15000 + 1)) + 5000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) { }
                    public void onFinish() {
                        alarm02.pause();
                        alarm02.seekTo(0);
                        explosion.start();
                    }
                }.start();
            }
        }.start();
    }

    private void exitCurrentGame() {
        playerListContainer.setVisibility(View.VISIBLE);
        imageBomb.setVisibility(View.INVISIBLE);
        ruleText.setText("LA BOMBA!");
        wordText.setText("TICK TACK");
        if (countdown1 != null) {
            countdown1.cancel();
        }
        if (countdown2 != null) {
            countdown2.cancel();
        }
        if(alarm02.isPlaying()) {
            alarm02.pause();
        }
        alarm02.seekTo(0);
        if(alarm01.isPlaying()) {
            alarm01.pause();
        }
        alarm01.seekTo(0);
        if(explosion.isPlaying()) {
            explosion.pause();
        }
        explosion.seekTo(0);
        newGame.setVisibility(View.VISIBLE);
        exit.setVisibility(View.INVISIBLE);
    }

    private void findViews() {
        ruleText = findViewById(R.id.Rule);
        wordText = findViewById(R.id.Word);
        newGame = findViewById(R.id.NewGame);
        exit = findViewById(R.id.Exit);
        imageBomb = findViewById(R.id.imageBomb);
        addPlayer = findViewById(R.id.AddPlayer);
        addNewPlayerContainer = findViewById(R.id.AddNewPlayer);
        addNewPlayerContainer.setVisibility(View.INVISIBLE);
        savePlayer = findViewById(R.id.SavePlayer);
        backButtonNewPlayer = findViewById(R.id.NewPlayerBackButton);
        newPlayerName = findViewById(R.id.EditTextTextPersonName);
        playerListRecyclerView = findViewById(R.id.PlayerViewRecyclerList);
        playerListContainer = findViewById(R.id.PlayerListContainer);
    }

    private void instantiateAudio() {
        alarm01 = MediaPlayer.create(this, R.raw.alarm_01);
        alarm01.setLooping(true);
        alarm02 = MediaPlayer.create(this, R.raw.alarm_02);
        alarm02.setLooping(true);
        explosion = MediaPlayer.create(this, R.raw.explosion);
    }

    private void shuffleWords() {
        List<String> wordsList = Arrays.asList(words);
        Collections.shuffle(wordsList);
        Collections.shuffle(wordsList);
        Collections.shuffle(wordsList);
        wordsList.toArray(words);
    }
}