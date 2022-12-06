package coms362.cards.fiftytwo;

import coms362.cards.events.inbound.MultiPlayerEvent;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class P52MultiPlayerButton extends Button {
    public static final String kSelector = "multiPlayerButton";

    public P52MultiPlayerButton(String label, Location location) {
        super(kSelector, MultiPlayerEvent.kId, label, location);
    }

}
