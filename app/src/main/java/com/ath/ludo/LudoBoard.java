package com.ath.ludo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LudoBoard extends AppCompatActivity {

    private TextView redPlayerName, greenPlayerName, yellowPlayerName, bluePlayerName;
    private int playerCount;

    private ImageButton diceRed, diceGreen, diceYellow, diceBlue;
    ImageView[] redTokens = new ImageView[4];
    ImageView[] blueTokens = new ImageView[4];
    ImageView[] greenTokens = new ImageView[4];
    ImageView[] yellowTokens = new ImageView[4];

    private int currentPlayerTurn = 1;
    //    private int diceResult=1;

    private int seed = 1;
    private final int a = 1664525;
    private final int c = 1013904223;
    private final int m = (int) Math.pow(2, 32);

    // LCG to generate the next random number
    private int lcg() {
        seed = (a * seed + c) % m; // Update the seed
        return Math.abs(seed) % 6 + 1; // Return a dice roll value between 1 and 6
    }
//    private Random random = new Random();

//    private int[][] pathCoordinates = {
//            {209, 40}, {209, 64}, {209, 88}, {209, 112}, {209, 136},
//            {234, 160}, {256, 160}, {281, 160}, {304, 160}, {328, 160}, {352, 160},
//            {352, 185}, {352, 210},
//            {329, 209}, {304, 209}, {281, 209}, {256, 209}, {282, 209},
//            {209, 233}, {209, 256}, {209, 280}, {209, 304}, {209, 328}, {209, 352},
//            {184, 352}, {160, 352},
//            {160, 328}, {160, 304}, {160, 280}, {160, 256}, {160, 232},
//            {136, 209}, {112, 209}, {88, 209}, {64, 209}, {40, 209}, {16, 209},
//            {16, 185}, {16, 160},
//            {40, 160}, {64, 160}, {88, 160}, {112, 160}, {137, 160},
//            {161, 136}, {160, 112}, {160, 88}, {160, 64}, {160, 40}, {160, 16},
//            {185, 16}, {209, 16}
//    };

    private int[][] redPathCoordinates = {
            {209, 40}, {209, 64}, {209, 88}, {209, 112}, {209, 136}, {234, 160}, {256, 160}, {281, 160}, {304, 160},
            {328, 160}, {352, 160}, {352, 185}, {352, 210}
    };

    private int[][] greenPathCoordinates = {
            {40, 159}, {64, 160}, {88, 160}, {112, 160}, {137, 160},
            {161, 136}, {160, 112}, {160, 88}, {160, 64}, {160, 40}, {160, 16},
            {185, 16}, {209, 16}
    };

    private int[][] yellowPathCoordinates = {
            {160, 328}, {160, 304}, {160, 280}, {160, 256}, {160, 232},
            {136, 209}, {112, 209}, {88, 209}, {64, 209}, {40, 209}, {16, 209},
            {16, 185}, {16, 160}
    };

    private int[][] bluePathCoordinates = {
            {329, 209}, {304, 209}, {281, 209}, {256, 209}, {282, 209},
            {209, 233}, {209, 256}, {209, 280}, {209, 304}, {209, 328}, {209, 352},
            {184, 352}, {160, 352},
    };

    private int redStartIndex = 0;
    private int greenStartIndex = 0;
    private int yellowStartIndex = 0;
    private int blueStartIndex = 0;

    private int redEndIndex = 50;
    private int greenEndIndex = 89;
    private int yellowEndIndex = 76;
    private int blueEndIndex = 63;

    private int currentRedPosition = redStartIndex;
    private int currentGreenPosition = greenStartIndex;
    private int currentYellowPosition = yellowStartIndex;
    private int currentBluePosition = blueStartIndex;

    private final int[][] redHomeArea = {
            {268, 75}, {292, 51}, {317, 75}, {293, 100}
    };
    private final int[][] greenHomeArea = {
            {76, 50}, {52, 75}, {76, 99}, {99, 75}
    };

    private final int[][] yellowHomeArea = {
            {76, 267}, {52, 292}, {76, 315}, {100, 292}
    };

    private final int[][] blueHomeArea = {
            {292, 267}, {268, 292}, {292, 316}, {316, 292}
    };

    private ConstraintLayout constraintLayout;
    private ConstraintSet constraintSet;

    public static final int PLAYER_RED = 1;
    public static final int PLAYER_GREEN = 2;
    public static final int PLAYER_YELLOW = 3;
    public static final int PLAYER_BLUE = 4;

    private int previousRedPosition = -1;
    private int previousGreenPosition = -1;
    private int previousYellowPosition = -1;
    private int previousBluePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ludo_board);

        redPlayerName = findViewById(R.id.red_player_name);
        greenPlayerName = findViewById(R.id.green_player_name);
        yellowPlayerName = findViewById(R.id.yellow_player_name);
        bluePlayerName = findViewById(R.id.blue_player_name);

        Intent intent = getIntent();
        playerCount = intent.getIntExtra("playerCount", 4);
        String player1 = intent.getStringExtra("player1");
        String player2 = intent.getStringExtra("player2");
        String player3 = intent.getStringExtra("player3");
        String player4 = intent.getStringExtra("player4");

        redPlayerName.setText(player1);
        greenPlayerName.setText(player2);
        yellowPlayerName.setText(player3);
        bluePlayerName.setText(player4);

        if (playerCount == 2) {
            greenPlayerName.setVisibility(View.GONE);
            bluePlayerName.setVisibility(View.GONE);
        } else if (playerCount == 3) {
            bluePlayerName.setVisibility(View.GONE);
        } else if (playerCount == 4) {
            redPlayerName.setVisibility(View.VISIBLE);
            greenPlayerName.setVisibility(View.VISIBLE);
            yellowPlayerName.setVisibility(View.VISIBLE);
            bluePlayerName.setVisibility(View.VISIBLE);
        }
        diceRed = findViewById(R.id.diceRed);
        diceGreen = findViewById(R.id.diceGreen);
        diceYellow = findViewById(R.id.diceYellow);
        diceBlue = findViewById(R.id.diceBlue);
        if (playerCount == 2) {
            diceGreen.setVisibility(View.GONE);
            diceBlue.setVisibility(View.GONE);
        } else if (playerCount == 3) {
            diceBlue.setVisibility(View.GONE);
        } else if (playerCount == 4) {
            diceRed.setVisibility(View.VISIBLE);
            diceGreen.setVisibility(View.VISIBLE);
            diceYellow.setVisibility(View.VISIBLE);
            diceBlue.setVisibility(View.VISIBLE);
        }

        redTokens[0] = findViewById(R.id.red_token1);
        redTokens[1] = findViewById(R.id.red_token2);
        redTokens[2] = findViewById(R.id.red_token3);
        redTokens[3] = findViewById(R.id.red_token4);

        blueTokens[0] = findViewById(R.id.blue_token1);
        blueTokens[1] = findViewById(R.id.blue_token2);
        blueTokens[2] = findViewById(R.id.blue_token3);
        blueTokens[3] = findViewById(R.id.blue_token4);

        greenTokens[0] = findViewById(R.id.green_token1);
        greenTokens[1] = findViewById(R.id.green_token2);
        greenTokens[2] = findViewById(R.id.green_token3);
        greenTokens[3] = findViewById(R.id.green_token4);

        yellowTokens[0] = findViewById(R.id.yellow_token1);
        yellowTokens[1] = findViewById(R.id.yellow_token2);
        yellowTokens[2] = findViewById(R.id.yellow_token3);
        yellowTokens[3] = findViewById(R.id.yellow_token4);

        updateDiceVisibility(currentPlayerTurn);

        // onClickListeners for dice
        diceRed.setOnClickListener(v -> rollDice(diceRed, PLAYER_RED));
        diceGreen.setOnClickListener(v -> rollDice(diceGreen, PLAYER_GREEN));
        diceYellow.setOnClickListener(v -> rollDice(diceYellow, PLAYER_YELLOW));
        diceBlue.setOnClickListener(v -> rollDice(diceBlue, PLAYER_BLUE));

        constraintLayout = findViewById(R.id.constraint_layout);

        constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
    }

    private void updateDiceVisibility(int currentPlayerTurn) {
        diceRed.setVisibility(View.GONE);
        diceGreen.setVisibility(View.GONE);
        diceYellow.setVisibility(View.GONE);
        diceBlue.setVisibility(View.GONE);

        diceRed.setEnabled(false);
        diceGreen.setEnabled(false);
        diceYellow.setEnabled(false);
        diceBlue.setEnabled(false);
        switch (currentPlayerTurn) {
            case 1:
                diceRed.setVisibility(View.VISIBLE);
                diceRed.setEnabled(true);
                break;
            case 2:
                diceGreen.setVisibility(View.VISIBLE);
                diceGreen.setEnabled(true);
                break;
            case 3:
                diceYellow.setVisibility(View.VISIBLE);
                diceYellow.setEnabled(true);
                break;
            case 4:
                diceBlue.setVisibility(View.VISIBLE);
                diceBlue.setEnabled(true);
                break;
        }
    }

    private void animateTokenBloom(ImageView token, Runnable onAnimationEnd) {
        Animation bloomIn = AnimationUtils.loadAnimation(token.getContext(), R.anim.bloom_in);

        bloomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onAnimationEnd.run();
                Animation bloomOut = AnimationUtils.loadAnimation(token.getContext(), R.anim.bloom_out);
                token.startAnimation(bloomOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        token.startAnimation(bloomIn);
    }

    private void simulateDiceRoll(ImageButton diceButton) {
        Handler handler = new Handler();

        int[] diceImages = {R.drawable.dice_one, R.drawable.dice_two, R.drawable.dice_three,
                R.drawable.dice_four, R.drawable.dice_five, R.drawable.dice_six};

        for (int i = 0; i < 10; i++) {
            int delay = i * 30;
            handler.postDelayed(() -> {
                int randomDiceImage = diceImages[lcg() - 1]; // Use lcg() instead of random.nextInt(6)
                diceButton.setImageResource(randomDiceImage);
            }, delay);
        }
    }


    private ImageView[] getCurrentTokens(int currentPlayerTurn) {
        return switch (currentPlayerTurn) {
            case 1 -> redTokens;
            case 2 -> greenTokens;
            case 3 -> yellowTokens;
            case 4 -> blueTokens;
            default -> new ImageView[0];
        };
    }

    private int getDiceImage(int diceResult) {
        return switch (diceResult) {
            case 1 -> R.drawable.dice_one;
            case 2 -> R.drawable.dice_two;
            case 3 -> R.drawable.dice_three;
            case 4 -> R.drawable.dice_four;
            case 5 -> R.drawable.dice_five;
            case 6 -> R.drawable.dice_six;
            default -> R.drawable.dice_one;
        };
    }

    public void rollDice(ImageButton diceButton, int playerId) {
        diceButton.setEnabled(false);
        simulateDiceRoll(diceButton);

        Animation animation = AnimationUtils.loadAnimation(diceButton.getContext(), R.anim.dice_roll);
        diceButton.startAnimation(animation);

        new Handler().postDelayed(() -> {
            int diceResult = lcg();
            diceButton.setImageResource(getDiceImage(diceResult));

            ImageView[] tokens = getCurrentTokens(currentPlayerTurn);
            boolean tokenMovable;
            tokenMovable = enableTokensForMovement(tokens, diceResult, playerId);

            if (tokenMovable) {
                enableTokenClick(tokens, diceResult, diceButton, playerId);
            } else {
                new Handler().postDelayed(() -> {
                    switchTurns();
                    diceButton.setEnabled(true);
                }, 500);
            }

        }, 300);
    }

    private boolean enableTokensForMovement(ImageView[] tokens, int diceResult, int playerId) {
        boolean anyTokenMovable = false;

        for (ImageView token : tokens) {
            if (diceResult == 6 || (!isTokenInHomeArea(token, playerId) && diceResult >= 1 && diceResult <= 5)) {
                token.setEnabled(true);
                anyTokenMovable = true;
                animateTokenBloom(token, () -> {

                });
            } else {
                token.setEnabled(false);
            }
        }

        return anyTokenMovable;
    }


    private void enableTokenClick(ImageView[] tokens, int diceResult, ImageButton diceButton, int playerId) {
        for (ImageView token : tokens) {
            if (token != null && token.isEnabled()) {
                token.setOnClickListener(v -> {
                    diceButton.setEnabled(true);
                    disableTokens(tokens);

                    if (diceResult==6) {
                        if(isTokenInHomeArea(token,playerId)) {
                            moveTokenToStart(token, playerId);
                            diceButton.setEnabled(true);
                            token.setEnabled(true);
                        }
                        else{
                            moveTokenBasedOnDice(token,diceResult,currentPlayerTurn);
                            diceButton.setEnabled(true);
                            token.setEnabled(true);
                        }
                        currentPlayerTurn = currentPlayerTurn - 1;
//                        switchTurns();
                    } else if(!isTokenInHomeArea(token,playerId)){
                            moveTokenBasedOnDice(token, diceResult, currentPlayerTurn);
                            diceButton.setEnabled(true);
                    }
                    switchTurns();
                    diceButton.setEnabled(true);
                });
            }
        }
    }

    private void disableTokens(ImageView[] tokens) {
        for (ImageView token : tokens) {
            if (token != null && !token.isPressed()) {
                token.setEnabled(false);
            }
        }
    }


//    private void moveTokenToStart(ImageView token, int currentPlayerTurn) {
////        for(ImageView token:tokens) {
////            enableTokenClick(tokens,diceResult,diceButton);
//            int startIndex = switch (currentPlayerTurn) {
//                case 1 -> redStartIndex;   // Red player
//                case 2 -> blueStartIndex;  // Blue player
//                case 3 -> greenStartIndex; // Green
//                case 4 -> yellowStartIndex; // Yellow
//                default -> throw new IllegalStateException("Invalid player turn: " + currentPlayerTurn);
//            };
//            int[] startPosition = pathCoordinates[startIndex];
//
//            moveTokenOnBoard(token.getId(), startPosition[0], startPosition[1]);
//
//            updateCurrentPosition(currentPlayerTurn, startIndex);
////        }
//    }
//
//    private void updateCurrentPosition(int currentPlayerTurn, int startIndex) {
//        switch (currentPlayerTurn) {
//            case 1 -> currentRedPosition = startIndex;
//            case 2 -> currentBluePosition = startIndex;
//            case 3 -> currentGreenPosition = startIndex;
//            case 4 -> currentYellowPosition = startIndex;
//        }
//    }
//
//    private boolean isTokenInHomeArea(ImageView token, int currentPlayerTurn) {
//        int[] tokenLocation = new int[2];
//        token.getLocationOnScreen(tokenLocation);
//
//        int[][] homeArea = getHomeAreaForPlayer(currentPlayerTurn);
//        return isInHomeArea(tokenLocation, homeArea);
//    }
//
//    private int[][] getHomeAreaForPlayer(int currentPlayerTurn) {
//        return switch (currentPlayerTurn) {
//            case 1 -> redHomeArea;
//            case 2 -> blueHomeArea;
//            case 3 -> greenHomeArea;
//            case 4 -> yellowHomeArea;
//            default -> new int[0][0];
//        };
//    }
//
//    private boolean isInHomeArea(int[] tokenLocation, int[][] homeArea) {
//        for (int[] coordinates : homeArea) {
//            if (tokenLocation[0] == coordinates[0] && tokenLocation[1] == coordinates[1]) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    private void moveTokenBasedOnDice(ImageView token, int diceResult, int currentPlayerTurn) {
//        boolean anyTokenMoved = false;
////        for(ImageView token:tokens) {
////            enableTokenClick(tokens,diceResult,diceButton,);
//            if (!isTokenInHomeArea(token, currentPlayerTurn)) {
//                int currentPosition = getCurrentPathCoordinate(token);
//                if (currentPosition == -1) {
//                    Log.d("TokenMovement", "Token is not on the path: " + token.getId());
//                    return;
//                }
//                int newPosition = currentPosition + diceResult;
//
//                if (newPosition < pathCoordinates.length) {
//                    int newMarginStart = pathCoordinates[newPosition][0];
//                    int newMarginTop = pathCoordinates[newPosition][1];
//
//                    moveTokenOnBoard(token.getId(), newMarginStart, newMarginTop);
//                    anyTokenMoved=true;
//                } else {
//                    Log.d("TokenMovement", "New position out of bounds: " + newPosition);
//                }
//            } else {
//                Log.d("TokenMovement", "Token is still in home area: " + token.getId());
//            }
////        }
//        if (!anyTokenMoved) {
//            switchTurns();
//        }
//    }
//
//    private int getCurrentPathCoordinate(ImageView token) {
//        int[] tokenLocation = new int[2];
//        token.getLocationOnScreen(tokenLocation);
//
//        for (int i = 0; i < pathCoordinates.length; i++) {
//            if (isTokenAtPathCoordinate(tokenLocation, pathCoordinates[i])) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    private boolean isTokenAtPathCoordinate(int[] tokenLocation, int[] pathCoordinate) {
//        int threshold = 10;
//
//        return (Math.abs(tokenLocation[0] - pathCoordinate[0]) <= threshold &&
//                Math.abs(tokenLocation[1] - pathCoordinate[1]) <= threshold);
//    }
//
//    private void switchTurns() {
//        diceRed.setVisibility(View.GONE);
//        diceGreen.setVisibility(View.GONE);
//        diceYellow.setVisibility(View.GONE);
//        diceBlue.setVisibility(View.GONE);
//
//        currentPlayerTurn= (currentPlayerTurn % 4) + 1;
//
//        updateDiceVisibility(currentPlayerTurn);
//        resetDiceResult();
//    }
//
//    private void resetDiceResult() {
//        diceRed.setImageResource(R.drawable.dice_one);
//        diceGreen.setImageResource(R.drawable.dice_one);
//        diceYellow.setImageResource(R.drawable.dice_one);
//        diceBlue.setImageResource(R.drawable.dice_one);
//    }
//
//    public void moveTokenOnBoard (int tokenId, int newMarginStart, int newMarginTop){
//
//        int newMarginStartPx = convertDpToPixels(newMarginStart);
//        int newMarginTopPx = convertDpToPixels(newMarginTop);
//
//        constraintSet.clear(tokenId, ConstraintSet.START);
//        constraintSet.clear(tokenId, ConstraintSet.TOP);
//
//        constraintSet.connect(tokenId, ConstraintSet.START, R.id.ludo_board, ConstraintSet.START, newMarginStartPx);
//        constraintSet.connect(tokenId, ConstraintSet.TOP, R.id.ludo_board, ConstraintSet.TOP, newMarginTopPx);
//
//        constraintSet.applyTo(constraintLayout);
//    }
//
//    private int convertDpToPixels ( int dp){
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
//    }
//}

    private void moveTokenToStart(ImageView token, int playerId) {
        int startIndex;

        if (playerId == PLAYER_RED) {
            startIndex = redStartIndex;
            previousRedPosition = currentRedPosition;
            currentRedPosition = startIndex;
        } else if (playerId == PLAYER_GREEN) {
            startIndex = greenStartIndex;
            previousGreenPosition = currentGreenPosition;
            currentGreenPosition = startIndex; // Set current position

        } else if (playerId == PLAYER_YELLOW) {
            startIndex = yellowStartIndex;
            previousYellowPosition = currentYellowPosition;
            currentYellowPosition=startIndex;
        } else if (playerId == PLAYER_BLUE) {
            startIndex = blueStartIndex;
            previousBluePosition = startIndex;
            currentBluePosition= startIndex;
        } else {
            throw new IllegalStateException("Invalid player ID: " + playerId);
        }

        int[] startPosition = getPathCoordinatesForPlayer(playerId)[startIndex];

        moveTokenOnBoard(token, startPosition[0], startPosition[1]);

        updateCurrentPosition(playerId, startIndex);
    }

    private void updateCurrentPosition(int playerId, int newPosition) {
        switch (playerId) {
            case PLAYER_RED -> currentRedPosition = newPosition;
            case PLAYER_GREEN -> currentGreenPosition = newPosition;
            case PLAYER_YELLOW -> currentYellowPosition = newPosition;
            case PLAYER_BLUE -> currentBluePosition = newPosition;
            default -> throw new IllegalStateException("Invalid player ID: " + playerId);
        }
    }

    private boolean isTokenInHomeArea(ImageView token, int playerId) {
        int[] tokenLocation = new int[2];
        token.getLocationOnScreen(tokenLocation);

        int[][] homeArea = getHomeAreaForPlayer(playerId);
        return isInHomeArea(tokenLocation, homeArea);
    }

    private int[][] getHomeAreaForPlayer(int playerId) {
        return switch (playerId) {
            case PLAYER_RED -> redHomeArea;
            case PLAYER_GREEN -> greenHomeArea;
            case PLAYER_YELLOW -> yellowHomeArea;
            case PLAYER_BLUE -> blueHomeArea;
            default -> new int[0][0];
        };
    }

    private boolean isInHomeArea(int[] tokenLocation, int[][] homeArea) {
        for (int[] coordinates : homeArea) {
            if (tokenLocation[0] == coordinates[0] && tokenLocation[1] == coordinates[1]) {
                return false;
            }
        }
        return true;
    }


    private void moveTokenBasedOnDice(ImageView token, int diceResult, int currentPlayerTurn) {
        boolean anyTokenMoved = false;

        int currentPosition = getCurrentPathCoordinate(token, currentPlayerTurn);

        if (!isTokenInHomeArea(token, currentPlayerTurn) && currentPosition != -1) {
            int newPosition = currentPosition + diceResult;

            int[][] pathCoordinates = getPathCoordinatesForPlayer(currentPlayerTurn);
            if (newPosition < pathCoordinates.length) {
                moveTokenOnBoard(token, pathCoordinates[newPosition][0], pathCoordinates[newPosition][1]);
                anyTokenMoved = true;
                updateCurrentPosition(currentPlayerTurn, newPosition);
            } else {
                Log.d("TokenMovement", "New position out of bounds: " + newPosition);
            }
        } else {
            Log.d("TokenMovement", "Token is still in home area or invalid current position.");
        }

        // Log if no token was moved
        if (!anyTokenMoved) {
            Log.d("TokenMovement", "No token was moved for player " + currentPlayerTurn);
        }

        // Switch turns if a token was moved
        if (anyTokenMoved) {
            switchTurns();
        }
    }

//    private int getStartPositionForPlayer(int playerId) {
//        switch (playerId) {
//            case PLAYER_RED:
//                return redStartIndex;
//            case PLAYER_GREEN:
//                return greenStartIndex;
//            case PLAYER_YELLOW:
//                return yellowStartIndex;
//            case PLAYER_BLUE:
//                return blueStartIndex;
//            default:
//                throw new IllegalArgumentException("Invalid player ID: " + playerId);
//        }
//    }


    private int getCurrentPathCoordinate(ImageView token, int currentPlayerTurn) {
        int[] tokenLocation = new int[2];
        token.getLocationOnScreen(tokenLocation);

        int[][] pathCoordinates = getPathCoordinatesForPlayer(currentPlayerTurn);

        for (int i = 0; i < pathCoordinates.length; i++) {
            if (isTokenAtPathCoordinate(tokenLocation, pathCoordinates[i])) {
                return i;
            }
        }
        return -1;
    }

    private boolean isTokenAtPathCoordinate(int[] tokenLocation, int[] pathCoordinate) {
        int threshold = 5;

        return (Math.abs(tokenLocation[0] - pathCoordinate[0]) <= threshold &&
                Math.abs(tokenLocation[1] - pathCoordinate[1]) <= threshold);
    }

    private int[][] getPathCoordinatesForPlayer(int playerId) {
        return switch (playerId) {
            case PLAYER_RED -> redPathCoordinates;
            case PLAYER_GREEN -> greenPathCoordinates;
            case PLAYER_YELLOW -> yellowPathCoordinates;
            case PLAYER_BLUE -> bluePathCoordinates;
            default -> throw new IllegalArgumentException("Invalid player ID: " + playerId);
        };
    }

    private void switchTurns() {
        diceRed.setVisibility(View.GONE);
        diceGreen.setVisibility(View.GONE);
        diceYellow.setVisibility(View.GONE);
        diceBlue.setVisibility(View.GONE);

        currentPlayerTurn = (currentPlayerTurn % 4) + 1;

        updateDiceVisibility(currentPlayerTurn);
        resetDiceResult();
    }

    private void resetDiceResult() {
        diceRed.setImageResource(R.drawable.dice_one);
        diceGreen.setImageResource(R.drawable.dice_one);
        diceYellow.setImageResource(R.drawable.dice_one);
        diceBlue.setImageResource(R.drawable.dice_one);
    }

    public void moveTokenOnBoard(ImageView token, int newMarginStart, int newMarginTop) {
        int newMarginStartPx = convertDpToPixels(newMarginStart);
        int newMarginTopPx = convertDpToPixels(newMarginTop);

        constraintSet.clear(token.getId(), ConstraintSet.START);
        constraintSet.clear(token.getId(), ConstraintSet.TOP);

        constraintSet.connect(token.getId(), ConstraintSet.START, R.id.ludo_board, ConstraintSet.START, newMarginStartPx);
        constraintSet.connect(token.getId(), ConstraintSet.TOP, R.id.ludo_board, ConstraintSet.TOP, newMarginTopPx);

        constraintSet.applyTo(constraintLayout);
    }

    private int convertDpToPixels(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}