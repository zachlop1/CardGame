package coms362.cards.war;

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

public class WarCardMove implements Move {

    protected Card c;
    protected Player p;
    protected Pile fromPile;
    protected Pile toPile;

    public WarCardMove(Card c, Player p, Pile fromPile, Pile toPile){
        this.c = c;
        this.p = p;
        this.fromPile = fromPile;
        this.toPile = toPile;
    }

    public void apply(Table table) {
        table.removeFromPile(WarRules.DROP_PILE, c);
        table.addToPile(WarRules.PICKUP_PILE, c);
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
