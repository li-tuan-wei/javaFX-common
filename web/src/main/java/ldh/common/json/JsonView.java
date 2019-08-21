package ldh.common.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class JsonView {
	
	private static Logger logger = LoggerFactory.getLogger(JsonView.class);
	private Gson gson;
	private Map<String, Object> data = new HashMap<String, Object>();
	private boolean isSuccess = true;
	private GsonBuilder builder = new GsonBuilder();
	
	public JsonView() {
		init();
	}
	
	public JsonView put(String key, Object message) {
		data.put(key, message);
		return this;
	}
	
	public JsonView fail(String message) {
		this.isSuccess = false;
		return put("info", message);
	}
	
	public String toJson() {
		createGson();
		data.put("isSuccess", this.isSuccess);
		try {
			return gson.toJson(data);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public String toJson(Object data) {
		createGson();
		try {
			return gson.toJson(data);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public String toJsonTree(Object data) {
		createGson();
		try {
			return gson.toJsonTree(data).toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public String toJson(Object data, Type type) {
		createGson();
		try {
			return gson.toJson(data, type);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public String toJsonTree(Object data, Type type) {
		createGson();
		try {
			return gson.toJsonTree(data, type).toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		createGson();
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return gson.fromJson(jsonString, clazz);
		} catch (Exception e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}
	
	public <T> T fromJson(String jsonString, Type type) {
		createGson();
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return gson.fromJson(jsonString, type);
		} catch (Exception e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}
	
	/** 
     * 设置转换日期类型的format pattern,如果不设置默认打印Timestamp毫秒数. 
     */  
    public JsonView setDateFormat(String pattern) {  
        if (StringUtils.isNotBlank(pattern)) {   
            builder.setDateFormat(pattern);
        } 
        return this;
    } 
    
    public JsonView setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {  
        if (fieldNamingStrategy != null) { 
            builder.setFieldNamingStrategy(fieldNamingStrategy);
        } 
        return this;
    } 
    
    public JsonView setExclusionStrategy(ExclusionStrategy... fxclusionStrategies) {  
        if (fxclusionStrategies != null) {   
            builder.setExclusionStrategies(fxclusionStrategies);
        } 
        return this;
    }
    
    public JsonView registerTypeAdapter(Type type, Object object) {  
        if (type != null || object != null) {   
            builder.registerTypeAdapter(type, object);
        }
        return this;
    }
    
    private void createGson() {
    	if (gson == null) gson = builder.create();
    }
    
    private void init() {
    	builder.serializeNulls();
    }
}
