package coms362.cards.abstractcomp;

import coms362.cards.app.ExitTestException;
import coms362.cards.events.inbound.*;

public class RulesDispatchBase implements RulesDispatch {

	@Override
	public Move apply(CardEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(DealEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(EndPlayEvent e, Table table, Player player) {
		throw new ExitTestException("Exit on EndPlay Event");
	}

	@Override
	public Move apply(InitGameEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(SelectGameEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(GameRestartEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(NewPartyEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(ConnectEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(SetQuorumEvent e, Table table, Player player) {
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(MultiPlayerEvent e, Table table, Player player){
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(TwoPlayersSelectionEvent e, Table table, Player player){
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(ThreePlayersSelectionEvent e, Table table, Player player){
		throw new RuntimeException("Event not supported " + e.toString());
	}
	
	@Override
	public Move apply(FourPlayersSelectionEvent e, Table table, Player player){
		throw new RuntimeException("Event not supported " + e.toString());
	}

	@Override
	public Move apply(WarEvent e, Table table, Player player){
		throw new RuntimeException("Event not supported " + e.toString());
	}

}
