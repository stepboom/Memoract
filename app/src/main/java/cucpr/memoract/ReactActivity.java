package cucpr.memoract;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class ReactActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgView1;
    private ImageView imgView2;
    private ImageView imgView3;
    private ImageView imgView4;
    private ImageView imgView5;
    private ImageView imgView6;
    private ImageView imgView7;
    private ImageView imgView8;
    private ImageView imgView9;
    private ImageView imgView10;
    private ImageView imgView11;
    private ImageView imgView12;
    private ImageView imgView13;
    private ImageView imgView14;
    private ImageView imgView15;
    private TextView timeTextView;
    private TextView scoreTextView;
    private ImageView img[] = new ImageView[15];
    private Timer t2;
    private Timer count;
    private boolean finish;
    private int exit;
    private MediaPlayer mp;
    private MediaPlayer mp2;
    private MediaPlayer mp3;
    private int delayTime;
    private int time;
    private int idleTime;
    private int score;
    private int pos[] = new int[15];
    private String nameArray[] = new String[5];
    private int scoreArray[] = new int[5];
    private int mute;
    private String nameHs;

    int adj[][] = { { 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0 } };

    /*
    METHODS TO INIT INSTANCES
     */
    void initInstances(){
        timeTextView = (TextView)findViewById(cucpr.memoract.R.id.timeTextView);
        scoreTextView = (TextView) findViewById(cucpr.memoract.R.id.scoreTextView);
        imgView1 = (ImageView)findViewById(cucpr.memoract.R.id.image1);
        imgView2 = (ImageView)findViewById(cucpr.memoract.R.id.image2);
        imgView3 = (ImageView)findViewById(cucpr.memoract.R.id.image3);
        imgView4 = (ImageView)findViewById(cucpr.memoract.R.id.image4);
        imgView5 = (ImageView)findViewById(cucpr.memoract.R.id.image5);
        imgView6 = (ImageView)findViewById(cucpr.memoract.R.id.image6);
        imgView7 = (ImageView)findViewById(cucpr.memoract.R.id.image7);
        imgView8 = (ImageView)findViewById(cucpr.memoract.R.id.image8);
        imgView9 = (ImageView)findViewById(cucpr.memoract.R.id.image9);
        imgView10 = (ImageView)findViewById(cucpr.memoract.R.id.image10);
        imgView11 = (ImageView)findViewById(cucpr.memoract.R.id.image11);
        imgView12 = (ImageView)findViewById(cucpr.memoract.R.id.image12);
        imgView13 = (ImageView)findViewById(cucpr.memoract.R.id.image13);
        imgView14 = (ImageView)findViewById(cucpr.memoract.R.id.image14);
        imgView15 = (ImageView)findViewById(cucpr.memoract.R.id.image15);

        finish = false;
        exit = 0;
        mp3 = MediaPlayer.create(ReactActivity.this, cucpr.memoract.R.raw.a);
        mp = MediaPlayer.create(ReactActivity.this, cucpr.memoract.R.raw.b);
        mp2 = MediaPlayer.create(ReactActivity.this, cucpr.memoract.R.raw.c);
        delayTime = 3;
        time = 30;
        idleTime = 6;
        score = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cucpr.memoract.R.layout.activity_react);
        initInstances();

        Intent intent = getIntent();
        mute = intent.getIntExtra("mute",0);

        img[0] = imgView1;
        img[1] = imgView2;
        img[2] = imgView3;
        img[3] = imgView4;
        img[4] = imgView5;
        img[5] = imgView6;
        img[6] = imgView7;
        img[7] = imgView8;
        img[8] = imgView9;
        img[9] = imgView10;
        img[10] = imgView11;
        img[11] = imgView12;
        img[12] = imgView13;
        img[13] = imgView14;
        img[14] = imgView15;

        readFromInternalFile("z2.txt");

        for(int i = 0 ;i < 15;i++){
            img[i].setImageResource(cucpr.memoract.R.drawable.t);
            img[i].setOnClickListener(ReactActivity.this);
            img[i].setClickable(false);
        }

        mp3.setVolume(0.5f,0.5f);
        if(mute==0)
            mp3.start();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int start = 1 + (int) (Math.random() * ((15 - 1) + 1));
                        System.out.println("START NO : " + start);
                        find5Pos(start);
                        updatePic();
                        for(int i = 0 ;i < 15;i++){
                            img[i].setClickable(true);
                        }
                    }
                });
            }
        },delayTime*1000);

        runStartTimer();
    }

    /*
    BELOW ARE METHODS TO CHECK LISTENER
     */


    @Override
    public void onClick(View v) {
        for(int i = 0 ;i < 15;i++){
            if(v==img[i]) {
                idleTime = 6;
                if (pos[i] == 1) {
                    pos[i] = 0;
                    setPos(bfs(i + 1, adj), i + 1);
                    updateScore(1);
                    updatePic();
                    if(mute==0){
                        mp.seekTo(0);
                        mp.start();
                    }
                } else {
                    updateScore(-1);
                    resetAll();
                    find5Pos(i+1);
                    if(mute==0){
                        mp2.seekTo(0);
                        mp2.start();
                    }

                    for(int k = 0 ;k < 15 ;k++){
                        img[k].setClickable(false);
                    }
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updatePic();
                                    for(int k = 0 ;k < 15 ;k++){
                                        img[k].setClickable(true);
                                    }
                                }
                            });

                        }
                    },1000);

                }
                break;
            }

        }

    }

    /*
    BELOW IS METHOD TO UPDATE & FINISH THE GAME
     */

    void updatePic(){
        for(int i = 0 ;i < 15;i++){
            if(pos[i]==0){
                img[i].setImageResource(cucpr.memoract.R.drawable.t);
            }
            else img[i].setImageResource(cucpr.memoract.R.drawable.block2);
        }
    }

    void finishProcess(){
        final Dialog dialog = new Dialog(ReactActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final int pos = checkScorePos(score);

        if(pos < 5){
            dialog.setContentView(cucpr.memoract.R.layout.activity_alert);
            TextView info = (TextView) dialog.findViewById(cucpr.memoract.R.id.hsTxt);

            String description = "You ranked " + (pos+1) +  " with " + score + " points in Reaction";
            info.setText(description);
            Button submit = (Button) dialog.findViewById(cucpr.memoract.R.id.hsBtn);
            final EditText name = (EditText) dialog.findViewById(cucpr.memoract.R.id.hsEdt);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nameHs = name.getText().toString();
                    if(nameHs.equals("")){
                        nameHs = "Anonymous";
                    }
                    updateScore(nameHs,score,pos);
                    mp3.stop();
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        }
        else {
            AlertDialog.Builder dialog2 = new AlertDialog.Builder(ReactActivity.this);
            final AlertDialog alert = dialog2.create();
            alert.setTitle("Congratulations");
            alert.setMessage("Your Score : " + score + "\nReturn to MENU");
            alert.setCancelable(false);
            alert.show();
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mp3.stop();
                            alert.dismiss();
                            finish();
                        }
                    });
                }
            }, 3000);
        }
    }

     /*
     BELOW ARE METHODS TO UPDATE TIME AND SCORE AND IDLE TIME
     */

    void updateTime(){
        if(time > 0)
            time--;

        timeTextView.setText(time+"");

        if(time==0) {
            finishProcess();
        }
    }

    void updateScore(int s){
        score += s;
        if(score < 0) score = 0;
        scoreTextView.setText("" + score);
    }

    void updateIdleTime(){
        if(idleTime>0)
            idleTime--;
        if(idleTime==0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ReactActivity.this);
            final AlertDialog alert = dialog.create();
            alert.setTitle("Idle Time Out");
            alert.setMessage("You are too lazy to play T-T");
            alert.setCancelable(false);
            alert.show();

            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mp3.stop();
                            alert.dismiss();
                            finish();
                        }
                    });
                }
            }, 3000);
        }
    }

    /*
     BELOW ARE METHODS ARE PERTAIN TO HIGH-SCORE
     */

    //Check position
    public int checkScorePos(int s) {
        int pos = -1;
        int start = 0;
        int end = 4;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (s == scoreArray[mid]) {
                pos = mid;
                break;
            } else if (s > scoreArray[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        if(pos == -1)
            pos = start;
        return pos;
    }

    //Update new score
    public void updateScore(String n, int s,int pos) {

        if (pos < 5) {
            for (int i = 4; i > pos; i--) {
                scoreArray[i] = scoreArray[i - 1];
                nameArray[i] = nameArray[i - 1];
            }
            scoreArray[pos] = s;
            nameArray[pos] = n;
        }

        writeToInternalFile("z2.txt",getWriteHighScoreString());
        MainActivity.hsText2 = getHighScoreString();
    }

    //Read File to get High-score
    public void readFromInternalFile(String fileName) {
        FileInputStream fis = null;
        BufferedReader in = null;
        try {
            fis = openFileInput(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            in = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String str;

        int i = 0;
        try {
            while ((str = in.readLine()) != null) {
                int s = Integer.parseInt(str.substring(0, str.indexOf(" ")));
                String n = (str.substring(str.indexOf(" ") + 1));
                nameArray[i] = n;
                scoreArray[i] = s;
                i++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Write File to Update High-score
    public void writeToInternalFile(String fileName, String text) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fileName, this.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(text.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Make String For Write
    public String getWriteHighScoreString(){
        String join ="";
        for(int i = 0 ;i < 5;i++){
            if(i==4){
                join += scoreArray[i] + " " + nameArray[i];
                break;
            }
            join += scoreArray[i] + " " + nameArray[i] + "\n";
        }
        return join;
    }

    //Make String For Ranking Text
    public String getHighScoreString(){
        String join ="";
        for(int i = 0 ;i < 5;i++){
            if(i==4){
                join += nameArray[i] + " "+ scoreArray[i];
                break;
            }
            join += nameArray[i] + " "+ scoreArray[i] + "\n";
        }
        return join;
    }

     /*
     BELOW ARE TIMERS
     */

    //Timer for initialize
    void runStartTimer() {
        t2 = new Timer();
        t2.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateTime();
                        updateIdleTime();
                        if (time == 0 || idleTime ==0 || finish) {
                            t2.cancel();
                            t2.purge();
                        }
                    }
                });
            }
        }, delayTime * 1000, 1000);

        count = new Timer();
        count.schedule(new TimerTask() {
            @Override
            public void run() {
                if (delayTime > 0)
                    delayTime--;
                System.out.println("TIME LEFT : " + delayTime);
                if (delayTime == 0 || finish) {
                    count.purge();
                    count.cancel();
                }

            }
        }, 0, 1000);
    }

    //Timer for RESUME app
    void runTimer(){
        t2 = new Timer();
        t2.schedule(new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateTime();
                        updateIdleTime();
                        System.out.println("TIME LEFT : " + time);
                        if(time==0 || idleTime == 0 || finish){
                            t2.cancel();
                            t2.purge();
                        }
                    }
                });
            }
        },0,1000);
    }

    //Pauser
    void pauseTimer(){
        count.cancel();
        count.purge();
        t2.cancel();
        t2.purge();
    }

    /*
     BELOW ARE METHODS TO DETECT ACTION
     */

    @Override
    protected void onUserLeaveHint() {
        exit = 1;
        mp3.pause();
        pauseTimer();
        System.out.println("EXIT HOME");
        super.onUserLeaveHint();

    }

    @Override
    protected void onResume() {
        System.out.println("RESUME" + exit);
        if(exit == 1){
            exit = 0;
            if(mute==0)
                mp3.start();
            if(delayTime>0){
                runStartTimer();
            }
            else
                runTimer();
        }
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);

        if(!finish){
            AlertDialog.Builder alert = new AlertDialog.Builder(ReactActivity.this);
            alert.setTitle("Exit The Game");
            alert.setCancelable(true);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish = true;
                    mp3.stop();
                    finish();
                }
            });
            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();
        }
    }

    /*
    BELOW ARE METHODS FOR ALGORITHM
     */

    int[] bfs(int s, int adj[][]) {
        int ans[] = new int[15];
        for (int i = 0; i < 15; i++) {
            ans[i] = -1;
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        ans[s - 1] = 0;
        int u;
        while (!q.isEmpty()) {
            u = q.element();
            q.remove();
            for (int i = 0; i < 15; i++) {
                if (adj[u - 1][i] == 1) {
                    if (ans[i] == -1) {
                        ans[i] = ans[u - 1] + 1;
                        q.add(i + 1);
                    }
                }
            }
        }
        return ans;
    }

    int setPos(int ans[], int start) // find end 1 step...
    {
        int stop;
        int j = 0;
        int len = 3;
        while (true) {
            stop = 1 + (int) (Math.random() * ((15 - 1) + 1));
            if (stop != start && ans[stop - 1] >= len && pos[stop - 1] == 0)
                break;
            j++;
            if (j > 30) {
                len--;
                j = 0;
            }
        }
        pos[stop - 1] = 1;
        return stop;
    }

    void find5Pos(int start) // find 4 step by 1 start...
    {
        int stop;
        int ans[] = bfs(start, adj);
        pos[start - 1] = 1;
        for (int i = 0; i < 4; i++) {
            stop = setPos(ans, start);
            start = stop;
        }
    }

    void resetAll() // reset pic...
    {
        for (int i = 0; i < 15; i++) {
            pos[i] = 0;
        }
    }

}
