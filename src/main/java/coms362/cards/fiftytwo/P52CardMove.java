package coms362.cards.fiftytwo;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.AddToPileTopRemote;
import coms362.cards.events.remote.HideCardRemote;
import coms362.cards.events.remote.RemoveFromPileRemote;
import coms362.cards.events.remote.ShowCardRemote;
import coms362.cards.events.remote.ShowPlayerScore;
import coms362.cards.model.Card;
import coms362.cards.model.Pile;

public class P52CardMove implements Move {
	
	private Card c;
	private Player p;
	private Pile fromPile;
	private Pile toPile;
	
	public P52CardMove(Card c, Player p, Pile fromPile, Pile toPile){
		this.c = c;
		this.p = p;
		this.fromPile = fromPile;
		this.toPile = toPile;
	}
	
	public void apply(Table table) {
		table.removeFromPile(P52Rules.DROP_PILE, c);
		table.addToPile(P52Rules.PICKUP_PILE, c);
		table.addToScore(p, 1);
	}
	
	public void apply(ViewFacade view) {
		view.send(new HideCardRemote(c));
		view.send(new RemoveFromPileRemote(fromPile, c));
		view.send(new AddToPileTopRemote(toPile, c));
		view.send(new ShowCardRemote(c));
		view.send(new ShowPlayerScore(p, null));
	}

}
