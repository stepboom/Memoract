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
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class MemActivity extends AppCompatActivity implements View.OnClickListener {

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
    private TextView scoreTextView;
    private TextView timeTextView;
    private int clickCount = 0;
    private int clicked[] = new int[15];
    private int prev = -1;
    private int right = 0;
    private int ans[] = new int[15];
    private String name[] = new String[15];//{"a1","a1","a2","a2","a3","a3","a4","a4","a5","a5","a6","a6","a7","a7","a"};
    private ImageView img[] = new ImageView[15];
    private MediaPlayer mp;
    private MediaPlayer mp2;
    private MediaPlayer mp3;
    private boolean finish = false;
    private int position[] = new int[15];
    private int score;
    private int time;
    private Timer t2;
    private int mute;
    private int exit;
    private int delayTime;
    private Timer count;
    private String nameArray[] = new String[5];
    private int scoreArray[] = new int[5];
    private String nameHs;

    /*
     BELOW ARE METHODS TO INIT INSTANCES
     */

    void initInstances(){
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
        scoreTextView = (TextView)findViewById(cucpr.memoract.R.id.scoreTextView);
        timeTextView = (TextView)findViewById(cucpr.memoract.R.id.timeTextView);
        mp = MediaPlayer.create(MemActivity.this, cucpr.memoract.R.raw.b);
        mp2 = MediaPlayer.create(MemActivity.this, cucpr.memoract.R.raw.c);
        mp3 = MediaPlayer.create(MemActivity.this, cucpr.memoract.R.raw.a);
        finish = false;
        generatePos();
        generateAns();
        generateName();
        score = 0;
        exit = 0;
        delayTime = 8;
        for(int i = 0;i <15 ;i++) {
            clicked[i] = 1;
        }
        time = 60;
        score = 0;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cucpr.memoract.R.layout.activity_mem);
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

        readFromInternalFile("z.txt");

        //initialize Tiles with listener and clickable
        for(int i= 0 ;i < 15;i++){
            img[i].setImageResource(MemActivity.this.getResources().getIdentifier(name[i],"drawable",MemActivity.this.getPackageName()));
            img[i].setOnClickListener(MemActivity.this);
            img[i].setClickable(false);
        }

        mp3.setVolume(0.5f,0.5f);
        if(mute==0)
            mp3.start();

        //Delay for showing Tiles 8 second before reset
        Timer t = new Timer();
        t.schedule(new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0 ;i < 15 ;i++){
                            img[i].setClickable(true);
                        }
                        reset();
                    }
                });
            }
        },delayTime*1000);

        //update Timer Start at 8 second count EVERY 1 second
        runStartTimer();

    }

    /*
     BELOW ARE METHODS TO DETECT LISTENER
     */

    @Override
    public void onClick(View v) {

        for(int i = 0;i < 15;i++){
            if(v==img[i]){ //Check ImageView
                if(clicked[i]!=-1){ //If not right !
                    img[i].setImageResource(MemActivity.this.getResources().getIdentifier(name[i],"drawable",MemActivity.this.getPackageName())); //Set Resource
                    img[i].setClickable(false); //Can't click this Image again.
                    clicked[i] = 1; //Clicked
                    if(ans[i]==-1){
                        clickCount = 3; // BOMB !
                        break;
                    }
                    if(clickCount==1){
                        if(ans[prev]==i){
                            right = 1;
                            clicked[i] = -1;
                            clicked[prev] = -1;
                        }
                    }
                    else
                        prev = i;
                }
                break;
            }
        }

        if(clickCount<2)
            clickCount++;

        if(clickCount ==3){ //BOMB PART
            if(mute==0){
                mp2.seekTo(0);
                mp2.start();
            }
            for(int i = 0 ;i < 15 ;i++){
                img[i].setClickable(false);
            }
            updateScore(-10);
            Timer t = new Timer();
            t.schedule(new TimerTask(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            reset();
                            clickCount = 0;
                            for(int i = 0 ;i < 15 ;i++){
                                img[i].setClickable(true);
                            }
                        }
                    });
                }},1000);
        }


        if(clickCount==2) { //CHECK IF PAIR IS MATCH OR NOT
            if(right == 0){
                if(mute==0){
                    mp2.seekTo(0);
                    mp2.start();
                }
                for(int i = 0 ;i < 15 ;i++){
                    img[i].setClickable(false);
                }
                updateScore(-5);
                Timer t = new Timer();
                t.schedule(new TimerTask(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            reset();
                            clickCount = 0;
                            for(int i = 0 ;i < 15 ;i++){
                                img[i].setClickable(true);
                            }
                        }
                    });
                }},1000);
            }

            else if(right==1){ //To delay right tile put this in run()
                if(mute==0){
                    mp.seekTo(0);
                    mp.start();
                }
                right = 0;
                clickCount = 0;

                updateScore(20);

                isFinish();

            }
        }
    }

    /*
     BELOW ARE METHODS TO RESET TILES
     */

    public void reset(){
        for(int i = 0 ; i < 15 ;i++) {
            if (clicked[i] == 1) {
                clicked[i] = 0;
                img[i].setImageResource(MemActivity.this.getResources().getIdentifier("block", "drawable", MemActivity.this.getPackageName()));
            }
        }
    }

    /*
     BELOW ARE METHODS TO UPDATE TIME AND SCORE
     */

    void updateTime(){
        if(time > 0)
            time--;

        timeTextView.setText(time+"");

        if(time==0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MemActivity.this);
            final AlertDialog alert = dialog.create();
            alert.setTitle("Time Out");
            alert.setMessage("Your Score : " + score + "\nReturn to MENU");
            //alert.set
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

    void updateScore(int s){
        score += s;
        if(score < 0) score = 0;
        scoreTextView.setText("" + score);
    }

    /*
     BELOW ARE METHODS TO CHECK COMPLETION
     */

    void isFinish(){
        for(int i = 0 ; i < 15 ;i++) {
            if(ans[i]!=-1) {
                if (clicked[i] == -1) {
                    finish = true;
                } else {
                    finish = false;
                    break;
                }
            }
        }
        if(finish) { //EXIT THE GAME
            final Dialog dialog = new Dialog(MemActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            final int pos = checkScorePos(score);

            if(pos < 5){
                dialog.setContentView(cucpr.memoract.R.layout.activity_alert);
                TextView info = (TextView) dialog.findViewById(cucpr.memoract.R.id.hsTxt);

                String description = "You ranked " + (pos+1) +  " with " + score + " points in Memory";
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
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(MemActivity.this);
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

        writeToInternalFile("z.txt",getWriteHighScoreString());
        MainActivity.hsText = getHighScoreString();
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
     BELOW ARE METHODS TO CREATE TIME
     */

    //Timer for initialize
    void runStartTimer(){
        t2 = new Timer();
        t2.schedule(new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateTime();
                        if(time==0 || finish){
                            t2.cancel();
                            t2.purge();
                        }
                    }
                });
            }
        },delayTime*1000,1000);

        count = new Timer();
        count.schedule(new TimerTask() {
            @Override
            public void run() {
                if(delayTime>0)
                    delayTime--;
                System.out.println("TIME LEFT : " + delayTime);
                if(delayTime==0 || finish){
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
                        System.out.println("TIME LEFT : " + time);
                        if(time==0 || finish){
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
            AlertDialog.Builder alert = new AlertDialog.Builder(MemActivity.this);
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
     BELOW ARE METHODS OF ALGORITHM TO GENERATE TILES POSITION
     */

    void generateName(){
        for(int i = 0 ;i < 15;i++){
            String file = "a";
            if(position[i]==0){
                name[i] = file;
            }
            else
                name[i] = file + position[i];
        }
    }

    void generateAns(){
        for(int i = 0 ;i < 15;i++){
            int k1 = -1;
            int k2;
            for(int j = 0 ;j < 15;j++){
                if(position[j]==i){
                    if(i==0){
                        ans[j] = -1;
                        break;
                    }
                    if(k1==-1){
                        k1 = j;
                    }
                    else{
                        k2 = j;
                        ans[k1] = k2;
                        ans[k2] = k1;
                    }
                }
            }
        }
    }

    int[] bfs (int s,int adj[][]) {
        int ans[] = new int [15];
        for (int i=0 ; i<15 ; i++) {
            ans[i] = -1;
        }

        Queue<Integer> q = new LinkedList<>();

        q.add(s);
        ans[s-1] = 0;
        int u;
        while(!q.isEmpty())
        {
            u = q.element();
            q.remove();
            for(int i = 0 ; i < 15 ; i++)
            {
                if(adj[u-1][i] == 1)
                {
                    if(ans[i] == -1)
                    {
                        ans[i] = ans[u-1] + 1;
                        q.add(i+1);
                    }
                }
            }
        }
        return ans;
    }

    void generatePos(){
        int adj[][] =  {
                {0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                {1 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                {0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                {1 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                {0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                {0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 },
                {0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 , 0 },
                {0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 },
                {0 , 0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 },
                {0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 0 },
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 },
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 1 },
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 },
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 1 },
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 }};

        int pic [] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        int round = 1;
        while(true) {

            int start;
            while(true) {
                start = 1 + (int)(Math.random() * ((15 - 1) + 1));
                if(pic[start-1] == 0) break;
            }

            pic[start-1] = round;

            int ans [] = bfs(start,adj);

            int stop;
            int j = 0;
            int len = 3;
            while(true) {
                stop = 1 + (int)(Math.random() * ((15 - 1) + 1));
                if(stop != start && ans[stop-1] >= len && pic[stop-1] == 0) break;
                j++;
                if(j > 30) {
                    len--;
                    j = 0;
                }
            }

            pic[stop-1] = round;

            round++;
            if(round > 7) break;
        }
        position = pic;
    }
}
