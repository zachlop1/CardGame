package coms362.cards.war;

import coms362.cards.abstractcomp.GameFactory;
import coms362.cards.abstractcomp.Player;
import coms362.cards.abstractcomp.Rules;
import coms362.cards.abstractcomp.Table;
import coms362.cards.abstractcomp.View;
import coms362.cards.abstractcomp.ViewFactory;
import coms362.cards.game.PartyRole;
import coms362.cards.game.PlayerView;
import coms362.cards.model.PlayerFactory;
import coms362.cards.model.TableBase;
import coms362.cards.streams.RemoteTableGateway;

public class WarGameFactory implements GameFactory, PlayerFactory, ViewFactory {

    @Override
    public Rules createRules() {
        return new WarRules();
    }

    @Override
    public Table createTable() {
        return new TableBase(this);
    }

    @Override
    public View createView(PartyRole role, Integer num, String socketId, RemoteTableGateway gw ) {
        return new PlayerView(num, socketId, gw);
    }

    @Override
    public Player createPlayer( Integer position, String socketId) {
        return new WarPlayer(position, socketId);
    }

    @Override
    public PlayerFactory createPlayerFactory() {
        return this;
    }

}
