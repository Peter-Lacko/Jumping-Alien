package jumpingalien.model;

public enum GeoFeature {

	AIR {
		
		/**
		 * Return the symbol representing the male gender.
	     *
		 * @return The symbol with unicode U+2642.
		 *       | result == '\u2642'
		 */
		public int getValue() {
			return 0;
		}
	},
	
	GROUND {
		
		/**
		 * Return the symbol representing the male gender.
	     *
		 * @return The symbol with unicode U+2642.
		 *       | result == '\u2642'
		 */
		public int getValue() {
			return 1;
		}
	},
	
	WATER {
		
		/**
		 * Return the symbol representing the male gender.
	     *
		 * @return The symbol with unicode U+2642.
		 *       | result == '\u2642'
		 */
		public int getValue() {
			return 2;
		}
	},
	
	MAGMA {
		
		/**
		 * Return the symbol representing the male gender.
	     *
		 * @return The symbol with unicode U+2642.
		 *       | result == '\u2642'
		 */
		public int getValue() {
			return 3;
		}
	};
	
	public abstract int getValue();
}
