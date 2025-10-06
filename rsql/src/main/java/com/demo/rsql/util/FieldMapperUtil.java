package com.demo.rsql.util;

import com.demo.rsql.config.RSQLProperty;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class FieldMapperUtil {
    
    public static Map<String, String> getFieldMap(Class<?> dtoClass, Class<?> entityClass) {
        Map<String, String> fieldMap = new HashMap<>();
        
        Field[] fields = dtoClass.getDeclaredFields();
        for (Field field : fields) {
            RSQLProperty annotation = field.getAnnotation(RSQLProperty.class);
            if (annotation != null) {
                String propertyPath = annotation.propertyPathMapping();
                if (propertyPath.isEmpty()) {
                    propertyPath = field.getName();
                }
                fieldMap.put(field.getName(), propertyPath);
            }
        }
        
        return fieldMap;
    }
}

