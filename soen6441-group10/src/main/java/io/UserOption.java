package io;

public enum UserOption {
	
	SAVE("s"),
	LOAD("l"),
	GAME_STATUS("o"),
	QUIT("q"),
	NEW_GAME("n"),
	EXIT("e"),
	NEXT_TURN("t"),
	BACK(""),
	YES("y"),
	NO("n");
	
	private final String option;
	
	private UserOption(String option_) {
		option = option_;
	}
	
	public String getOptionString() {
		return option;
	}

}
