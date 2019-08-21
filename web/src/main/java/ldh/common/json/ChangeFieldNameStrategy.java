package ldh.common.json;

import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

public class ChangeFieldNameStrategy implements FieldNamingStrategy{

	private String fieldName;
	
	public ChangeFieldNameStrategy(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String translateName(Field field) {
		System.out.println(field);
		if (fieldName.equals(field.getName())) {
			System.out.println(field.getClass());
		}
		return field.getName();
	}
	
	

}
