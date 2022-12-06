package coms362.cards.war;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Rules;
import coms362.cards.abstractcomp.RulesDispatch;
import coms362.cards.abstractcomp.RulesDispatchBase;
import coms362.cards.abstractcomp.Table;
import coms362.cards.events.inbound.CardEvent;
import coms362.cards.events.inbound.ConnectEvent;
import coms362.cards.events.inbound.DealEvent;
import coms362.cards.events.inbound.Event;
import coms362.cards.events.inbound.EventUnmarshallers;
import coms362.cards.events.inbound.GameRestartEvent;
import coms362.cards.events.inbound.InitGameEvent;
import coms362.cards.events.inbound.NewPartyEvent;
import coms362.cards.events.inbound.SetQuorumEvent;
import coms362.cards.game.CreatePlayerMove;
import coms362.cards.game.PartyRole;
import coms362.cards.game.SetQuorumMove;
import coms362.cards.model.Card;
import coms362.cards.model.Pile;
import coms362.cards.game.DoNothingMove;

public class WarBattleRules extends WarRules
        					implements Rules, RulesDispatch {

	public static final String BATTLE_DROP_PILE = "battlePile";
    public static final String BATTLE_DROP_PILE2 = "battlePile2";
	
    public WarBattleRules() {
    	super();
    }

    public Move apply(CardEvent e, Table table, Player player){
    	Pile fromPile;
    	Pile toPile;
    	if(player.getPlayerNum()%2 == 0) {
    		fromPile = table.getPile(BATTLE_DROP_PILE);
            toPile = table.getPile(PICKUP_PILE);
    	} else {
    		fromPile = table.getPile(BATTLE_DROP_PILE2);
            toPile = table.getPile(PICKUP_PILE2);
    	}  
        Card c = fromPile.getCard(e.getId());
        if (c == null) {
            return new DoNothingMove();
        }
        return new WarCardMove(c, player, fromPile, toPile);
    }

    @Override
    public Move apply(InitGameEvent e, Table table, Player player){
        return new DoNothingMove();
    }

    @Override
    public Move apply(NewPartyEvent e, Table table, Player player){
        return new DoNothingMove();
    }

    @Override
    public Move apply(SetQuorumEvent e, Table table, Player player){
        return new SetQuorumMove(e.getQuorum());
    }

    @Override
    public Move apply(ConnectEvent e, Table table, Player player){
        Move rval = new DoNothingMove();
        return rval;
    }
}
