package coms362.cards.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import coms362.cards.abstractcomp.GameFactory;
import coms362.cards.events.inbound.ConnectEvent;
import coms362.cards.events.inbound.EndPlayEvent;
import coms362.cards.events.inbound.Event;
import coms362.cards.model.PlayerFactory;
import coms362.cards.socket.SocketMessage;
import coms362.cards.streams.InBoundQueue;
import coms362.cards.streams.RemoteTableGateway;

/*
 * 1. User connects and ConnectEvent is created and added to event queue.
 * 2. MatchController takes ConnectEvent from queue and
 * applies it to Rules which results in a Move.
 * 3. Applies Move to the table.
 * 4. Because single player game, table has partiesReady true.
 * 5. Applies Move to the views.
 * 6. MatchController tests table if partiesReady.
 * 7. MatchController creates InitEvent.
 * 8. MatchController creates PlayController and starts it.
 */

public class TestMatchController {

	@Test	
	public void test() {

		InBoundQueue inQ = new InBoundQueue();
		PlayerFactory playerFactory = new MockPlayerFactory();
		MockTableBase table = new MockTableBase(playerFactory);
		MockRules rules = new MockRules();
		RemoteTableGateway remote = RemoteTableGateway.getInstance();
		GameFactory gameFactory = new MockGameFactory();

		MatchController mc = new MatchController(inQ, table, rules, remote, gameFactory);
		
		Event connectEvent = new ConnectEvent(new SocketMessage("connect", 1234));
		inQ.add(connectEvent);
		inQ.add(new EndPlayEvent());
		
		mc.start();
		
		assertEquals(2, rules.getEvalCount());
		assertEquals(2, rules.getMockMove().getCountApplyTable());
		assertEquals(2, rules.getMockMove().getCountApplyView());
		assertTrue(table.partiesReady());
	}
}
