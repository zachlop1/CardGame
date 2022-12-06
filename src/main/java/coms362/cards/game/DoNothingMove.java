package coms362.cards.game;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;

/**
 * A command that does nothing.
 */
public class DoNothingMove implements Move {

	public void apply(Table table) {
	}

	public void apply(ViewFacade view) {
	}

}
