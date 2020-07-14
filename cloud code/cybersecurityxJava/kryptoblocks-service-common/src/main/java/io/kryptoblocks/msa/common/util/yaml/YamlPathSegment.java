package io.kryptoblocks.msa.common.util.yaml;

// TODO: Auto-generated Javadoc
/**
 * The Class YamlPathSegment.
 */
public abstract class YamlPathSegment {

	/**
	 * Decode.
	 *
	 * @param code the code
	 * @return the yaml path segment
	 */
	public static YamlPathSegment decode(String code) {
		switch (code.charAt(0)) {
		case '*':
			return anyChild();
		case '.':
			return valueAt(code.substring(1));
		case '&':
			return keyAt(code.substring(1));
		case '[':
			return valueAt(Integer.parseInt(code.substring(1)));
		default:
			throw new IllegalArgumentException("Can't decode YamlPathSegment from '"+code+"'");
		}
	}

	/**
	 * The Enum YamlPathSegmentType.
	 */
	public static enum YamlPathSegmentType {
		
		/** The val at key. */
		VAL_AT_KEY, 
 /** The key at key. */
 //Go to value associate with given key in a map.
		KEY_AT_KEY, 
 /** The val at index. */
 //Go to the key node associated with a given key in a map.
		VAL_AT_INDEX, 
 /** The any child. */
 //Go to value associate with given index in a sequence
		ANY_CHILD // Go to any child (assumes you are using ambiguous traversal method, otherwise this is probably not very useful)
	}

	/**
	 * The Class AnyChild.
	 */
	public static class AnyChild extends YamlPathSegment {

		/** The instance. */
		static AnyChild INSTANCE = new AnyChild();

		/**
		 * Instantiates a new any child.
		 */
		private AnyChild() {}

		/**
		 * To nav string.
		 *
		 * @return the string
		 */
		@Override
		public String toNavString() {
			return ".*";
		}

		/**
		 * To prop string.
		 *
		 * @return the string
		 */
		@Override
		public String toPropString() {
			return "*";
		}

		/**
		 * To index.
		 *
		 * @return the integer
		 */
		@Override
		public Integer toIndex() {
			return null;
		}

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		@Override
		public YamlPathSegmentType getType() {
			return YamlPathSegmentType.ANY_CHILD;
		}

		/**
		 * Gets the type code.
		 *
		 * @return the type code
		 */
		@Override
		protected char getTypeCode() {
			return '*';
		};

		/**
		 * Gets the value code.
		 *
		 * @return the value code
		 */
		@Override
		protected String getValueCode() {
			return "";
		}

	}

	/**
	 * The Class AtIndex.
	 */
	public static class AtIndex extends YamlPathSegment {

		/** The index. */
		private int index;

		/**
		 * Instantiates a new at index.
		 *
		 * @param index the index
		 */
		public AtIndex(int index) {
			this.index = index;
		}

		/**
		 * To nav string.
		 *
		 * @return the string
		 */
		@Override
		public String toNavString() {
			return "["+index+"]";
		}

		/**
		 * To prop string.
		 *
		 * @return the string
		 */
		@Override
		public String toPropString() {
			return "["+index+"]";
		}

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		@Override
		public YamlPathSegmentType getType() {
			return YamlPathSegmentType.VAL_AT_INDEX;
		}

		/**
		 * To index.
		 *
		 * @return the integer
		 */
		@Override
		public Integer toIndex() {
			return index;
		}

		/**
		 * Hash code.
		 *
		 * @return the int
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + index;
			return result;
		}

		/**
		 * Equals.
		 *
		 * @param obj the obj
		 * @return true, if successful
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AtIndex other = (AtIndex) obj;
			if (index != other.index)
				return false;
			return true;
		}

		/**
		 * Gets the type code.
		 *
		 * @return the type code
		 */
		@Override
		protected char getTypeCode() {
			return '[';
		}

		/**
		 * Gets the value code.
		 *
		 * @return the value code
		 */
		@Override
		protected String getValueCode() {
			return ""+index;
		}
	}

	/**
	 * The Class ValAtKey.
	 */
	public static class ValAtKey extends YamlPathSegment {

		/** The key. */
		private String key;

		/**
		 * Instantiates a new val at key.
		 *
		 * @param key the key
		 */
		public ValAtKey(String key) {
			this.key = key;
		}

		/**
		 * To nav string.
		 *
		 * @return the string
		 */
		@Override
		public String toNavString() {
			if (key.indexOf('.')>=0) {
				//TODO: what if key contains '[' or ']'??
				return "["+key+"]";
			}
			return "."+key;
		}

		/**
		 * To prop string.
		 *
		 * @return the string
		 */
		@Override
		public String toPropString() {
			//Don't start with a '.' if trying to build a 'self contained' expression.
			return key;
		}

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		@Override
		public YamlPathSegmentType getType() {
			return YamlPathSegmentType.VAL_AT_KEY;
		}

		/**
		 * To index.
		 *
		 * @return the integer
		 */
		@Override
		public Integer toIndex() {
			return null;
		}

		/**
		 * Hash code.
		 *
		 * @return the int
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		/**
		 * Equals.
		 *
		 * @param obj the obj
		 * @return true, if successful
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ValAtKey other = (ValAtKey) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		/**
		 * Gets the type code.
		 *
		 * @return the type code
		 */
		@Override
		protected char getTypeCode() {
			return '.';
		}

		/**
		 * Gets the value code.
		 *
		 * @return the value code
		 */
		@Override
		protected String getValueCode() {
			return key;
		}
	}

	/**
	 * The Class KeyAtKey.
	 */
	public static class KeyAtKey extends ValAtKey {

		/**
		 * Instantiates a new key at key.
		 *
		 * @param key the key
		 */
		public KeyAtKey(String key) {
			super(key);
		}

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		@Override
		public YamlPathSegmentType getType() {
			return YamlPathSegmentType.KEY_AT_KEY;
		}

		/**
		 * Gets the type code.
		 *
		 * @return the type code
		 */
		@Override
		protected char getTypeCode() {
			return '&';
		}

	}

	/**
	 * Can empty.
	 *
	 * @return true, if successful
	 */
	public boolean canEmpty() {
		//All path segments implement a real 'one step' movement,
		return false;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return toNavString();
	}

	/**
	 * To nav string.
	 *
	 * @return the string
	 */
	public abstract String toNavString();
	
	/**
	 * To prop string.
	 *
	 * @return the string
	 */
	public abstract String toPropString();

	/**
	 * To index.
	 *
	 * @return the integer
	 */
	public abstract Integer toIndex();
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public abstract YamlPathSegmentType getType();

	/**
	 * Value at.
	 *
	 * @param key the key
	 * @return the yaml path segment
	 */
	public static YamlPathSegment valueAt(String key) {
		return new ValAtKey(key);
	}
	
	/**
	 * Value at.
	 *
	 * @param index the index
	 * @return the yaml path segment
	 */
	public static YamlPathSegment valueAt(int index) {
		return new AtIndex(index);
	}
	
	/**
	 * Key at.
	 *
	 * @param key the key
	 * @return the yaml path segment
	 */
	public static YamlPathSegment keyAt(String key) {
		return new KeyAtKey(key);
	}

	/**
	 * Any child.
	 *
	 * @return the yaml path segment
	 */
	public static YamlPathSegment anyChild() {
		return AnyChild.INSTANCE;
	}

	/**
	 * Encode.
	 *
	 * @return the string
	 */
	public String encode() {
		return getTypeCode() + getValueCode();
	}


	/**
	 * Gets the value code.
	 *
	 * @return the value code
	 */
	protected abstract String getValueCode();

	/**
	 * Gets the type code.
	 *
	 * @return the type code
	 */
	protected abstract char getTypeCode();

}