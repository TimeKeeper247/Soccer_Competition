package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import asgn1Exceptions.TeamException;
import asgn1SoccerCompetition.SoccerTeam;


/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerLeage class
 *
 * @author Alan Woodley
 *
 */
public class SoccerTeamTests {
	private SoccerTeam team;
	private final int win_points = 3;
	private final int draw_points = 1;
	private final int loss_points = 0;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void new_team() throws TeamException{
		team = new SoccerTeam("Official","Nickname");
	}
	
	@Test
	public void return_names(){
		assertEquals("Official", team.getOfficialName());
		assertEquals("Nickname", team.getNickName());
	}
	
	@Test
	public void normal_win() throws TeamException{
		team.playMatch(4, 2);
		assertEquals(1, team.getMatchesWon());
		assertEquals(win_points, team.getCompetitionPoints());
		assertEquals("W----", team.getFormString());
	}
	
	@Test
	public void normal_loss() throws TeamException{
		team.playMatch(2, 4);
		assertEquals(1, team.getMatchesLost());
		assertEquals(loss_points, team.getCompetitionPoints());
		assertEquals("L----", team.getFormString());
	}
	
	@Test
	public void normal_draw() throws TeamException{
		team.playMatch(4,  4);
		assertEquals(1, team.getMatchesDrawn());
		assertEquals(draw_points, team.getCompetitionPoints());
		assertEquals("D----", team.getFormString());
	}
	
	@Test
	public void reset_stats() throws TeamException{
		team.playMatch(4, 2);
		assertEquals(1, team.getMatchesWon());
		assertEquals(0, team.getMatchesLost());
		assertEquals(win_points, team.getCompetitionPoints());
		assertEquals("W----", team.getFormString());
		team.resetStats();
		team.playMatch(2, 4);
		assertEquals(0, team.getMatchesWon());
		assertEquals(1, team.getMatchesLost());
		assertEquals(loss_points, team.getCompetitionPoints());
		assertEquals("L----", team.getFormString());
	}
	
	@Test
	public void accumulated_stats() throws TeamException{
		final int goals_for  = 4;
		final int goals_against = 2;
		team.playMatch(goals_for, goals_against);
		assertEquals(goals_for, team.getGoalsScoredSeason());
		assertEquals(goals_against, team.getGoalsConcededSeason());
		assertEquals(goals_for-goals_against, team.getGoalDifference());
		assertEquals("W----", team.getFormString());
		team.playMatch(goals_for, goals_against);
		assertEquals(goals_for*2, team.getGoalsScoredSeason());
		assertEquals(goals_against*2, team.getGoalsConcededSeason());
		assertEquals((goals_for*2)-(goals_against*2), team.getGoalDifference());
		assertEquals("WW---", team.getFormString());
		team.playMatch(goals_against, goals_for);
		assertEquals(goals_for*2+goals_against, team.getGoalsScoredSeason());
		assertEquals(goals_against*2+goals_for, team.getGoalsConcededSeason());
		assertEquals((goals_for*2+goals_against)-(goals_against*2+goals_for), team.getGoalDifference());
		assertEquals("LWW--", team.getFormString());
		team.resetStats();
		assertEquals(0, team.getGoalsScoredSeason());
		assertEquals(0, team.getGoalsConcededSeason());
		assertEquals("-----", team.getFormString());
	}
	
	@Test
	public void comparisons() throws TeamException{
		//Other team has same name for later comparison
		SoccerTeam team2 = new SoccerTeam("Official", "Nickname");
		team.playMatch(4, 2);
		team2.playMatch(2, 4);
		assertTrue(team.compareTo(team2)<0);//team has more comp. points
		assertTrue(team2.compareTo(team)>0);//team has less comp. points
		team.resetStats();
		team2.resetStats();
		team.playMatch(4, 2);
		team2.playMatch(10, 2);
		assertTrue(team2.getGoalDifference() > team.getGoalDifference());
		//Both teams have same comp. points therefore goal difference is used
		assertTrue(team.compareTo(team2)>0);
		team.playMatch(7, 0);//To reach same goal difference as team2
		team2.playMatch(1, 0);//To ream same comp. points as team
		assertTrue(team.getGoalDifference() == team2.getGoalDifference());
		//Since all stats are the same including the name, .compareTo() will return 0
		assertEquals(0, team.compareTo(team2));
	}
	
	@Test //Shouldn't throw exception if names are the same
	public void new_nameless_team() throws TeamException{
		team = new SoccerTeam("Same", "Same");
		exception.expect(TeamException.class);
		team = new SoccerTeam("", "");
		team = new SoccerTeam(null,null);
	}
	
	@Test //Shouldn't throw exception at the limits; only when past
	public void unrealistic_scores() throws TeamException{
		team.playMatch(0, 0);
		team.playMatch(20, 20);
		exception.expect(TeamException.class);
		team.playMatch(21, 21);
		team.playMatch(-1, -1);
	}
}
