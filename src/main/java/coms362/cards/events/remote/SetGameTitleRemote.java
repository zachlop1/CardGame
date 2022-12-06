package coms362.cards.events.remote;

import coms362.cards.streams.Marshalls;

public class SetGameTitleRemote implements Marshalls {
	
	private String title = "";
	
	public SetGameTitleRemote(String newTitle) {
		title = newTitle;
	}

	public String marshall() {
		return String.format("title='%s';$('#game-title').text(title);document.title=title;", title);
	}

	public String stringify() {
		return "SetGameTitle = "+title;
	}

}
