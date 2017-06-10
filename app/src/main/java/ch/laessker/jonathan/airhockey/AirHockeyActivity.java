package ch.laessker.jonathan.airhockey;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

import ch.laessker.jonathan.airhockey.game.Game;
import ch.laessker.jonathan.airhockey.game.Player;
import ch.laessker.jonathan.airhockey.util.DBHelper;
import ch.laessker.jonathan.airhockey.util.SettingsValues;

public class AirHockeyActivity extends Activity {
    /**
     * Hold a reference to our GLSurfaceView
     */
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    private Game game;
    private Player p1;
    private Player p2;

    private Button pauseButtonTop;
    private Button pauseButtonBottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean startNew = getIntent().getExtras().getBoolean("startNew");


        if(startNew) {
            p1 = new Player(1, "pinco 1", 0);
            p2 = new Player(2, "pallino 2", 0);
            game = Game.getInstance();
            DBHelper helper = new DBHelper(getApplicationContext());
            SettingsValues values = helper.returnSavedValues();
            int difficulty = values.getDifficulty();
            game.setValues(1, difficulty, p1, p2);
            Toast.makeText(AirHockeyActivity.this, "Game started" +
                    "\nDifficulty: " + difficulty, Toast.LENGTH_SHORT).show();
        } else {
            game= Game.getInstance();
            DBHelper helper = new DBHelper(getApplicationContext());
            helper.getGame();

            Toast.makeText(AirHockeyActivity.this, "Game loaded", Toast.LENGTH_SHORT).show();
        }

        setContentView(R.layout.surface_view_layout);
        glSurfaceView = (GLSurfaceView)findViewById(R.id.surfaceviewclass);


        pauseButtonTop = (Button) findViewById(R.id.pauseButtonTop);
        pauseButtonTop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AirHockeyActivity.this,PauseMenu.class);
                //i.putExtra("GameClass", game);
                startActivityForResult(i, 0);
            }
        });

        pauseButtonBottom = (Button) findViewById(R.id.pauseButtonBottom);
        pauseButtonBottom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(AirHockeyActivity.this,PauseMenu.class);
                //i.putExtra("GameClass", game);
                startActivityForResult(i, 0);
            }
        });


        // Check if the system supports OpenGL ES 2.0.
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager
                .getDeviceConfigurationInfo();
        // Even though the latest emulator supports OpenGL ES 2.0,
        // it has a bug where it doesn't set the reqGlEsVersion so
        // the above check doesn't work. The below will detect if the
        // app is running on an emulator, and assume that it supports
        // OpenGL ES 2.0.
        final boolean supportsEs2 =
                configurationInfo.reqGlEsVersion >= 0x20000
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                        && (Build.FINGERPRINT.startsWith("generic")
                        || Build.FINGERPRINT.startsWith("unknown")
                        || Build.MODEL.contains("google_sdk")
                        || Build.MODEL.contains("Emulator")
                        || Build.MODEL.contains("Android SDK built for x86")));

        final AirHockeyRenderer airHockeyRenderer = new AirHockeyRenderer(AirHockeyActivity.this,game);

        if (supportsEs2) {
            // ...
            // Request an OpenGL ES 2.0 compatible context.
            glSurfaceView.setEGLContextClientVersion(2);

            // Assign our renderer.
            glSurfaceView.setRenderer(airHockeyRenderer);
            rendererSet = true;
        } else {
            /*
             * This is where you could create an OpenGL ES 1.x compatible
             * renderer if you wanted to support both ES 1 and ES 2. Since
             * we're not doing anything, the app will crash if the device
             * doesn't support OpenGL ES 2.0. If we publish on the market, we
             * should also add the following to AndroidManifest.xml:
             *
             * <uses-feature android:glEsVersion="0x00020000"
             * android:required="true" />
             *
             * This hides our app from those devices which don't support OpenGL
             * ES 2.0.
             */
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        glSurfaceView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    // Convert touch coordinates into normalized device
                    // coordinates, keeping in mind that Android's Y
                    // coordinates are inverted.
                    final float normalizedX =
                            (event.getX() / (float) v.getWidth()) * 2 - 1;
                    final float normalizedY =
                            -((event.getY() / (float) v.getHeight()) * 2 - 1);
                    int actionPeformed = event.getAction();


                    switch(actionPeformed) {
                        case MotionEvent.ACTION_DOWN:
                            glSurfaceView.queueEvent(new Runnable() {
                                @Override
                                public void run() {
                                    airHockeyRenderer.handleTouchPress(
                                            normalizedX, normalizedY);
                                }
                            });

                        case MotionEvent.ACTION_POINTER_DOWN:
                            glSurfaceView.queueEvent(new Runnable() {
                                @Override
                                public void run() {
                                    airHockeyRenderer.handleTouchPress(
                                            normalizedX, normalizedY);
                                }
                            });
                        case MotionEvent.ACTION_MOVE: {
                            glSurfaceView.queueEvent(new Runnable() {
                                @Override
                                public void run() {
                                    airHockeyRenderer.handleTouchDrag(
                                            normalizedX, normalizedY);
                                }
                            });
                        }

                    }
                    return true;
                } else {
                    return false;
                }
            }
        });

        //setContentView(glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (rendererSet) {
            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (rendererSet) {
            glSurfaceView.onResume();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, null);
                this.finish();
            }
        }
    }

}