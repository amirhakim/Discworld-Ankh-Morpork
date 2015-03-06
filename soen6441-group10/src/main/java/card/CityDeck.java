/**
 * 
 */
package card;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the areas and the deck of city area cards of the game. 
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class CityDeck {

	private final int size = 12;

	private transient final String[] names = { "The Shades", "Dolly Sisters",
			"The Hippo", "Dragon's Landing", "Ils of Gods", "The Scoures",
			"Small Gods", "Dimwell", "Nap Hill", "Seven Sleepers",
			"Unreal Estate", "Longwall" };

	private List<Area> cards;

	
	public CityDeck() {
		populateDeck();
	}

	/**
	 * This method gets the city card related to the area
	 * @param title
	 * @return
	 */
	public Area getCard(String title) {
		for (Area c : this.cards) {
			if (c.getTitle().equals(title)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Get city area cards
	 * @return city area cards
	 */
	public List<Area> getCards() {
		return this.cards;
	}

	/**
	 * This method puts all city area cards in the deck.
	 */
	public void populateDeck() {
		
		this.cards = new ArrayList<Area>();
		for (int i = 0; i < this.size; ++i) {
			Area tmp = new Area();
			tmp.setTitle(this.names[i]);
			this.cards.add(tmp);
		}

		Area TheShades = this.getCard("The Shades");
		TheShades.setBuildingCost(6);
		Area DollySisters = this.getCard("Dolly Sisters");
		DollySisters.setBuildingCost(6);
		Area TheHippo = this.getCard("The Hippo");
		TheHippo.setBuildingCost(12);
		Area DragonsLanding = this.getCard("Dragon's Landing");
		DragonsLanding.setBuildingCost(12);
		Area IlsofGods = this.getCard("Ils of Gods");
		IlsofGods.setBuildingCost(12);
		Area TheScoures = this.getCard("The Scoures");
		TheScoures.setBuildingCost(6);
		Area SmallGods = this.getCard("Small Gods");
		SmallGods.setBuildingCost(18);
		Area Dimwell = this.getCard("Dimwell");
		Dimwell.setBuildingCost(6);
		Area NapHill = this.getCard("Nap Hill");
		NapHill.setBuildingCost(12);
		Area SevenSleepers = this.getCard("Seven Sleepers");
		SevenSleepers.setBuildingCost(18);
		Area UnrealEstate = this.getCard("Unreal Estate");
		UnrealEstate.setBuildingCost(18);
		Area Longwall = this.getCard("Longwall");
		Longwall.setBuildingCost(12);

		TheShades.addNeighbour(Dimwell, true);
		TheShades.addNeighbour(TheScoures, true);
		TheShades.addNeighbour(TheHippo, true);

		Dimwell.addNeighbour(TheScoures, true);
		Dimwell.addNeighbour(Longwall, true);

		Longwall.addNeighbour(IlsofGods, true);
		Longwall.addNeighbour(SevenSleepers, true);

		SevenSleepers.addNeighbour(NapHill, true);
		SevenSleepers.addNeighbour(UnrealEstate, true);
		SevenSleepers.addNeighbour(IlsofGods, true);

		NapHill.addNeighbour(UnrealEstate, true);
		NapHill.addNeighbour(DollySisters, true);

		UnrealEstate.addNeighbour(IlsofGods, true);
		UnrealEstate.addNeighbour(DollySisters, true);
		UnrealEstate.addNeighbour(DragonsLanding, true);
		UnrealEstate.addNeighbour(SmallGods, true);

		DollySisters.addNeighbour(DragonsLanding, true);

		DragonsLanding.addNeighbour(SmallGods, true);

		SmallGods.addNeighbour(IlsofGods, true);
		SmallGods.addNeighbour(TheScoures, true);
		SmallGods.addNeighbour(TheHippo, true);

		TheScoures.addNeighbour(IlsofGods, true);
		TheScoures.addNeighbour(TheHippo, true);

	}

}
