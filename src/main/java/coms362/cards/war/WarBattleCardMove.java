package coms362.cards.war;

import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.model.Card;
import coms362.cards.model.Pile;

public class WarBattleCardMove extends WarCardMove {
	public WarBattleCardMove(Card c, Player p, Pile fromPile, Pile toPile){
        super(c, p, fromPile, toPile);
    }
	
	@Override
	public void apply(Table table) {
        table.removeFromPile(WarBattleRules.BATTLE_DROP_PILE, c);
        table.addToPile(WarRules.PICKUP_PILE, c);
        table.addToScore(p, 1);
    }
}
