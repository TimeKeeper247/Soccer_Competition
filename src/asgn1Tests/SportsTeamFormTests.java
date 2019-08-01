package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1SoccerCompetition.SportsTeamForm;
import asgn1SportsUtils.WLD;

/**
 * A set of JUnit tests for the asgn1SoccerCompetition.SoccerTeamForm class
 *
 * @author Alan Woodley
 *
 */
public class SportsTeamFormTests {
	private SportsTeamForm form;
	private final int maxForm = 5;
	
	@Before
	public void new_form(){
		form = new SportsTeamForm();
	}
	
	@Test
	public void normal_results(){
		form.addResultToForm(WLD.WIN);
		form.addResultToForm(WLD.DRAW);
		form.addResultToForm(WLD.LOSS);
		assertEquals("LDW--",form.toString());
		form.resetForm();
		assertEquals("-----",form.toString());
	}
	
	@Test
	public void result_overflow(){
		for (int i=0;i<maxForm;i++){
			form.addResultToForm(WLD.WIN);
		}
		assertEquals("WWWWW",form.toString());
		form.addResultToForm(WLD.DRAW);
		form.addResultToForm(WLD.LOSS);
		assertEquals("LDWWW",form.toString());
	}
	
	@Test
	public void varying_num_games(){
		for (int i=0;i<maxForm-2;i++){
			form.addResultToForm(WLD.WIN);
		}
		assertEquals(3,form.getNumGames());
		form.addResultToForm(WLD.DRAW);
		assertEquals(4,form.getNumGames());
		form.addResultToForm(WLD.LOSS);
		assertEquals(5,form.getNumGames());
		form.resetForm();
		assertEquals(0,form.getNumGames());
	}
}
