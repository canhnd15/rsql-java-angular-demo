package com.demo.rsql.util;

import com.demo.rsql.config.RSQLProperty;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PageableUtil {
    
    public static Pageable withSortingAndFieldMapping(Pageable pageable, String sorts, 
                                                      Class<?> dtoClass, Class<?> entityClass) {
        if (sorts == null || sorts.trim().isEmpty()) {
            return pageable;
        }
        
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortFields = sorts.split(",");
        
        for (String sortField : sortFields) {
            String[] parts = sortField.trim().split(":");
            String fieldName = parts[0];
            Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1]) 
                ? Sort.Direction.DESC : Sort.Direction.ASC;
            
            // Map DTO field to entity field
            String entityFieldName = mapFieldName(fieldName, dtoClass, entityClass);
            orders.add(new Sort.Order(direction, entityFieldName));
        }
        
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
    
    private static String mapFieldName(String dtoFieldName, Class<?> dtoClass, Class<?> entityClass) {
        try {
            Field dtoField = dtoClass.getDeclaredField(dtoFieldName);
            RSQLProperty annotation = dtoField.getAnnotation(RSQLProperty.class);
            if (annotation != null && !annotation.propertyPathMapping().isEmpty()) {
                return annotation.propertyPathMapping();
            }
            return dtoFieldName;
        } catch (NoSuchFieldException e) {
            return dtoFieldName;
        }
    }
}

