package coms362.cards.app;

import coms362.cards.abstractcomp.GameFactory;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Rules;
import coms362.cards.abstractcomp.Table;
import coms362.cards.abstractcomp.View;
import coms362.cards.game.PartyRole;
import coms362.cards.model.PlayerFactory;
import coms362.cards.streams.RemoteTableGateway;

public class MockGameFactory implements GameFactory {

	@Override
	public Rules createRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table createTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerFactory createPlayerFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player createPlayer(Integer position, String socketId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View createView(PartyRole role, Integer num, String socketId, RemoteTableGateway gw) {
		return new MockView();
	}

}
