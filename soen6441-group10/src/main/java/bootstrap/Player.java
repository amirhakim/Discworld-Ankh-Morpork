/**
 * @File
 * Player class representing human player of game
 */
package bootstrap;

public class Player {
	
		private String name;
		private Card personality;
	
	//Constructor
	Player(){
	}
	
	//Sets player name for the current object.
	//Returns true for a valid name otherwise returns false.
	boolean setName(String name){
		//Checks for a valid name. Valid name contains only letter.
		if(isAlpha(name)){
			this.name=name;
			return true;
		}
		else{
			return false;
		}		
	}

	private boolean isAlpha(String name) {
		//TODO this needs to allow numbers
		return name.matches("^[a-zA-Z0-9_]*$");
	}
	
	//Returns name value
	String getName(){
		return this.name;
	}
	
	void setPersonality(Card personality){
		this.personality = personality;
	}
	
	Card getPersonality() {
		return this.personality;
	}
	
	void turn() {
		System.out.println(this.getName() + " turn");
	}
}
