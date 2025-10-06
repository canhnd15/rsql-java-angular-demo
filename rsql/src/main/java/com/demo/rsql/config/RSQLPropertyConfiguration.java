package com.demo.rsql.config;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @see "documentation/domain/paging-filtering-sorting/01_documentation.md"
 */
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class RSQLPropertyConfiguration {

    /**
     * Name of the property which can be used in the RSQL sort or filter strings
     */
    private final String propertyName;

    /**
     * Defines if the property can be used for filtering. Default: true
     */
    private boolean filterable = true;

    /**
     * Defines if the property can be used for filtering. Default: true
     */
    private boolean sortable = true;

    /**
     * Defines the propertyPath which is needed to map the database field
     */
    private String propertyPathMapping;

    public RSQLPropertyConfiguration(final String propertyName) {
        this.propertyName = propertyName;
    }

    public static RSQLPropertyConfiguration forName(@NotNull final String parameter) {
        return new RSQLPropertyConfiguration(parameter);
    }

    public boolean hasMapper() {
        return this.propertyPathMapping != null;
    }

}
