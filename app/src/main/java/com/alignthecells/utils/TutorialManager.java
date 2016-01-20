package com.alignthecells.utils;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

/**
 * Created by Sergey on 9/4/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class TutorialManager {

    static private boolean previousTutorialFinished = true;
    static private int currentTutorial = 0;
  //  static private ShowcaseView scView;

    static private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
    //        scView.hide();
            previousTutorialFinished = true;
        }
    };

    public static void startNextTutorial(final Activity instance, final View v)
    {
        if (!previousTutorialFinished)
        {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startNextTutorial(instance, v);
                }
            }, 1000);
        }
        if (GamePreferences.firstRun && previousTutorialFinished){
            switch (currentTutorial)
            {
                case 0: {
      /*              scView = new ShowcaseView.Builder(instance)
                            .setTarget(new ViewTarget(v))
                            .setContentTitle("Start the game")
                            .setContentText("Touch the text to start the game")
                            .setOnClickListener(listener)
                            .hideOnTouchOutside()
                            .build();*/
                    currentTutorial+=1;
                }break;
                case 1: {
       /*             scView = new ShowcaseView.Builder(instance)
                            .setTarget(new ViewTarget(v))
                            .setContentTitle("Swipe")
                            .setContentText("Swipe the board to move the tiles")
                            .setOnClickListener(listener)
                            .hideOnTouchOutside()
                            .build();
               */ }
                break;
                default:
            }
        }
    }
}
