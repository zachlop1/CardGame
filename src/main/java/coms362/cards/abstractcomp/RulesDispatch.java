package coms362.cards.abstractcomp;

import coms362.cards.events.inbound.*;

/**
 * Part of the double dispatch mechanism we use to recover concrete type information from Event
 * subclasses.
 * 
 * This declaration segregates the double dispatch portion of the rules api from the unrelated (and
 * more game independent) methods.
 * 
 * Since we anticipate that a material number of input events will be reusable across games,
 * defining a separate interface for these methods allows us to reuse those events without change,
 * even though the ultimate implementor (Rules) will change with each game.
 * 
 * The base implementation (which for every one of these methods just throws an "unimplemented"
 * exception) must also be expanded each time a new Event is added. However the combination
 * insulates existing game-specific rules objects from needing to be changes and allows each new
 * game-specific rules object to define methods for only the events it requires.
 * 
 * @author Robert Ward
 */
public interface RulesDispatch {

    public Move apply(CardEvent e, Table table, Player player);

    public Move apply(DealEvent e, Table table, Player player);

    public Move apply(EndPlayEvent e, Table table, Player player);

    public Move apply(InitGameEvent e, Table table, Player player);

    public Move apply(SelectGameEvent e, Table table, Player player);

    public Move apply(GameRestartEvent e, Table table, Player player);

    public Move apply(NewPartyEvent e, Table table, Player player);

    public Move apply(ConnectEvent e, Table table, Player player);

    public Move apply(SetQuorumEvent e, Table table, Player player);

    public Move apply(MultiPlayerEvent e, Table table, Player player);
    
    public Move apply(TwoPlayersSelectionEvent e, Table table, Player player);
    
    public Move apply(ThreePlayersSelectionEvent e, Table table, Player player);
    
    public Move apply(FourPlayersSelectionEvent e, Table table, Player player);

    public Move apply(WarEvent e, Table table, Player player);

}
