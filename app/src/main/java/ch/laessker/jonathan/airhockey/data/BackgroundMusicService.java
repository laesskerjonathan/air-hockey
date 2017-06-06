package ch.laessker.jonathan.airhockey.data;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import ch.laessker.jonathan.airhockey.R;

public class BackgroundMusicService extends Service {

    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    public void onCreate()
    {
        mp = MediaPlayer.create(this, R.raw.urameshimusic);
        mp.start();
        mp.setLooping(false);
    }
    public void onDestroy(){
        mp.stop();
        mp.release();

    }
    public void onStop(){
        mp.stop();
        mp.release();
    }

    public void onPause(){

        mp.stop();
        mp.release();

    }

}