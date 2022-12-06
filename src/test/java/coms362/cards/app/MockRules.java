package coms362.cards.app;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Rules;
import coms362.cards.abstractcomp.Table;
import coms362.cards.events.inbound.Event;

public class MockRules implements Rules {
	int evalCount = 0;
	MockMove move = new MockMove();

	@Override
	public Move eval(Event nextE, Table table, Player player) {
		evalCount++;
		return getMockMove();
	}
	
	public int getEvalCount() {
		return evalCount;
	}
	
	public MockMove getMockMove() {
		return move;
	}
}
