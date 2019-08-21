package ldh.common.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ExclusionField implements ExclusionStrategy{

	private String[] exclusionFieldNames;
	private Class<?> clazz;
	
	public ExclusionField(String... exclusionFieldNames) {
		this.exclusionFieldNames = exclusionFieldNames;
	}
	
	public ExclusionField(Class<?> clazz, String... exclusionFieldNames) {
		this.exclusionFieldNames = exclusionFieldNames;
		this.clazz = clazz;
	}
	
	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if (f != null) {
			if (clazz == null) {
				for (String fieldName : exclusionFieldNames) {
					if (fieldName.equals(f.getName())) return true;
				}
			} else {
				Class<?> currentClazz = f.getDeclaringClass();
				if (currentClazz != null && currentClazz.getName().equals(clazz.getName())) {
					for (String fieldName : exclusionFieldNames) {
						if (f.getName().equals(fieldName)) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}

}
