package org.micoli.minecraft.utils;

import org.bukkit.entity.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class QDOrientation.
 */
public class QDOrientation {
	
	/**
	 * The Enum CardinalDualOrientation.
	 */
	public enum CardinalDualOrientation {
		
		/** The NS. */
		NS,
		
		/** The EW. */
		EW
	}
	
	/**
	 * The Enum CardinalOrientation.
	 */
	public enum CardinalOrientation{
		
		/** The N. */
		N,
		
		/** The E. */
		E,
		
		/** The S. */
		S,
		
		/** The W. */
		W
	}
	
	/**
	 * The Class MultipleOrientation.
	 */
	public static class MultipleOrientations{
		
		private int angle=0;
		
		/** The cardinal dual orientation. */
		private CardinalDualOrientation cardinalDualOrientation;
		
		/** The cardinal orientation. */
		private CardinalOrientation cardinalOrientation;
		
		/**
		 * Instantiates a new multiple orientation.
		 *
		 * @param cardinalDualorientation the cardinal dualorientation
		 * @param cardinalOrientation the cardinal orientation
		 */
		public MultipleOrientations(CardinalDualOrientation cardinalDualorientation,CardinalOrientation cardinalOrientation,int angle){
			this.cardinalDualOrientation=cardinalDualorientation;
			this.cardinalOrientation=cardinalOrientation;
			this.angle = angle;
		}
		
		/**
		 * Gets the cardinal dual orientation.
		 *
		 * @return the cardinalDualorientation
		 */
		public final CardinalDualOrientation getCardinalDualOrientation() {
			return cardinalDualOrientation;
		}
		
		/**
		 * @param cardinalDualOrientation the cardinalDualOrientation to set
		 */
		public final void setCardinalDualOrientation(CardinalDualOrientation cardinalDualOrientation) {
			this.cardinalDualOrientation = cardinalDualOrientation;
		}

		/**
		 * Gets the cardinal orientation.
		 *
		 * @return the cardinalOrientation
		 */
		public final CardinalOrientation getCardinalOrientation() {
			return cardinalOrientation;
		}
		
		/**
		 * Sets the cardinal orientation.
		 *
		 * @param cardinalOrientation the cardinalOrientation to set
		 */
		public final void setCardinalOrientation(CardinalOrientation cardinalOrientation) {
			this.cardinalOrientation = cardinalOrientation;
		}

		/**
		 * @return the angle
		 */
		public final int getAngle() {
			return angle;
		}

		/**
		 * @param angle the angle to set
		 */
		public final void setAngle(int angle) {
			this.angle = angle;
		}
	}
	
	/**
	 * Gets the orientation.
	 *
	 * @param player the player
	 * @return the orientation
	 */
	public static MultipleOrientations getOrientations(Player player){
		int iOrientation = (int) (player.getLocation().getYaw() + 180) % 360;
		MultipleOrientations orientations = new MultipleOrientations(CardinalDualOrientation.NS,CardinalOrientation.N,iOrientation);
		if (iOrientation < 45 + 0 * 90) {
			orientations.setCardinalOrientation(CardinalOrientation.N);
			orientations.setCardinalDualOrientation( CardinalDualOrientation.EW);
		} else if (iOrientation < 45 + 1 * 90) {
			orientations.setCardinalOrientation( CardinalOrientation.E);
			orientations.setCardinalDualOrientation( CardinalDualOrientation.NS);
		} else if (iOrientation < 45 + 2 * 90) {
			orientations.setCardinalOrientation( CardinalOrientation.S);
			orientations.setCardinalDualOrientation( CardinalDualOrientation.EW);
		} else if (iOrientation < 45 + 3 * 90) {
			orientations.setCardinalOrientation( CardinalOrientation.W);
			orientations.setCardinalDualOrientation( CardinalDualOrientation.NS);
		}
		return orientations;
		//logger.log("Facing %s,%s", facing, orientation.toString());
	}
}
