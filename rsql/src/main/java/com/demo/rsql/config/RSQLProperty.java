package com.demo.rsql.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link RSQLProperty} can be used to mark a field in a DTO as allowed option for RSQL query
 * <a
 *
 * @see "documentation/domain/paging-filtering-sorting/01_documentation.md"
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RSQLProperty {

    /**
     * Allowing filtering via rsql query
     */
    boolean filterable() default true;

    /**
     * Allowing sorting via rsql query
     */
    boolean sortable() default true;

    /**
     * The property which is used to map the database field. If not set the name of the field of the Annotation is used
     */
    String propertyPathMapping() default "";
}
