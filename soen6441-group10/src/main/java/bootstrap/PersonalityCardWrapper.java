/**
 * Card continaining information about all the personalities in the game
 */

package bootstrap;

import card.PersonalityCard;

public class PersonalityCardWrapper extends CardWrapper<PersonalityCard> {
	
	public PersonalityCardWrapper(PersonalityCard card) {
		super(card);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((super.card == null) ? 0 : super.card.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PersonalityCardWrapper)) {
			return false;
		}
		PersonalityCardWrapper other = (PersonalityCardWrapper) obj;
		if (super.card == null) {
			if (other.getCard() != null) {
				return false;
			}
		} else if (!super.card.equals(other.getCard())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PersonalityCard [title=").append(super.card).append("]");
		return builder.toString();
	}

}
