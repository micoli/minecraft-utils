package org.micoli.minecraft.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonExclusionStrategy.
 */
public class JsonExclusionStrategy implements ExclusionStrategy {
	
	/* (non-Javadoc)
	 * @see com.google.gson.ExclusionStrategy#shouldSkipClass(java.lang.Class)
	 */
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.google.gson.ExclusionStrategy#shouldSkipField(com.google.gson.FieldAttributes)
	 */
	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return (f.getName().equalsIgnoreCase("_ebean_intercept")||f.getName().equalsIgnoreCase("_EBEAN_MARKER"));
	}

}
