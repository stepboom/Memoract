package cucpr.memoract;

import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class HighScore extends AppCompatActivity {

    private BufferedReader in;
    private BufferedWriter out;
    private String nameArray[] = new String[5];
    private int scoreArray[] = new int[5];
    private static HighScore instance = new HighScore();

    public static HighScore getInstance(){
        return instance;
    }

    public HighScore(){
        try {
            in = new BufferedReader(new InputStreamReader(getResources().openRawResource(cucpr.memoract.R.raw.z), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

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


    public void updateScore(String n, int s,int pos) {
        if (pos < 5) {
            for (int i = 4; i > pos; i--) {
                scoreArray[i] = scoreArray[i - 1];
                nameArray[i] = nameArray[i - 1];
            }
            scoreArray[pos] = s;
            nameArray[pos] = n;
        }
        writeToInternalFile("z.txt",getHighScoreString());

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



    public void loadHighScore() {
        String str;

        int i = 0;

        try {
            while ((str = in.readLine()) != null) {
                int s = Integer.parseInt(str.substring(0, str.indexOf(" ")));
                String n = (str.substring(str.indexOf(" ") + 1));
                nameArray[i] = n;
                scoreArray[i] = s;
                System.out.println(nameArray[i] + " " + scoreArray[i]);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeToInternalFile("z.txt",getHighScoreString());
    }

    public String getHighScoreString(){
        String join ="";
        for(int i = 0 ;i < 5;i++){
            join += nameArray[i] + " " + scoreArray[i] + "\n";
        }
        return join;
    }






}
