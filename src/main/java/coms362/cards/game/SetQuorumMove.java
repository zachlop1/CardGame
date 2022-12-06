package coms362.cards.game;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Table;
import coms362.cards.app.ViewFacade;
import coms362.cards.model.Quorum;

public class SetQuorumMove implements Move {

    private Quorum quorum;

    public SetQuorumMove(Quorum quorum) {
        this.quorum = quorum;
    }

    @Override
    public void apply(Table table) {
        table.setQuorum(quorum);
    }

    @Override
    public void apply(ViewFacade view) {
        // TODO Auto-generated method stub
    }

}
