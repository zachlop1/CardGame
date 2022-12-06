package coms362.cards.fiftytwo;

import java.util.Map;
import java.util.Random;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.CreateButtonRemote;
import coms362.cards.events.remote.CreatePileRemote;
import coms362.cards.events.remote.SetBottomPlayerTextRemote;
import coms362.cards.events.remote.SetGameTitleRemote;
import coms362.cards.events.remote.SetupTableRemote;
import coms362.cards.model.Card;
import coms362.cards.model.Location;
import coms362.cards.model.Pile;

public class P52InitMove implements Move {

	private Table table;
	private Map<Integer, Player> players;
	private String title;

	public P52InitMove(Map<Integer, Player> players, String game, Table table) {
		this.players = players;
		this.title = game;
		this.table = table;
	}

	public void apply(Table table) {
		Pile dropPile = new Pile(P52Rules.DROP_PILE, new Location(100, 100));
		Random random = table.getRandom();
		// create a standard deck of 52 cards with random placements
		for (String suit : Card.suits) {
			for (int i = 1; i <= 13; i++) {
				Card card = new Card();
				card.setSuit(suit);
				card.setRank(i);
				card.setX(random.nextInt(200) + 100);
				card.setY(random.nextInt(200) + 100);
				card.setRotate(random.nextInt(360));
				card.setFaceUp(random.nextBoolean());
				dropPile.addCard(card);
			}
		}
		table.addPile(dropPile);
		table.addPile(new Pile(P52Rules.PICKUP_PILE, new Location(500, 359)));
		dropPile.setFaceUp(true);
	}

	public void apply(ViewFacade view) {
		view.send(new SetupTableRemote());
		view.send(new SetGameTitleRemote(title));

		for (Player p : players.values()) {
			String role = (p.getPlayerNum() == 1) ? "Dealer" : "Player " + p.getPlayerNum();
			view.send(new SetBottomPlayerTextRemote(role, p));
		}

		view.send(new CreatePileRemote(table.getPile(P52Rules.DROP_PILE)));
		view.send(new CreatePileRemote(table.getPile(P52Rules.PICKUP_PILE)));

		P52DealButton dealButton = new P52DealButton("DEAL", new Location(50, 50));
		view.register(dealButton); // so we can find it later.
		view.send(new CreateButtonRemote(dealButton));
	}

}
