package coms362.cards.app;

import coms362.cards.abstractcomp.Move;
import coms362.cards.model.PlayerFactory;
import coms362.cards.model.TableBase;

public class MockTableBase extends TableBase {
	private boolean isMoveApplied = false;
	private int applyMoveCount = 0;
	
	public MockTableBase(PlayerFactory pFactory) {
		super(pFactory);
	}
	
	@Override
	public void apply(Move move) {
		isMoveApplied = true;
		applyMoveCount++;
	}

	@Override
	public boolean partiesReady() {
		return isMoveApplied;
	}
	
	public int getApplyMoveCount() {
		return applyMoveCount;
	}
}
