package com.ath.ludo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerSetupActivity extends AppCompatActivity {

    private EditText player1Name, player2Name, player3Name, player4Name;
    private RadioGroup playerModeGroup;
    private ImageButton startGameButton;
    private int playerCount=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_setup_activity);

        player1Name = findViewById(R.id.player1Name);
        player2Name = findViewById(R.id.player2Name);
        player3Name = findViewById(R.id.player3Name);
        player4Name = findViewById(R.id.player4Name);

        playerModeGroup = findViewById(R.id.playerModeGroup);
        startGameButton = findViewById(R.id.startGameButton);

        setPLayerVisibility(4);

        playerModeGroup.setOnCheckedChangeListener((group, checkedId) ->{
            if(checkedId == R.id.twoPlayerMode) {
                setPLayerVisibility(2);
            } else if (checkedId == R.id.twoPlayerMode) {
                setPLayerVisibility(2);
            } else if (checkedId ==R.id.threePlayerMode) {
                setPLayerVisibility(3);
            } else if (checkedId== R.id.fourPlayerMode) {
                setPLayerVisibility(4);
            }
        });
        startGameButton.setOnClickListener(v ->  startGame());
    }

    private void setPLayerVisibility(int playerCount) {
        player1Name.setVisibility(View.VISIBLE);
        player3Name.setVisibility(View.VISIBLE);
        player2Name.setVisibility(playerCount >= 3? View.VISIBLE: View.GONE);
        player4Name.setVisibility(playerCount >= 4? View.VISIBLE: View.GONE);
    }

    private void startGame() {
        String name1 = getPlayerNameOrDefault(player1Name, "Player 1");
        String name3 = getPlayerNameOrDefault(player3Name, "Player 3");
        String name2 = (player2Name.getVisibility() == View.VISIBLE) ? getPlayerNameOrDefault(player2Name, "Player 2") : null;
        String name4 = (player4Name.getVisibility() == View.VISIBLE) ? getPlayerNameOrDefault(player4Name, "Player 4") : null;

        StringBuilder toastMessage = new StringBuilder("Starting game with: " + name1 + ", " + name2);
        if (name3 != null) {
            toastMessage.append(", ").append(name3);
        }
        if (name4 != null) {
            toastMessage.append(", ").append(name4);
        }

        Toast.makeText(this, toastMessage.toString(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(PlayerSetupActivity.this, LudoBoard.class);
        intent.putExtra("playerCount", playerCount);
        intent.putExtra("player1", name1);
        intent.putExtra("player2", name2);
        intent.putExtra("player3", name3);
        intent.putExtra("player4", name4);
        startActivity(intent);
    }
    private String getPlayerNameOrDefault(EditText editText, String defaultName) {
        return TextUtils.isEmpty(editText.getText().toString())? defaultName : editText.getText().toString();
    }
}

