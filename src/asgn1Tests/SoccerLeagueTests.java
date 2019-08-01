package asgn1Tests;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import asgn1Exceptions.LeagueException;
import asgn1Exceptions.TeamException;
import asgn1SoccerCompetition.SoccerLeague;
import asgn1SoccerCompetition.SoccerTeam;


/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerLeage class
 *
 * @author Alan Woodley
 *
 */
public class SoccerLeagueTests {
	private SoccerLeague league;
	private SoccerTeam team,team2,team3;
	private int num_teams = 2;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void new_league() throws LeagueException, TeamException{
		league = new SoccerLeague(num_teams);
	}
	
	@Test
	public void required_num_teams(){
		assertEquals(num_teams, league.getRequiredNumTeams());
	}
	
	@Test
	public void normal_register_team() throws LeagueException, TeamException{
		team = new SoccerTeam("Official","Nickname");
		league.registerTeam(team);
		assertTrue(league.getRegisteredNumTeams() == 1);
		assertTrue(league.containsTeam("Official"));
	}
	
	@Test
	public void  normal_remove_team() throws LeagueException, TeamException{
		team = new SoccerTeam("Official","Nickname");
		league.registerTeam(team);
		assertTrue(league.getRegisteredNumTeams() == 1);
		assertTrue(league.containsTeam("Official"));
		league.removeTeam(team);
		assertTrue(league.getRegisteredNumTeams() == 0);
		assertFalse(league.containsTeam("Official"));
	}
	
	@Test
	public void normal_start_season() throws LeagueException, TeamException{
		team = new SoccerTeam("Team1","Team1");
		team2 = new SoccerTeam("Team2","Team2");
		league.registerTeam(team);
		league.registerTeam(team2);
		league.startNewSeason();
		assertFalse(league.isOffSeason());
		league.playMatch("Team1", 4, "Team2", 2);
		assertTrue(league.getTopTeam() == team);
		assertTrue(league.getBottomTeam() == team2);
		league.endSeason();
		assertTrue(league.isOffSeason());
	}
	
	@Test
	public void normal_play_match() throws LeagueException, TeamException{
		team = new SoccerTeam("Team1","Team1");
		team2 = new SoccerTeam("Team2","Team2");
		league.registerTeam(team);
		league.registerTeam(team2);
		league.startNewSeason();
		league.playMatch("Team1", 4, "Team2", 2);
		assertTrue(league.getTopTeam() == team);
		assertTrue(league.getBottomTeam() == team2);
	}
	
	@Test
	public void empty_season() throws LeagueException{
		exception.expect(LeagueException.class);
		league.startNewSeason();
	}
	
	@Test
	public void league_full() throws LeagueException, TeamException{
		exception.expect(LeagueException.class);
		exception.expectMessage("League is already full");
		for (int i=0;i<this.num_teams+1;i++){
			league.registerTeam(new SoccerTeam("Team"+i,"Team"+i));
		}
		//Cannot check if season is already started as well as if league is full as the season cannot start unless the league is full
	}
	
	@Test
	public void same_team_registration() throws LeagueException, TeamException{
		exception.expect(LeagueException.class);
		exception.expectMessage("A team with that official name already exists");
		for (int i=0;i<this.num_teams;i++){
			league.registerTeam(new SoccerTeam("Same","Same"+i));
		}
	}
	
	@Test
	public void premature_team_removal() throws LeagueException, TeamException{
		team = new SoccerTeam("Team1","Team1");
		team2 = new SoccerTeam("Team2","Team2");
		league.registerTeam(team);
		league.registerTeam(team2);
		league.startNewSeason();
		exception.expect(LeagueException.class);
		exception.expectMessage("Season has not yet ended");
		league.removeTeam(team);
		league.removeTeam(team2);
	}
	
	@Test
	public void nonexistent_team() throws LeagueException, TeamException{
		for (int i=0;i<this.num_teams;i++){
			league.registerTeam(new SoccerTeam("Team"+i,"Team"+i));
		}
		exception.expect(LeagueException.class);
		exception.expectMessage("No team has that name");
		league.getTeamByOfficialName("Nonexistent Team");
	}
	
	@Test
	public void nonexistent_team_removal() throws LeagueException, TeamException{
		team = new SoccerTeam("Team1","Team1");
		team2 = new SoccerTeam("Team2","Team2");
		team3 = new SoccerTeam("Team3","Team3");
		league.registerTeam(team);
		league.registerTeam(team2);
		exception.expect(LeagueException.class);
		exception.expectMessage("That team does not exist in the league");
		league.removeTeam(team3);
	}
	
	@Test
	public void nonstarted_season() throws LeagueException, TeamException{
		for (int i=0;i<this.num_teams;i++){
			league.registerTeam(new SoccerTeam("Team"+i,"Team"+i));
		}
		exception.expect(LeagueException.class);
		exception.expectMessage("Season has not yet started");
		league.endSeason();
	}
	
	@Test
	public void same_team_match() throws LeagueException, TeamException{
		for (int i=0;i<this.num_teams;i++){
			league.registerTeam(new SoccerTeam("Team"+i,"Team"+i));
		}
		league.startNewSeason();
		exception.expect(LeagueException.class);
		exception.expectMessage("Both teams have the same name");
		league.playMatch("Team1",4,"Team1",2);
	}
	
	@Test
	public void off_season_match() throws LeagueException, TeamException{
		for (int i=0;i<this.num_teams;i++){
			league.registerTeam(new SoccerTeam("Team"+i,"Team"+i));
		}
		exception.expect(LeagueException.class);
		exception.expectMessage("Season has not yet started");
		league.playMatch("Team1",4,"Team2",2);
	}
}

