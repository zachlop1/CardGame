package coms362.cards.game;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;


public class EndPlayMove implements Move {

	private Player winner;

	public EndPlayMove(Player winner) {
		this.winner = winner;
	}

 	public void apply(Table table) {
		table.setMatchOver(true);
		if (winner != null) {
			table.setMatchOver(true);
		}
	}
	public void apply(ViewFacade view) {
		view.send(new SetGameTitleRemote(String.format("Player %s Wins!", winner.getPlayerNum())));

	P52DealButton dealButton = new P52DealButton("DEAL", new Location(50, 50));
	view.register(dealButton); // so we can find it later.


	public void apply(Table table) {
		table.setMatchOver(true);
	}

	public void apply(ViewFacade view) {	
	}
	
	@Override	
	public boolean isMatchEnd(){
		return true;
	}

}
