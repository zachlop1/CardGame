package coms362.cards.app;

import coms362.cards.abstractcomp.Move;
import coms362.cards.abstractcomp.Table;

public class MockMove implements Move {
	int countApplyTable = 0;
	int countApplyView = 0;

	@Override
	public void apply(Table table) {
		((MockTableBase)table).apply(this);
		countApplyTable++;
	}

	@Override
	public void apply(ViewFacade views) {
		countApplyView++;
	}

	public int getCountApplyTable() {
		return countApplyTable;
	}

	public int getCountApplyView() {
		return countApplyView;
	}
}
