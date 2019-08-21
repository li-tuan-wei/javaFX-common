package ldh.common.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ExclusionFieldNameStrategy implements ExclusionStrategy{

	private String[] exclusionFieldNames;
	
	public ExclusionFieldNameStrategy(String... exclusionFieldNames) {
		this.exclusionFieldNames = exclusionFieldNames;
	}
	
	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes arg0) {
		for (String fieldName : exclusionFieldNames) {
			if (fieldName.equals(arg0.getName())) return true;
		}
		return false;
	}

}
