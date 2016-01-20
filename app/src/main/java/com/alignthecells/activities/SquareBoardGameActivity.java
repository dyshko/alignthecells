package com.alignthecells.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import com.alignthecells.R;
import com.alignthecells.utils.BoardViewParams;
import com.alignthecells.utils.GamePreferences;
import com.alignthecells.utils.TimerHandler;
import com.alignthecells.views.BoardView;
import com.alignthecells.views.MyTextView;

import static java.lang.Math.min;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SquareBoardGameActivity extends MainActivity {

    public static SquareBoardGameActivity instance = null;

    public RelativeLayout contentView;

    public RelativeLayout gameArea;

    MyTextView hintView;
    public TimerHandler timerHandler;
    int screenWidth;
    int screenHeight;
    private BoardView boardView;
    private ImageButton shuffleButton;
    private ImageButton optionsButton;
//    private ImageButton gameTypeButton;
    private PopupMenu popup;

    private boolean startViewIsVisible = true;


    private RelativeLayout startTextView;

    private int boardSideLength;

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timerHandler.resume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        contentView = (RelativeLayout) findViewById(R.id.fullscreen_content);

        instance = this;

        createResetButton();

        createOptionsButton();

//        createGameTypeButton();

        initTimerHandler();

        hintView = (MyTextView) findViewById(R.id.hint);
        hintView.setVisibility(View.GONE);
        hintView.setEnabled(false);


        contentView.post(new Runnable() {
            @Override
            public void run() {
                screenWidth = contentView.getMeasuredWidth();
                screenHeight = contentView.getMeasuredHeight();
                loadGameArea();
            }
        });
    }

    private void loadGameArea() {
        gameArea = (RelativeLayout) findViewById(R.id.game_area);
        createBoard();
        createStartView();
    }

    private void initTimerHandler() {
        timerHandler = new TimerHandler();
    }

    private void createBoard() {
        boardSideLength = (int) (0.90 * min(screenWidth, screenHeight));
        BoardViewParams bvp = new BoardViewParams(8, boardSideLength, GamePreferences.boardSize);
        //Adjasting the parameters to be integral
        boardSideLength = bvp.sideLength;
        RelativeLayout.LayoutParams boardLayoutParams = new RelativeLayout.LayoutParams(boardSideLength, boardSideLength);
        boardView = new BoardView(getApplicationContext(), bvp);
        boardLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        gameArea.addView(boardView, boardLayoutParams);
        timerHandler.reset();
        boardView.disableTouch();
    }

    private void createResetButton() {
        shuffleButton = (ImageButton) findViewById(R.id.reset_button);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiftBoardRandom();
            }
        });
        shuffleButton.setEnabled(false);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Do you want to quit?");
        alertDialogBuilder
                .setCancelable(true)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (!startViewIsVisible) {
                            boardView.solvedAnimation();
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    instance.finish();
                                }
                            }, (long)(1.5f*GamePreferences.animationDuration));
                        }
                        else instance.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public void showGameDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Congratulations!");
        alertDialogBuilder
                .setMessage("Solved in " + timerHandler.getText() +
                        " and " + boardView.shifts + " moves!")
          //      "Best: "+GamePreferences.getBestTime(this) + " and " + GamePreferences.getBestMoves(this)+ " moves.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        shiftBoardRandom();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    private void shiftBoardRandom() {
        boardView.shiftBoardRandom();
        timerHandler.start();
    }

    public void startGame() {
        shuffleButton.setEnabled(true);
        boardView.firstShiftRandom();
        timerHandler.start();
    }

    private void createStartView() {
        startTextView = (RelativeLayout)getLayoutInflater().inflate(R.layout.start_layout, gameArea, false);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) startTextView.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        gameArea.addView(startTextView,params);//,params);

        startTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                deleteStartView();
                hintView.setVisibility(View.VISIBLE);
                hintView.setEnabled(true);
                hintView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                    }
                });
            }
        });
        startViewIsVisible = true;
        boardView.disableTouch();
    }

    private void deleteStartView() {
        if (startTextView != null)
            gameArea.removeView(startTextView);
        startViewIsVisible = false;
        boardView.enableTouch();
    }

    public void showPopupMenu()
    {
        popup.show();
    }

    public void createOptionsButton() {
        optionsButton = (ImageButton) findViewById(R.id.options_button);
        popup = new PopupMenu(this, optionsButton);
        popup.inflate(R.menu.game_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch (id) {
                    case R.id.menu_game_mode:
                        intent = new Intent(getApplicationContext(), GameTypePreferenceActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.menu_info:
                        intent = new Intent(getApplicationContext(), InformationActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.menu_settings:
                        intent = new Intent(getApplicationContext(), GamePreferenceActivity.class);
                        startActivity(intent);
                        break;

                    default:
                }
                return true;
            }
        });
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });
    }

    public void stopTimer() {
        timerHandler.stop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public boolean buttonTouchEnabled() {
        return shuffleButton.isEnabled();
    }

    public void disableButtonTouch() {
        shuffleButton.setEnabled(false);
    }

    public void enableButtonTouch() {
        shuffleButton.setEnabled(true);
    }
}

