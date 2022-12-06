package coms362.cards.war;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.events.remote.CreateCardRemote;
import coms362.cards.events.remote.HideButtonRemote;
import coms362.cards.events.remote.UpdateCardRemote;
import coms362.cards.fiftytwo.P52DealButton;
import coms362.cards.model.Card;
import coms362.cards.model.Pile;

public class WarBattleDealMove extends WarDealMove {

    public WarBattleDealMove(Table table, Player player) {
    	super(table, player);
    }

    public void apply(Table table) {
    	// TODO 
    }

    public void apply(ViewFacade views) {

        try {
            String remoteId = views.getRemoteId(WarButton.kSelector);
            views.send(new HideButtonRemote(remoteId));
            Pile local = table.getPile(WarBattleRules.BATTLE_DROP_PILE);
            Pile local2 = table.getPile(WarBattleRules.BATTLE_DROP_PILE2);
            if (local == null) {
                return;
            }
            for (int i = 0; i < 3; i++) {
            	Card c = local.getCards().iterator().next();
            	String outVal = "";
                views.send(new CreateCardRemote(c));
                views.send(new UpdateCardRemote(c));
                System.out.println(outVal);
            }
            for (int i = 0; i < 3; i++) {
            	Card c = local2.getCards().iterator().next();
            	String outVal = "";
                views.send(new CreateCardRemote(c));
                views.send(new UpdateCardRemote(c));
                System.out.println(outVal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

