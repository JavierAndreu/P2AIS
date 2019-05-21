package es.codeurjc.ais.tictactoe;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.hamcrest.MockitoHamcrest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;

public class TicTacToeGameTest {
	
	TicTacToeGame juego;
	Connection c1;
	Connection c2;
	Player p0;
	Player p1;
	
	
	@BeforeEach
	public void setUp(){
		juego = new TicTacToeGame();
		c1 = mock(Connection.class);
		c2 = mock(Connection.class);
		p0 = new Player(0,"x","Javier");
		p1 = new Player(1,"y", "Diego"); 
		juego.addConnection(c1);
		juego.addConnection(c2);
		
		juego.addPlayer(p0);
		reset(c1);
		reset(c2);
		juego.addPlayer(p1);
		verify(c1).sendEvent(eq(EventType.JOIN_GAME), MockitoHamcrest.argThat(hasItems(p0,p1)));
		verify(c2).sendEvent(eq(EventType.JOIN_GAME), MockitoHamcrest.argThat(hasItems(p0, p1)));
		verify(c1).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(c2).sendEvent(eq(EventType.SET_TURN), eq(p0));
		reset(c1);
		reset(c2);
	}
	
	

	@Test
	public void testTicTacToeGameWin() {
		
		juego.mark(0);
		juego.mark(1);
		juego.mark(4);
		juego.mark(8);
		juego.mark(6);
		juego.mark(2);
		
		verify(c1,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(c2,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(c1,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		verify(c2,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		
		juego.mark(3);
		
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
	    verify(c1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	    assertEquals(argument.getValue().player, p0);
		
	}
	
	
	@Test
	public void testTicTacToeGameLose() {
		
		juego.mark(0);
		juego.mark(2);
		juego.mark(3);
		juego.mark(6);
		juego.mark(8);
		
		verify(c1,times(2)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(c2,times(2)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(c1,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		verify(c2,times(3)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		
		juego.mark(4);
		
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
	    verify(c1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	    assertEquals(argument.getValue().player, p1);
		
	}
	
	
	@Test
	public void testTictacToeGameDraw() {
		
		juego.mark(0);
		juego.mark(1);
		juego.mark(3);
		juego.mark(6);
		juego.mark(2);
		juego.mark(4);
		juego.mark(7);
		juego.mark(5);
		
		verify(c1,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(c2,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p0));
		verify(c1,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		verify(c2,times(4)).sendEvent(eq(EventType.SET_TURN), eq(p1));
		
		juego.mark(8);
		
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
	    verify(c1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		assertNull(argument.getValue());
	}

}
