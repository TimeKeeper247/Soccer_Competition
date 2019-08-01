package asgn1SoccerCompetition;

import java.util.ArrayDeque;

import asgn1SportsUtils.WLD;
import java.lang.String;


/**
 * A data structure to store the 'form' of a sports team, that is the 
 * the result (win, loss, draw) of the last 5 matches played. Every time
 * that a new results is added, the previous results are shifted down a
 * position in the data structure. After 5 games are played, any new element 
 * will 'push' the least recent result from the back of the data structure and 
 * will then be added to the front. 
 * 
 *  
 * @author Alan Woodley
 *
 */
public class SportsTeamForm {

	// The number of recent games to show as the recent form of the team
	private static final int maxLength = 5;
	
	private ArrayDeque<Character> form;

	/**
	 * Constructs the data structure that holds the match results (win, loss, draw) for recent matches.
	 * For simplicity the results for the last 5 matches will be stored.
	 * 
	 */
	public SportsTeamForm() {
		// TODO
		form = new ArrayDeque<Character>(maxLength);
	}
	
	/**
	 * Adds a new result to the data structure. If the number of games played is less than 5 then the 
	 * result will be added to the the front of the data structure - with all teams shifting down one 
	 * position in the data structure. If the number of games played is more than 5 then the 6th most
	 * recent game is removed, the 2nd - 5th most recent games are shifted down a position and the 
	 * most recent game is added to the front of the data structure. 
	 * 
	 * @param result The result of the latest match
	 *
	 */
	public void addResultToForm(WLD result){ 
		// TODO
		if(form.size()<maxLength){
			form.addFirst(result.getChar());
		} else {
			form.removeLast();
			form.addFirst(result.getChar());
		}
	}
	
	/**
	 * Returns a string that represents the results of the last few matches that a team has played. 
	 * The length of the string returned will be equal to the maximum number of matches. 
	 * A win ('W'), loss ('L') or draw ('D') will be indicated as specified. The order of results 
	 * is "12345" (i.e left to right) where 1 is the last match played and 5 is the 5th last match played. 
	 * If the number of matches played is less than the maximum number of matches then a no match ('-') 
	 * character will be used instead. Again, the order will be left to right so after one match is
	 *  played the returned string will be "1----". 
	 * 
	 * @return A string representing the results of recent matches.
	 */
	public String toString(){
		// TODO
		Character[] chars = form.toArray(new Character[form.size()]);
		String str = "";
		for (Character c : chars){
			str += c.toString();
		}
		while(str.length() < maxLength){
			str = str + '-';
		}
		return str;
	}
	
	
	/**
	 * Returns the number of games played  
     *
	 * @return The number of games played. 
	 */
	public int getNumGames(){
		// TODO
		return form.size();
	}
	
	/**
	 * Resets the data structure to its initial values.
	 */
	public void resetForm(){
		// TODO
		form.clear();
	}
	
}
