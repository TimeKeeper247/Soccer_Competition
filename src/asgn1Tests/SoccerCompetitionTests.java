package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import asgn1Exceptions.CompetitionException;
import asgn1Exceptions.LeagueException;
import asgn1Exceptions.TeamException;
import asgn1SoccerCompetition.SoccerCompetition;
import asgn1SoccerCompetition.SoccerLeague;
import asgn1SoccerCompetition.SoccerTeam;

/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerCompetition class
 *
 * @author Alan Woodley
 *
 */
public class SoccerCompetitionTests {
	private SoccerCompetition competition;
	private final int num_leagues = 1;
	private final int num_teams = 2;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void new_competition(){
		competition = new SoccerCompetition("comp1",num_leagues,num_teams);
	}
	
	@Test
	public void normal_season()throws CompetitionException, LeagueException, TeamException{
		for (int i=0;i<num_leagues;i++){
			for (int j=0;j<num_teams;j++){
				competition.getLeague(i).registerTeam(new SoccerTeam("Team"+j,"Team"+j));
			}
		}
		competition.startSeason();
		assertFalse(competition.getLeague(0).isOffSeason());
		assertFalse(competition.getLeague(num_leagues-1).isOffSeason());
		competition.endSeason();
		assertTrue(competition.getLeague(0).isOffSeason());
		assertTrue(competition.getLeague(num_leagues-1).isOffSeason());
	}
	
	@Test
	public void nonexistent_league() throws CompetitionException{
		exception.expect(CompetitionException.class);
		exception.expectMessage("Invalid league number");
		competition.getLeague(num_leagues);
	}
}

