/**
 * 
 */
package asgn1SoccerCompetition;

import java.util.ArrayList;
import java.util.List;

import asgn1Exceptions.CompetitionException;
import asgn1Exceptions.LeagueException;

/**
 * A class to model a soccer competition. The competition contains one or more number of leagues, 
 * each of which contain a number of teams. Over the course a season matches are played between
 * teams in each league. At the end of the season a premier (top ranked team) and wooden spooner 
 * (bottom ranked team) are declared for each league. If there are multiple leagues then relegation 
 * and promotions occur between the leagues.
 * 
 * @author Alan Woodley
 * @version 1.0
 *
 */
public class SoccerCompetition implements SportsCompetition{
	
	String name;
	private List<SoccerLeague> league_list;
	
	/**
	 * Creates the model for a new soccer competition with a specific name,
	 * number of leagues and number of teams in each league
	 * 
	 * @param name The name of the competition.
	 * @param numLeagues The number of leagues in the competition.
	 * @param numTeams The number of teams in each league.
	 */
	public SoccerCompetition(String name, int numLeagues, int numTeams){
		this.name = name;
		// TODO Complete
		league_list = new ArrayList<SoccerLeague>(numLeagues);
		for (int i=0;i<numLeagues;i++){
			league_list.add(new SoccerLeague(numTeams));
		}
	}
	
	/**
	 * Retrieves a league with a specific number (indexed from 0). Returns an exception if the 
	 * league number is invalid.
	 * 
	 * @param leagueNum The number of the league to return.
	 * @return A league specified by leagueNum.
	 * @throws CompetitionException if the specified league number is less than 0.
	 *  or equal to or greater than the number of leagues in the competition.
	 */
	public SoccerLeague getLeague(int leagueNum) throws CompetitionException{
		if (leagueNum < 0 || leagueNum >= league_list.size()){
			throw new CompetitionException("Invalid league number");
		}
		// TODO
		return league_list.get(leagueNum);
	}
	

	/**
	 * Starts a new soccer season for each league in the competition.
	 */
	public void startSeason() {

		// TODO
		for (SoccerLeague league : league_list){
			try {
				league_list.get(league_list.indexOf(league)).startNewSeason();
			} catch (LeagueException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	
	/** 
	 * Ends the season of each of the leagues in the competition. 
	 * If there is more than one league then it handles promotion
	 * and relegation between the leagues.  
	 * 
	 */
	public void endSeason()  {

		// TODO
		for (SoccerLeague league : league_list){
			try {
				league_list.get(league_list.indexOf(league)).endSeason();
			} catch (LeagueException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/** 
	 * For each league displays the competition standings.
	 */
	public void displayCompetitionStandings(){
		System.out.println("+++++" + this.name + "+++++");
		
		// TODO (optional)
		// HINT The heading for each league is
		//	System.out.println("---- League" + (i +1) + " ----");
		//  System.out.println("Official Name" +  '\t' +  "Nick Name" + '\t' + "Form" + '\t' +  "Played" + '\t' + "Won" + '\t' + "Lost" + '\t' + "Drawn" + '\t' + "For" + '\t' + "Against" + '\t' + "GlDiff" + '\t' + "Points");
		for (SoccerLeague league : league_list){
			System.out.println("---- League" + (league_list.indexOf(league)+1) + " ----");
			System.out.println("Official Name" +  '\t' +  "Nick Name" + '\t' + "Form" + '\t' +  "Played" + '\t' + "Won" + '\t' + "Lost" + '\t' + "Drawn" + '\t' + "For" + '\t' + "Against" + '\t' + "GlDiff" + '\t' + "Points");
			league_list.get(league_list.indexOf(league)).displayLeagueTable();
		}
	}
	

}
