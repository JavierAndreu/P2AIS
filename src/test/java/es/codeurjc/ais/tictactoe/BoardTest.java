package es.codeurjc.ais.tictactoe;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.codeurjc.ais.tictactoe.Board;

public class BoardTest {
	
	
	private Board tablero;
	int [] posiciones;
	List<Integer> l;
	
	
	@BeforeEach
	public void setUp(){
		tablero = new Board();
		l = new LinkedList<Integer>();
	}

	@Test
	public void testWin() {
		tablero.getCell(0).value = "x";
		tablero.getCell(1).value = "y";
		tablero.getCell(4).value = "x";
		tablero.getCell(8).value = "y";
		tablero.getCell(6).value = "x";
		tablero.getCell(2).value = "y";
		tablero.getCell(3).value = "x";
		
		posiciones = tablero.getCellsIfWinner("x");
		for(int i=0; i<3; i++) {
			l.add(posiciones[i]);
		}
		
		assertThat(l,hasItems(0,3,6));
		
	}
	
	
	@Test
	public void testLose() {
		tablero.getCell(0).value = "x";
		tablero.getCell(2).value = "y";
		tablero.getCell(3).value = "x";
		tablero.getCell(6).value = "y";
		tablero.getCell(8).value = "x";
		tablero.getCell(4).value = "y";
		
		posiciones = tablero.getCellsIfWinner("y");
		for(int i=0; i<3; i++) {
			l.add(posiciones[i]);
		}
		
		assertThat(l,hasItems(2,4,6));
		
	}
		
	
	@Test
	public void testCheckDraw() {
		tablero.getCell(0).value = "x";
		tablero.getCell(1).value = "y";
		tablero.getCell(3).value = "x";
		tablero.getCell(6).value = "y";
		tablero.getCell(2).value = "x";
		tablero.getCell(4).value = "y";
		tablero.getCell(7).value = "x";
		tablero.getCell(5).value = "y";
		
		assertFalse(tablero.checkDraw());
		
		tablero.getCell(8).value = "x";
		
		assertTrue(tablero.checkDraw());
		
	}

}
