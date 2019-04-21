package Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * The class that prints to file for high scores
 * 
 * @author Mark Sabado
 */
public class HighScore {
	private int score;
	
	private PrintWriter out; 
	
	private File highScoreFile;
	private Scanner inp;
	private FileWriter fw;
	private BufferedWriter bw;
	
	private int oldHighScore;
	private int newHighScore;
	
	/**
	 * creates a new high score object
	 */
	public HighScore() {
		 try {
				highScoreFile = new File("HighScores.txt");
				inp = new Scanner(highScoreFile);
				fw = new FileWriter(highScoreFile);
				bw = new BufferedWriter(fw);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		//	oldHighScore = inp.nextInt();
	}
	
	/**
	 * updates the object
	 */
	public void update() {
		//out.println(score);
	}	
	
	/**
	 * gets your score
	 * 
	 * @return gets your score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * sets your score
	 * 
	 * @param score what your score is
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	
}
