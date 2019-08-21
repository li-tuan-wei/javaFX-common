package ldh.common.json;

import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

public class ChangeField implements FieldNamingStrategy{

	private String fieldName;
	private String newFieldName;
	private Class<?> clazz;
	
	public ChangeField(String fieldName, String newFieldName) {
		this.fieldName = fieldName;
		this.newFieldName = newFieldName;
	}
	
	public ChangeField(Class<?> clazz, String fieldName, String newFieldName) {
		this(fieldName, newFieldName);
		this.clazz = clazz;
	}

	@Override
	public String translateName(Field field) {
		if (field != null) {
			if (clazz == null) {
				if (fieldName.equals(field.getName())) {
					return newFieldName;
				} 
			} else {
				Class<?> currentClazz = field.getDeclaringClass();
				if (currentClazz != null && currentClazz.getName().equals(clazz.getName())) {
					if (fieldName.equals(field.getName())) {
						return newFieldName;
					}
				} 
			}
			return field.getName();
		}
		
		return null;
	}
	
	

}
