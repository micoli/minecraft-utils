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
	public static class MultipleOrientationsrere{
	}
	public static class MultipleOrientations{
		
		/** The angle. */
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
		 * @param angle the angle
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
		 * Sets the cardinal dual orientation.
		 *
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
		 * Gets the angle.
		 *
		 * @return the angle
		 */
		public final int getAngle() {
			return angle;
		}

		/**
		 * Sets the angle.
		 *
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
		try{
			if (iOrientation < 45 + 0 * 90) {
				//player.sendMessage(String.format("1 %d",iOrientation));
				orientations.setCardinalOrientation(CardinalOrientation.N);
				orientations.setCardinalDualOrientation( CardinalDualOrientation.EW);
			} else if (iOrientation < 45 + 1 * 90) {
				//player.sendMessage(String.format("2 %d",iOrientation));
				orientations.setCardinalOrientation( CardinalOrientation.E);
				orientations.setCardinalDualOrientation( CardinalDualOrientation.NS);
			} else if (iOrientation < 45 + 2 * 90) {
				//player.sendMessage(String.format("3 %d",iOrientation));
				orientations.setCardinalOrientation( CardinalOrientation.S);
				orientations.setCardinalDualOrientation( CardinalDualOrientation.EW);
			} else if (iOrientation < 45 + 3 * 90) {
				//player.sendMessage(String.format("4 %d",iOrientation));
				orientations.setCardinalOrientation( CardinalOrientation.W);
				orientations.setCardinalDualOrientation( CardinalDualOrientation.NS);
			}
			//player.sendMessage(String.format("4 %s",orientations.getCardinalDualOrientation().toString()));
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return orientations;
		//logger.log("Facing %s,%s", facing, orientation.toString());
	}
}
