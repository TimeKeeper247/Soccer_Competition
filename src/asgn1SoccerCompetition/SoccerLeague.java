package asgn1SoccerCompetition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import asgn1Exceptions.LeagueException;
import asgn1Exceptions.TeamException;
import java.util.List;
import java.util.List;

/**
 * A class to model a soccer league. Matches are played between teams and points awarded for a win,
 * loss or draw. After each match teams are ranked, first by points, then by goal difference and then
 * alphabetically. 
 * 
 * @author Alan Woodley
 * @version 1.0
 *
 */
public class SoccerLeague implements SportsLeague{
	// Specifies the number of team required/limit of teams for the league
	private int requiredTeams;
	// Specifies is the league is in the off season
	private boolean offSeason;
	
	private List<SoccerTeam> team_list;
	
	/**
	 * Generates a model of a soccer team with the specified number of teams. 
	 * A season can not start until that specific number of teams has been added. 
	 * Once that number of teams has been reached no more teams can be added unless
	 * a team is first removed. 
	 * 
	 * @param requiredTeams The number of teams required/limit for the league.
	 */
	public SoccerLeague (int requiredTeams){
		// TODO
		this.offSeason = true;
		this.requiredTeams = requiredTeams;
		team_list = new ArrayList<SoccerTeam>(this.requiredTeams);
	}

	
	/**
	 * Registers a team to the league.
	 * 
	 * @param team Registers a team to play in the league.
	 * @throws LeagueException If the season has already started, if the maximum number of 
	 * teams allowed to register has already been reached or a team with the 
	 * same official name has already been registered.
	 */
	public void registerTeam(SoccerTeam team) throws LeagueException {
		// TODO
		if (this.getRegisteredNumTeams() == this.getRequiredNumTeams()){
			throw new LeagueException("League is already full");
		} else if (!this.isOffSeason()){
			throw new LeagueException("Season has already started");
		}
		for (SoccerTeam this_team : team_list){
			if (team_list.get(team_list.indexOf(this_team)).getOfficialName().equals(team.getOfficialName())){
				throw new LeagueException("A team with that official name already exists");
			}
		}
		team_list.add(team);
	}
	
	/**
	 * Removes a team from the league.
	 * 
	 * @param team The team to remove
	 * @throws LeagueException if the season has not ended or if the team is not registered into the league.
	 */
	public void removeTeam(SoccerTeam team) throws LeagueException{
		// TODO
		if (!this.isOffSeason()){
			throw new LeagueException("Season has not yet ended");
		}
		Iterator<SoccerTeam> iter = team_list.iterator();
		while(iter.hasNext()){
			SoccerTeam this_team = iter.next();
			if (team_list.get(team_list.indexOf(this_team)).getOfficialName().equals(team.getOfficialName())){
				iter.remove();
				return;
			}
		}
		throw new LeagueException("That team does not exist in the league");
	}
	
	/** 
	 * Gets the number of teams currently registered to the league
	 * 
	 * @return the current number of teams registered
	 */
	public int getRegisteredNumTeams(){
		// TODO
		return team_list.size();
	}
	
	/**
	 * Gets the number of teams required for the league to begin its 
	 * season which is also the maximum number of teams that can be registered
	 * to a league.

	 * @return The number of teams required by the league/maximum number of teams in the league
	 */
	public int getRequiredNumTeams(){
		return requiredTeams;
	}
	
	/** 
	 * Starts a new season by reverting all statistics for each team to initial values.
	 * 
	 * @throws LeagueException if the number of registered teams does not equal the required number of teams or if the season has already started
	 */
	public void startNewSeason() throws LeagueException{
		// TODO 
		if (this.getRequiredNumTeams() != this.getRegisteredNumTeams()){
			throw new LeagueException("League is missing required number of teams.");
		} else if (!this.isOffSeason()){
			throw new LeagueException("Season has already started");
		}
		for (SoccerTeam team : team_list){
			team_list.get(team_list.indexOf(team)).resetStats();
		}
		this.offSeason = false;
		this.sortTeams();
	}
	

	/**
	 * Ends the season.
	 * 
	 * @throws LeagueException if season has not started
	 */
	public void endSeason() throws LeagueException{
		// TODO
		if (this.isOffSeason()){
			throw new LeagueException("Season has not yet started");
		}
		offSeason = true;
	}
	
	/**
	 * Specifies if the league is in the off season (i.e. when matches are not played).
	 * @return True If the league is in its off season, false otherwise.
	 */
	public boolean isOffSeason(){
		return this.offSeason;
	}
	
	
	
	/**
	 * Returns a team with a specific name.
	 * 
	 * @param name The official name of the team to search for.
	 * @return The team object with the specified official name.
	 * @throws LeagueException if no team has that official name.
	 */
	public SoccerTeam getTeamByOfficialName(String name) throws LeagueException{		
		// TODO 
		for (SoccerTeam team : team_list){
			if (team_list.get(team_list.indexOf(team)).getOfficialName().equals(name)){
				return team_list.get(team_list.indexOf(team));
			}
		}
		throw new LeagueException("No team has that name");
	}
		
	/**
	 * Plays a match in a specified league between two teams with the respective goals. After each match the teams are
	 * resorted.
     *
	 * @param homeTeamName The name of the home team.
	 * @param homeTeamGoals The number of goals scored by the home team.
	 * @param awayTeamName The name of the away team.
	 * @param awayTeamGoals The number of goals scored by the away team.
	 * @throws LeagueException If the season has not started or if both teams have the same official name. 
	 */
	public void playMatch(String homeTeamName, int homeTeamGoals, String awayTeamName, int awayTeamGoals) throws LeagueException{
		// TODO 
		if (homeTeamName == awayTeamName){
			throw new LeagueException("Both teams have the same name");
		} else if (this.isOffSeason()){
			throw new LeagueException("Season has not yet started");
		}
		try {
			this.getTeamByOfficialName(homeTeamName).playMatch(homeTeamGoals, awayTeamGoals);
			this.getTeamByOfficialName(awayTeamName).playMatch(awayTeamGoals, homeTeamGoals);
		} catch (TeamException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		this.sortTeams();
	}
	
	/**
	 * Displays a ranked list of the teams in the league  to the screen.
	 */
	public void displayLeagueTable(){
		// TODO (optional)
		for (SoccerTeam team : team_list){
			team_list.get(team_list.indexOf(team)).displayTeamDetails();
		}
	}	
	
	/**
	 * Returns the highest ranked team in the league.
     *
	 * @return The highest ranked team in the league. 
	 * @throws LeagueException if the number of teams is zero or less than the required number of teams.
	 */
	public SoccerTeam getTopTeam() throws LeagueException{
		// TODO
		if (this.getRegisteredNumTeams() == 0 || this.getRegisteredNumTeams() < this.getRequiredNumTeams()){
			throw new LeagueException("League has less than the required number of teams");
		}
		this.sortTeams();
		return team_list.get(0);
	}

	/**
	 * Returns the lowest ranked team in the league.
     *
	 * @return The lowest ranked team in the league. 
	 * @throws LeagueException if the number of teams is zero or less than the required number of teams.
	 */
	public SoccerTeam getBottomTeam() throws LeagueException{
		// TODO
		if (this.getRegisteredNumTeams() == 0 || this.getRegisteredNumTeams() < this.getRequiredNumTeams()){
			throw new LeagueException("League has less than the required number of teams");
		}
		this.sortTeams();
		return team_list.get(team_list.size()-1);
	}

	/** 
	 * Sorts the teams in the league.
	 */
    public void sortTeams(){		
		// TODO
    	Collections.sort(team_list);
    }
    
    /**
     * Specifies if a team with the given official name is registered to the league.
     * 
     * @param name The name of a team.
     * @return True if the team is registered to the league, false otherwise. 
     */
    public boolean containsTeam(String name){
		// TODO
    	for (SoccerTeam team : team_list){
    		if (team_list.get(team_list.indexOf(team)).getOfficialName() == name){
    			return true;
    		}
    	}
    	return false;
    }
    
}
