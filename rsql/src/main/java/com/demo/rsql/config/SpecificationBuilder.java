package com.demo.rsql.config;

import io.github.perplexhub.rsql.RSQLCustomPredicate;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class SpecificationBuilder {

    /**
     * Builds a JPA Specification from an RSQL filter string.
     *
     * @param filters a URL-encoded RSQL filter string (may be null or blank)
     * @return a Specification parsed from the filters, or null if filters is null or blank
     */
    public static <T> Specification<T> build(String filters) {
        if (filters != null && !filters.trim().isEmpty()) {
            filters = URLDecoder.decode(filters, StandardCharsets.UTF_8);
            return RSQLJPASupport.toSpecification(filters);
        }

        return null;
    }

    /**
     * Builds a JPA Specification from an RSQL filter string using a field mapping.
     *
     * @param filters  a URL-encoded RSQL filter string (may be null or blank)
     * @param fieldMap a map where keys are external/API field names and values are entity attribute names
     * @return a Specification parsed from the filters with mapped fields, or null if filters is null or blank
     */
    public static <T> Specification<T> build(
            String filters,
            Map<String, String> fieldMap
    ) {
        if (filters != null && !filters.trim().isEmpty()) {
            filters = URLDecoder.decode(filters, StandardCharsets.UTF_8);
            return RSQLJPASupport.toSpecification(filters, fieldMap);
        }

        return null;
    }

    /**
     * Builds a JPA Specification from an RSQL filter string using a field mapping and custom predicates.
     *
     * @param filters          a URL-encoded RSQL filter string (may be null or blank)
     * @param fieldMap         a map where keys are external/API field names and values are entity attribute names
     * @param customPredicates custom RSQL predicate definitions to extend parsing capabilities
     * @return a Specification parsed from the filters with mapped fields and custom predicates, or null if filters is
     * null or blank
     */
    public static <T> Specification<T> build(
            String filters,
            Map<String, String> fieldMap,
            List<RSQLCustomPredicate<?>> customPredicates
    ) {
        if (filters != null && !filters.trim().isEmpty()) {
            filters = URLDecoder.decode(filters, StandardCharsets.UTF_8);
            return RSQLJPASupport.toSpecification(filters, fieldMap, customPredicates, Map.of());
        }

        return null;
    }

    /**
     * Builds a JPA Specification from an RSQL filter string and combines it with a base Specification.
     *
     * @param filters          a URL-encoded RSQL filter string (may be null or blank)
     * @param fieldMap         a map where keys are external/API field names and values are entity attribute names
     * @param customPredicates custom RSQL predicate definitions to extend parsing capabilities
     * @param baseSpecSupplier a supplier for a base Specification to AND with the filter Specification
     * @return the combined Specification; if filters is null or blank, returns the base Specification
     */
    public static <T> Specification<T> build(
            String filters,
            Map<String, String> fieldMap,
            List<RSQLCustomPredicate<?>> customPredicates,
            Supplier<Specification<T>> baseSpecSupplier
    ) {
        Specification<T> filterSpec = null;

        if (filters != null && !filters.trim().isEmpty()) {
            filters = URLDecoder.decode(filters, StandardCharsets.UTF_8);
            filterSpec = RSQLJPASupport.toSpecification(filters, fieldMap, customPredicates, Map.of());
        }

        Specification<T> baseSpec = Optional.ofNullable(baseSpecSupplier.get())
                .orElse(Specification.where(null));

        return (filterSpec != null) ? filterSpec.and(baseSpec) : baseSpec;
    }
}
