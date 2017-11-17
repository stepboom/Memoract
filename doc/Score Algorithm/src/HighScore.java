import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class HighScore {

	static BufferedReader in;
	static BufferedWriter out;
	static String name[] = new String[5];
	static int score[] = new int[5];

	public static void updateScore(String n, int s) throws IOException {
		int pos = 0;
		int start = 0;
		int end = 4;

		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (s == score[mid]) {
				pos = mid;
				break;
			} else if (s > score[mid]) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}
		System.out.println(pos + " " + start + " " + end);
		pos = start;
		if (pos < 5) {
			for (int i = 4; i > pos; i--) {
				score[i] = score[i - 1];
				name[i] = name[i - 1];
			}
			score[pos] = s;
			name[pos] = n;
		}

		for (int i = 0; i < 5; i++) {
			String w = name[i] + " " + score[i];
			out.write(w);
			out.newLine();
		}
		out.close();

	}

	public static void loadHighScore() throws IOException {
		File fileDir = new File("z.txt");
		in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("z.txt")), "UTF-8"));
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("zz.txt")), "UTF-8"));
		String str;

		int i = 0;

		while ((str = in.readLine()) != null) {
			String n = str.substring(0, str.indexOf(" "));
			int s = Integer.parseInt(str.substring(str.indexOf(" ") + 1));
			name[i] = n;
			score[i] = s;
			
			i++;
		}

		in.close();

		updateScore("Pizza", 10);
		
		for(int j = 0 ; j<5 ;j++) {
			System.out.println(name[j] + " " + score[j]);
		}

	}

	public static void main(String[] args) throws IOException {
		loadHighScore();
	}

}