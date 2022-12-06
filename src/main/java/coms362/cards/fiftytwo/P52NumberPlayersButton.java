package coms362.cards.fiftytwo;

import coms362.cards.events.inbound.*;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class P52NumberPlayersButton extends Button {
    public static final String kSelector = "numberPlayersButton";

    public P52NumberPlayersButton(String label, Location location, String kId) {
        super(kSelector, kId, label, location);
    }

}
