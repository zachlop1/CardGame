package coms362.cards.fiftytwo;

import coms362.cards.events.inbound.DealEvent;
import coms362.cards.model.Button;
import coms362.cards.model.Location;


public class P52DealButton extends Button {
	public static final String kSelector = "dealButton";

	public P52DealButton(String label, Location location) {
		super(kSelector, DealEvent.kId, label, location);
	}	

}
