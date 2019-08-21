package ldh.common.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class IncludeField implements ExclusionStrategy{

	private String[] fieldNames;
	private Class<?> clazz = null;
	
	public IncludeField(String...fieldNames) {
		this.fieldNames = fieldNames;
	}
	
	public IncludeField(Class<?> clazz, String...fieldNames) {
		this.fieldNames = fieldNames;
		this.clazz = clazz;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if (f != null) {
			System.out.println("f: " + f.getDeclaredClass());
			if (clazz == null) {
				for (String fieldName : fieldNames) {
					if (f.getName().equals(fieldName)) {
						return false;
					}
				}
			} else {
				Class<?> currentClazz = f.getDeclaringClass();
				if (currentClazz != null && currentClazz.getName().equals(clazz.getName())) {
					for (String fieldName : fieldNames) {
						if (f.getName().equals(fieldName)) {
							return false;
						}
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
