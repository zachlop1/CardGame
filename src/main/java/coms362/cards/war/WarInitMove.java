package coms362.cards.war;

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
import coms362.cards.fiftytwo.P52DealButton;
import coms362.cards.model.Card;
import coms362.cards.model.Location;
import coms362.cards.model.Pile;

public class WarInitMove implements Move {

    private Table table;
    private Map<Integer, Player> players;
    private String title;

    public WarInitMove(Map<Integer, Player> players, String game, Table table) {
        this.players = players;
        this.title = game;
        this.table = table;
    }

    public void apply(Table table) {
        Pile dropPile = new Pile(WarRules.DROP_PILE, new Location(200, 100));
        Pile dropPile2 = new Pile(WarRules.DROP_PILE2, new Location(400, 100));
        for (String suit : Card.suits) {
            for (int i = 1; i <= 13; i++) {
                Card card = new Card();
                card.setSuit(suit);
                card.setRank(i);
                card.setX(200*(i%2 + 1)+i);
                card.setY(100+i);
                card.setRotate(0);
                card.setFaceUp(false);
                if(i%2 == 0) {
                	dropPile.addCard(card);
                } else {
                	dropPile2.addCard(card);
                }      
            }
        }
        table.addPile(dropPile);
        table.addPile(dropPile2);
        table.addPile(new Pile(WarRules.PICKUP_PILE, new Location(200, 359)));
        table.addPile(new Pile(WarRules.PICKUP_PILE2, new Location(400, 359)));
        dropPile.setFaceUp(true);
    }

    public void apply(ViewFacade view) {
        view.send(new SetupTableRemote());
        view.send(new SetGameTitleRemote(title));

        for (Player p : players.values()) {
            String role = (p.getPlayerNum() == 1) ? "Dealer" : "Player " + p.getPlayerNum();
            view.send(new SetBottomPlayerTextRemote(role, p));
        }

        view.send(new CreatePileRemote(table.getPile(WarRules.DROP_PILE)));
        view.send(new CreatePileRemote(table.getPile(WarRules.DROP_PILE2)));
        view.send(new CreatePileRemote(table.getPile(WarRules.PICKUP_PILE)));
        view.send(new CreatePileRemote(table.getPile(WarRules.PICKUP_PILE2)));

        P52DealButton dealButton = new P52DealButton("DEAL", new Location(50, 50));
        view.register(dealButton); // so we can find it later.
        view.send(new CreateButtonRemote(dealButton));
    }

}
