package coms362.cards.war;

import coms362.cards.events.inbound.DealEvent;
import coms362.cards.events.inbound.WarEvent;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class WarButton extends Button {
    public static final String kSelector = "warButton";

    public WarButton(String label, Location location) {
        super(kSelector, WarEvent.kId, label, location);
    }

}
