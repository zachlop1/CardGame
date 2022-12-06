package coms362.cards.app;

import java.io.IOException;
import java.util.Stack;

import coms362.cards.abstractcomp.GameFactory;
import coms362.cards.abstractcomp.Rules;
import coms362.cards.abstractcomp.Table;
import coms362.cards.events.inbound.*;
import coms362.cards.events.remote.CreateButtonRemote;
import coms362.cards.events.remote.HideButtonRemote;
import coms362.cards.fiftytwo.P52DealButton;
import coms362.cards.fiftytwo.P52MultiPlayerButton;
import coms362.cards.fiftytwo.P52NumberPlayersButton;
import coms362.cards.war.WarButton;
import coms362.cards.game.PartyRole;
import coms362.cards.game.PlayerView;
import coms362.cards.model.Location;
import coms362.cards.model.PregameSetup;
import coms362.cards.model.Quorum;
import coms362.cards.streams.InBoundQueue;
import coms362.cards.streams.RemoteTableGateway;

/**
 * This class is responsible for processing events involved in determining what game is to be
 * played. It runs in a context with a single default view.
 * 
 * It defers events that can't be processed without access to concrete game rules: for example how
 * many players are required/allowed, and what position and role each party will hold.
 * 
 * Currently events carrying this and related game/match information can be submitted by the host on
 * * the query string, for example:
 * 
 * ?host&player=1&min=1&max=2&game=PU52MP
 * 
 * The "host" parameter qualifies the connection to select game and specify game "quorum" limits. In
 * this case, the host would also be registered as player 1. The "host" is <em>not</em> required to
 * be a player in all games.
 * 
 * @author Robert Ward
 */
public class GameController {

    private InBoundQueue inQ;
    private RemoteTableGateway remote;
    private GameFactoryFactory abstractFactory;
    private String multiPlayerButtonId = "";
    private String singlePlayerButtonId = "";
    private String warButtonId = "";
    private String TwoPlayerButtonId = "";
    private String ThreePlayerButtonId = "";
    private String FourPlayerButtonId = "";
    Stack<Event> deferred = new Stack<Event>();
    PregameSetup game = new PregameSetup();

    public GameController(InBoundQueue inQ, RemoteTableGateway gateway,
            GameFactoryFactory gFFactory) {
        this.inQ = inQ;
        this.remote = gateway;
        this.abstractFactory = gFFactory;
    }

    public void run() {

        System.out.println("Application Started");

        Event e = null;
        while (!game.isSelected()) {
            try {
                e = inQ.take();
                ((SysEvent) e).accept(this, game);

            } catch (ExitTestException ex) {
                break;
            } catch (Exception ex) {
                System.out.println("GameController Exception " + ex.getMessage());
                System.out.println("Deferring unhandled event " + e.toString());
                ex.printStackTrace(System.err);
                // we can't do anything game specific until after we have seen SelectGame.
                deferred.add(e);
            };
        }

        GameFactory factory = abstractFactory.getGameFactory(game.getSelection());
        Rules rules = factory.createRules();
        Table table = factory.createTable();
        inQ.pushBack(deferred);

        MatchController match = new MatchController(inQ, table, rules, remote, factory);
        match.start();
    }

    // TODO: make specific handlers for each match pre-req event.
    public void apply(ConnectEvent e, PregameSetup game) { // TODO Auto-generated method stub
        System.out.println("GameRules.apply " + e.getClass().getSimpleName());
        System.out.println(e.toString());
        //Create button
        PlayerView view = new PlayerView(0, e.getSocketId(), remote);
        try{
            P52MultiPlayerButton button = new P52MultiPlayerButton("Multi-Player", new Location(150, 250));
            P52DealButton button2 = new P52DealButton("Single-Player", new Location(350, 250));
            WarButton button3 = new WarButton("War", new Location(250, 300));
            multiPlayerButtonId = button.getRemoteId();
            singlePlayerButtonId = button2.getRemoteId();
            warButtonId = button3.getRemoteId();
            view.send(new CreateButtonRemote(button));
            view.send(new CreateButtonRemote(button2));
            view.send(new CreateButtonRemote(button3));
        }catch(IOException e1){
            e1.printStackTrace();
        }
        //Register Handler
        EventUnmarshallers handlers = EventUnmarshallers.getInstance();
        handlers.registerHandler(DealEvent.kId, (Class) DealEvent.class);
        handlers.registerHandler(MultiPlayerEvent.kId, (Class) MultiPlayerEvent.class);
        handlers.registerHandler(WarEvent.kId, (Class) WarEvent.class);

        //Player number selection
        String pnum = null;
        if ((pnum = e.getParam("player")) != null) {
            pnum = (pnum.isEmpty() ? "1" : pnum);
            deferred.push(new NewPartyEvent(PartyRole.player, pnum, e.getSocketId()));
        }
    }

    public void apply(MultiPlayerEvent e, PregameSetup game){
        //Hide button
        PlayerView view = new PlayerView(0, e.getSocketId(), remote);
        try{
            view.send(new HideButtonRemote(multiPlayerButtonId));
            view.send(new HideButtonRemote(singlePlayerButtonId));
            view.send(new HideButtonRemote(warButtonId));
            P52NumberPlayersButton button = new P52NumberPlayersButton("2 Players", new Location(300, 100), TwoPlayersSelectionEvent.kId);
            P52NumberPlayersButton button2 = new P52NumberPlayersButton("3 Players", new Location(300, 200), ThreePlayersSelectionEvent.kId);
            P52NumberPlayersButton button3 = new P52NumberPlayersButton("4 Players", new Location(300, 300), FourPlayersSelectionEvent.kId);
            TwoPlayerButtonId = button.getRemoteId();
            ThreePlayerButtonId = button2.getRemoteId();
            FourPlayerButtonId = button3.getRemoteId();
            view.send(new CreateButtonRemote(button));
            view.send(new CreateButtonRemote(button2));
            view.send(new CreateButtonRemote(button3));
        }catch(IOException e1){
            e1.printStackTrace();
        }
      //Register Handler
        EventUnmarshallers handlers = EventUnmarshallers.getInstance();
        handlers.registerHandler(TwoPlayersSelectionEvent.kId, (Class) TwoPlayersSelectionEvent.class);
        handlers.registerHandler(ThreePlayersSelectionEvent.kId, (Class) ThreePlayersSelectionEvent.class);
        handlers.registerHandler(FourPlayersSelectionEvent.kId, (Class) FourPlayersSelectionEvent.class);
        
        
    }

    public void apply(WarEvent e, PregameSetup game){
        //Hide buttons
        PlayerView view = new PlayerView(0, e.getSocketId(), remote);
        try{
            view.send(new HideButtonRemote(multiPlayerButtonId));
            view.send(new HideButtonRemote(singlePlayerButtonId));
            view.send(new HideButtonRemote(warButtonId));
        }catch(IOException e1){
            e1.printStackTrace();
        }
        Quorum pushQ = new Quorum(1, 1);
        deferred.insertElementAt(new SetQuorumEvent(pushQ), 0);
        //Select game
        String selection = "WAR";
        inQ.pushBack(new SelectGameEvent(selection));
    }

    public void apply(DealEvent e, PregameSetup game){
        //Hide buttons
        PlayerView view = new PlayerView(0, e.getSocketId(), remote);
        try{
            view.send(new HideButtonRemote(multiPlayerButtonId));
            view.send(new HideButtonRemote(singlePlayerButtonId));
            view.send(new HideButtonRemote(warButtonId));
        }catch(IOException e1){
            e1.printStackTrace();
        }
        Quorum pushQ = new Quorum(1, 1);
        deferred.insertElementAt(new SetQuorumEvent(pushQ), 0);
        //Select game
        String selection = "PU52SP";
        inQ.pushBack(new SelectGameEvent(selection));
    }

    public void apply(SelectGameEvent e, PregameSetup game) {
        String selected = "";
        if (abstractFactory.isValidSelection(selected = e.getSelection())) {
            game.setSelected(selected);
        } else {
            // we need to inform the alleged host now
            System.out.format("GameController. SelectGame : %s is not a supported game.", selected);
            inQ.pushBack(new InvalidGameSelectionEvent(selected));
        }

    }

    public void apply(TwoPlayersSelectionEvent e, PregameSetup game){
        //Hide button
        PlayerView view = new PlayerView(0, e.getSocketId(), remote);
        try{
            view.send(new HideButtonRemote(TwoPlayerButtonId));
            view.send(new HideButtonRemote(ThreePlayerButtonId));
            view.send(new HideButtonRemote(FourPlayerButtonId));
            view.send(new HideButtonRemote(warButtonId));
        }catch(IOException e1){
            e1.printStackTrace();
        }
        Quorum pushQ = new Quorum(2, 2);
        deferred.insertElementAt(new SetQuorumEvent(pushQ), 0);
        //Select game
        String selection = "PU52MP";
        inQ.pushBack(new SelectGameEvent(selection));
    }
    
    public void apply(ThreePlayersSelectionEvent e, PregameSetup game){
        //Hide button
        PlayerView view = new PlayerView(0, e.getSocketId(), remote);
        try{
            view.send(new HideButtonRemote(TwoPlayerButtonId));
            view.send(new HideButtonRemote(ThreePlayerButtonId));
            view.send(new HideButtonRemote(FourPlayerButtonId));
            view.send(new HideButtonRemote(warButtonId));
        }catch(IOException e1){
            e1.printStackTrace();
        }
        Quorum pushQ = new Quorum(3, 3);
        deferred.insertElementAt(new SetQuorumEvent(pushQ), 0);
        //Select game
        String selection = "PU52MP";
        inQ.pushBack(new SelectGameEvent(selection));
    }
    
    public void apply(FourPlayersSelectionEvent e, PregameSetup game){
        //Hide button
        PlayerView view = new PlayerView(0, e.getSocketId(), remote);
        try{
            view.send(new HideButtonRemote(TwoPlayerButtonId));
            view.send(new HideButtonRemote(ThreePlayerButtonId));
            view.send(new HideButtonRemote(FourPlayerButtonId));
            view.send(new HideButtonRemote(warButtonId));
        }catch(IOException e1){
            e1.printStackTrace();
        }
        Quorum pushQ = new Quorum(4, 4);
        deferred.insertElementAt(new SetQuorumEvent(pushQ), 0);
        //Select game
        String selection = "PU52MP";
        inQ.pushBack(new SelectGameEvent(selection));
    }
    
    public void apply(InvalidGameSelectionEvent e, PregameSetup game) {
        System.out.println("InvalidGameSelection Event");
        try {
            remote.send(new SystemStatus(e.getMsg()), "default");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void apply(NewPartyEvent e, PregameSetup game) {
        deferred.add(e);
    }

    // should not normally be processed from game controller.

 /**
     * Handles the end of the game
     * @param endPlay
     * @param game2
 */

    public void apply(EndPlayEvent endPlay, PregameSetup game2) {
        throw new ExitTestException("Exit on EndPlay Event");
    }

}
