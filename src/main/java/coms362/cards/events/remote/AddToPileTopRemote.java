package coms362.cards.events.remote;

import coms362.cards.model.Card;
import coms362.cards.model.Pile;
import coms362.cards.streams.Marshalls;

public class AddToPileTopRemote implements Marshalls {
	private Pile p;
	private Card c;

	public AddToPileTopRemote(Pile p, Card c) {
		this.p = p;
		this.c = c;
	}

	public String marshall() {
	    return String.format(
                "pile = cards362.getById(\'%s\');"
                + "pile.addCard(cards362.getById('%s'));\n"
                + "pile.render();\n", 
                p.getRemoteId(),
                c.getRemoteId()
        );
	}

	public String stringify() {
		return String.format("AddToPileRemote p=%s, c=%d", p.getRemoteId(), c.getRemoteId());
	}

}
