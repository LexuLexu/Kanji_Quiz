package com.example.quiztest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * The type Help activity.
 */
public class HelpActivity extends AppCompatActivity {

    private int menuItemCheck;

    private ImageView arrow;
    private int arrowOrigY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        load_bottom_bar();

        cycle_menu_checked();
    }

    protected void onStart() {
        super.onStart();

        animate_arrow();
    }


    /**
     * Load bottom bar.
     */
    public void load_bottom_bar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(2).setChecked(false);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case (R.id.ic_profile):
                        Intent intent = new Intent(HelpActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case (R.id.ic_friends):
                        Intent intent2 = new Intent(HelpActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case (R.id.ic_leaderboard):
                        Intent intent3 = new Intent(HelpActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case (R.id.ic_settings):
                        Intent intent4 = new Intent(HelpActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;

            }

        });
    }

    /**
     * Go to questions.
     *
     * @param view the view
     */
    public void go_to_questions (View view) {
        Intent levelChoiceIntent = new Intent(HelpActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);
    }

    /**
     * Cycle menu checked.
     */
    public void cycle_menu_checked() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        Menu menu = bottomNavigationView.getMenu();

        Handler handler = new Handler();
        int delay = 1000; //milliseconds

        menuItemCheck = 0;

        handler.postDelayed(new Runnable(){
            public void run(){

                MenuItem menuItem = menu.getItem(menuItemCheck).setChecked(true);

                menuItemCheck++;

                if (menuItemCheck == 2) {
                    menuItemCheck++;
                }
                else if (menuItemCheck == 5) {
                    menuItemCheck = 0;
                }

                handler.postDelayed(this, delay);
            }
        }, delay);

    }

    /**
     * Animate arrow.
     */
    public void animate_arrow() {

        arrow = findViewById(R.id.downArrow);
        arrowOrigY = 0;

        ObjectAnimator downAnimation = ObjectAnimator.ofFloat(arrow, "translationY", 64f);
        ObjectAnimator upAnimation = ObjectAnimator.ofFloat(arrow, "translationY", 0);

        downAnimation.setDuration(1000);
        upAnimation.setDuration(1000);

        Handler handler = new Handler();
        int delay = 200; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){

                int[] origPoint = new int[2];
                arrow.getLocationOnScreen(origPoint);
                if (arrowOrigY == 0) {
                    arrowOrigY = origPoint[1];
                }

                int[] point = new int[2];
                arrow.getLocationOnScreen(point);
                int arrowCurrentY = point[1];

                System.out.println("current = " + arrowCurrentY);
                System.out.println("orig = " + arrowOrigY);

                if (arrowCurrentY == arrowOrigY) {
                    downAnimation.start();
                    System.out.println("going down");
                    arrow.invalidate();
                }

                else if (arrowCurrentY == (arrowOrigY + 64f)) {
                  upAnimation.start();
                  System.out.println("going up");
                  arrow.invalidate();
                }

                handler.postDelayed(this, delay);
            }
        }, delay);

    }
}
