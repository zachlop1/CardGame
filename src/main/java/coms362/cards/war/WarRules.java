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
import coms362.cards.war.WarCardMove;

public class WarRules extends RulesDispatchBase
        implements Rules, RulesDispatch {

    public static final String DROP_PILE = "randomPile";
    public static final String DROP_PILE2 = "randomPile2";
    public static final String PICKUP_PILE = "discardPile";
    public static final String PICKUP_PILE2 = "discardPile2";

    public WarRules() {
        registerEvents();
    }

    public Move eval(Event nextE, Table table, Player player) {
        return nextE.dispatch(this, table, player);
    }

    public Move apply(CardEvent e, Table table, Player player){
    	Pile fromPile;
    	Pile toPile;
    	if(player.getPlayerNum()%2 == 0) {
    		fromPile = table.getPile(DROP_PILE);
            toPile = table.getPile(PICKUP_PILE);
    	} else {
    		fromPile = table.getPile(DROP_PILE2);
            toPile = table.getPile(PICKUP_PILE2);
    	}  
        Card c = fromPile.getCard(e.getId());
        if (c == null) {
            return new DoNothingMove();
        }
        return new WarCardMove(c, player, fromPile, toPile);
    }

    public Move apply(DealEvent e, Table table, Player player){
        return new WarDealMove(table, player);
    }

    public Move apply(InitGameEvent e, Table table, Player player){
        return new WarInitMove(table.getPlayerMap(), "War Multiplayer", table);
    }

    public Move apply(NewPartyEvent e, Table table, Player player){
        if (e.getRole() == PartyRole.player){
            return new CreatePlayerMove( e.getPosition(), e.getSocketId());
        }
        return new DoNothingMove();
    }

    public Move apply(SetQuorumEvent e, Table table, Player player){
        return new SetQuorumMove(e.getQuorum());
    }

    public Move apply(ConnectEvent e, Table table, Player player){
        Move rval = new DoNothingMove();
        System.out.println("Rules apply ConnectEvent "+e);
        if (! table.getQuorum().exceeds(table.getPlayers().size()+1)){
            if (e.getRole() == PartyRole.player){
                rval =  new CreatePlayerMove( e.getPosition(), e.getSocketId());
            }
        }
        System.out.println("PickupRules connectHandler rval = "+rval);
        return rval;
    }
    public Move battle_win(CardEvent e,Player p1, Player p2, Card player1card, Table table,
    		Card player2card){
    	Pile toPile;
    	Pile fromPile;
    	
        if(player1card.getRank() > player2card.getRank()) {
        	 System.out.println("Player 1 wins the battle!");
             fromPile = table.getPile(PICKUP_PILE);
             toPile = table.getPile(DROP_PILE);
             Card c = fromPile.getCard(e.getId());
             if (c == null) {
                 return new DoNothingMove();
             }
        	 return new WarCardMove(c, p1, fromPile, toPile);
        }
        else {
        	System.out.println("Player 2 wins the battle!");
            fromPile = table.getPile(PICKUP_PILE2);
            toPile = table.getPile(DROP_PILE2);
            Card c = fromPile.getCard(e.getId());
            if (c == null) {
                return new DoNothingMove();
            }
       	 	return new WarCardMove(c, p2, fromPile, toPile);
        }
    }
    public Move collect_other_pile(Table table, Player Winner, CardEvent e) {
    	Pile fromPile;
    	Pile toPile;
    	if(Winner.getPlayerNum()%2 == 0) {
    		toPile = table.getPile(PICKUP_PILE2);
            fromPile = table.getPile(DROP_PILE);
    	} else {
    		toPile = table.getPile(PICKUP_PILE);
            fromPile = table.getPile(DROP_PILE2);
    	} 
        Card c = fromPile.getCard(e.getId());
        if (c == null) {
            return new DoNothingMove();
        }
        return new WarCardMove(c, Winner, fromPile, toPile);
    }
    

    /**
     * We rely on Rules to register the appropriate input events with
     * the unmarshaller. This avoids excessive complexity in the
     * abstract factory and there is a natural dependency between
     * the rules and the game input events.
     */
    private void registerEvents() {
        EventUnmarshallers handlers = EventUnmarshallers.getInstance();
        handlers.registerHandler(InitGameEvent.kId, (Class) InitGameEvent.class);
        handlers.registerHandler(DealEvent.kId, (Class) DealEvent.class);
        handlers.registerHandler(CardEvent.kId, (Class) CardEvent.class);
        handlers.registerHandler(GameRestartEvent.kId, (Class) GameRestartEvent.class);
        handlers.registerHandler(NewPartyEvent.kId, (Class) NewPartyEvent.class);
    }
}
