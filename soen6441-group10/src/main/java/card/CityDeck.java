/**
 * 
 */
package card;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Amir
 *
 */
public class CityDeck {

	private final int size = 12;

	private transient final String[] names = { "The Shades", "Dolly Sisters",
			"The Hippo", "Dragon's Landing", "Ils of Gods", "The Scoures",
			"Small Gods", "Dimwell", "Nap Hill", "Seven Sleepers",
			"Unreal Estate", "Longwall" };

	private List<CityCard> cards;

	public CityDeck() {
		populateDeck();
	}

	public CityCard getCard(String title) {
		for (CityCard c : this.cards) {
			if (c.getTitle().equals(title)) {
				return c;
			}
		}
		return null;
	}

	public List<CityCard> getCards() {
		return this.cards;
	}

	public void populateDeck() {
		// TODO Auto-generated method stub
		this.cards = new ArrayList<CityCard>();
		for (int i = 0; i < this.size; ++i) {
			CityCard tmp = new CityCard();
			tmp.setTitle(this.names[i]);
			this.cards.add(tmp);
		}

		CityCard TheShades = this.getCard("The Shades");
		TheShades.setBuildingCost(6);
		CityCard DollySisters = this.getCard("Dolly Sisters");
		DollySisters.setBuildingCost(6);
		CityCard TheHippo = this.getCard("The Hippo");
		TheHippo.setBuildingCost(12);
		CityCard DragonsLanding = this.getCard("Dragon's Landing");
		DragonsLanding.setBuildingCost(12);
		CityCard IlsofGods = this.getCard("Ils of Gods");
		IlsofGods.setBuildingCost(12);
		CityCard TheScoures = this.getCard("The Scoures");
		TheScoures.setBuildingCost(6);
		CityCard SmallGods = this.getCard("Small Gods");
		SmallGods.setBuildingCost(18);
		CityCard Dimwell = this.getCard("Dimwell");
		Dimwell.setBuildingCost(6);
		CityCard NapHill = this.getCard("Nap Hill");
		NapHill.setBuildingCost(12);
		CityCard SevenSleepers = this.getCard("Seven Sleepers");
		SevenSleepers.setBuildingCost(18);
		CityCard UnrealEstate = this.getCard("Unreal Estate");
		UnrealEstate.setBuildingCost(18);
		CityCard Longwall = this.getCard("Longwall");
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
