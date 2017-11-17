package cucpr.memoract;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imgBtn1;
    private ImageButton imgBtn2;
    private Button htpBtn;
    private Button rBtn;
    private Button mBtn;
    private Button sBtn;
    private Button iBtn;
    private MediaPlayer mp;
    private int mute;
    private int exit;
    private BufferedReader in;
    public static String hsText;
    public static String hsText2;

    /*
     BELOW ARE METHODS TO INIT INSTANCES
     */

    void initInstance(){
        imgBtn1 = (ImageButton)findViewById(cucpr.memoract.R.id.imgBtn1);
        imgBtn2 = (ImageButton)findViewById(cucpr.memoract.R.id.imgBtn2);
        htpBtn = (Button)findViewById(cucpr.memoract.R.id.htpBtn);
        rBtn = (Button)findViewById(cucpr.memoract.R.id.rBtn);
        mBtn = (Button)findViewById(cucpr.memoract.R.id.mBtn);
        sBtn = (Button)findViewById(cucpr.memoract.R.id.sBtn);
        iBtn = (Button)findViewById(cucpr.memoract.R.id.iBtn);
        mp =  MediaPlayer.create(MainActivity.this, cucpr.memoract.R.raw.d);
        mute = 0;
        exit = 0;
        //writeToInternalFile("z.txt","100 Somchai\n80 Atiwong\n75 Nuttawut\n60 Veera\n55 Visanu");
        //writeToInternalFile("z2.txt","30 Somchai\n25 Atiwong\n20 Nuttawut\n15 Veera\n10 Visanu");
        readHighScoreFromFile("z.txt");
        readHighScoreFromFile("z2.txt");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cucpr.memoract.R.layout.activity_main);
        initInstance();

        imgBtn1.setOnClickListener(MainActivity.this);
        imgBtn2.setOnClickListener(MainActivity.this);
        htpBtn.setOnClickListener(MainActivity.this);
        rBtn.setOnClickListener(MainActivity.this);
        mBtn.setOnClickListener(MainActivity.this);
        sBtn.setOnClickListener(MainActivity.this);
        iBtn.setOnClickListener(MainActivity.this);
        mp.setLooping(true);
        mp.start();
    }

    /*
     BELOW ARE METHODS TO DETECT LISTENER
     */

    @Override
    public void onClick(View v) {
        if(v==imgBtn1) {
            Intent intent = new Intent(MainActivity.this, MemActivity.class);
            intent.putExtra("mute",mute);
            startActivityForResult(intent,0);
            mp.pause();
        }
        else if(v==imgBtn2) {
            Intent intent = new Intent(MainActivity.this, ReactActivity.class);
            String b = "REACT GAME";
            intent.putExtra("mute", mute);
            startActivityForResult(intent,0);
            mp.pause();
        }
        else if(v==htpBtn){
            showMessage("HOW TO PLAY","MEM : MEMORIZE 7 PAIRS PLUS 1 BOMB\n" +
                    "YOU HAVE 8 SECONDS TO REMEMBER THEM ALL\n" +
                    "RIGHT PAIR WILL GET 20 PTS\nWRONG PAIR WILL DEDUCE 5 PTS\n" +
                    "CLICK BOMB WILL DEDUCE 10 PTS\n\n" +
                    "REACT : CLICK EVERY IMAGE ON SCREEN\n" +
                    "YOU HAVE 3 SECONDS TO PREPARE\n" +
                    "EACH RIGHT CLICK GET 1 PTS\n" +
                    "CLICK AREA OUTSIDE\n" +
                    "WILL DEDUCE 1 PTS & IMAGE RESET\n");
        }
        else if(v==rBtn){
            showHighScore();
        }
        else if(v==mBtn){
            if(mp.isPlaying()) {
                mp.pause();
                mBtn.setText("unmute");
                mute = 1;
            }
            else {
                mp.start();
                mBtn.setText("mute");
                mute = 0;
            }
        }
        else if(v==sBtn){
            showMessage("SETTING","Update Soon");
        }
        else if(v==iBtn){
            showMessage("INFO","ANDROID : STEPBOOM\n ALGORITHM : TUEYTOMA");
        }
    }

    /*
     BELOW ARE METHODS TO SHOW DIALOG
     */

    public void showMessage(String title,String Message){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(cucpr.memoract.R.layout.activity_dialog);
        dialog.setCancelable(true);

        TextView text2 = (TextView) dialog.findViewById(cucpr.memoract.R.id.TextView02);
        text2.setText(title);

        TextView text = (TextView) dialog.findViewById(cucpr.memoract.R.id.TextView01);
        text.setText(Message);

        if(title.equals("HOW TO PLAY")){
            text.setTextSize(10);
        }

        /*ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
        img.setImageResource(R.drawable.a);*/

        Button button = (Button) dialog.findViewById(cucpr.memoract.R.id.Button01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showHighScore(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(cucpr.memoract.R.layout.activity_hs_dialog);
        dialog.setCancelable(true);

        TextView title = (TextView) dialog.findViewById(cucpr.memoract.R.id.TextView02);
        title.setText("RANKING");

        final TextView ranking = (TextView) dialog.findViewById(cucpr.memoract.R.id.TextView01);
        ranking.setText(hsText);

        final TextView ranking2 = (TextView) dialog.findViewById(cucpr.memoract.R.id.TextView03);
        ranking2.setText(hsText2);

        /*ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
        img.setImageResource(R.drawable.a);*/

        Button ok = (Button) dialog.findViewById(cucpr.memoract.R.id.Button01);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button reset = (Button) dialog.findViewById(cucpr.memoract.R.id.Button02);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetHighScore("z.txt");
                resetHighScore("z2.txt");
                ranking.setText(hsText);
                ranking2.setText(hsText2);
            }
        });

        dialog.show();
    }

    /*
     BELOW ARE METHODS TO GET HIGH-SCORE FILE
     */

    public boolean canReadFromInternalFile(String fileName) {
        File file = getFileStreamPath(fileName);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public void readHighScoreFromFile(String fileName) {
        if(canReadFromInternalFile(fileName)){
            FileInputStream fis = null;
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
        }
        else{
            try {
                if(fileName.equals("z.txt"))
                    in = new BufferedReader(new InputStreamReader(getResources().openRawResource(cucpr.memoract.R.raw.z), "UTF-8"));
                else
                    in = new BufferedReader(new InputStreamReader(getResources().openRawResource(cucpr.memoract.R.raw.z2), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String str;
        String text="";
        String writeText="";

        int i = 0;
        try {
            while ((str = in.readLine()) != null) {
                int s = Integer.parseInt(str.substring(0, str.indexOf(" ")));
                String n = (str.substring(str.indexOf(" ") + 1));
                if(i==4){
                    text += n + " " + s;
                    writeText += s + " " + n;
                    break;
                }
                text += n + " " +s + "\n";
                writeText += s + " " + n + "\n";
                i++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(fileName.equals("z.txt"))
            hsText = text;
        else
            hsText2 = text;
        if(!canReadFromInternalFile(fileName)){
            writeToInternalFile(fileName,writeText);
        }
    }

    public void resetHighScore(String fileName){
        try {
            if(fileName.equals("z.txt"))
                in = new BufferedReader(new InputStreamReader(getResources().openRawResource(cucpr.memoract.R.raw.z), "UTF-8"));
            else
                in = new BufferedReader(new InputStreamReader(getResources().openRawResource(cucpr.memoract.R.raw.z2), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String str;
        String text="";
        String writeText="";

        int i = 0;
        try {
            while ((str = in.readLine()) != null) {
                int s = Integer.parseInt(str.substring(0, str.indexOf(" ")));
                String n = (str.substring(str.indexOf(" ") + 1));
                if(i==4){
                    text += n + " " + s;
                    writeText += s + " " + n;
                    break;
                }
                text += n + " " +s + "\n";
                writeText += s + " " + n + "\n";
                i++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeToInternalFile(fileName,writeText);
        if(fileName.equals("z.txt")){
            hsText = text;
        } else{
            hsText2 = text;
        }

    }

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

    /*
     BELOW ARE METHODS TO DETECT ACTION
     */

    //Activity Intent Comeback
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_CANCELED) {
                if(mBtn.getText().toString().equals("mute")){
                    mp.seekTo(0);
                    mp.start();
                }

            }
        }
    }

    //Back Key Pressed
    @Override
    public void onBackPressed() {
        mp.stop();
        finish();
        super.onBackPressed();
    }

    //Home Key Pressed
    @Override
    protected void onUserLeaveHint()
    {
        exit = 1;
        mp.pause();
        super.onUserLeaveHint();
    }

    //User Come Back
    @Override
    protected void onResume() {
        super.onResume();
        if(exit == 1){
            exit = 0;
            if(mute==0)
                mp.start();
        }
    }
}
