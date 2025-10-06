package com.demo.rsql.config;

import com.demo.rsql.rest.PageingAndRSQLQueryParams;
import io.github.perplexhub.rsql.RSQLCommonSupport;
import io.github.perplexhub.rsql.RSQLJPASupport;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Contains a configuration about all RSQL-Properties that are used for a specific request. The configuration options
 * are documented at {@link RSQLPropertyConfiguration}.
 * This can be used to configure, validate against a whitelist and generating a specification for a given RSQL filter
 * or sort string
 *
 * @see "documentation/domain/paging-filtering-sorting/01_documentation.md"
 */
@Getter
@EqualsAndHashCode
@ToString
public class RSQLPropertyConfigurations {

    private static final Pattern MULTIPLE_SORT_SEPARATOR = Pattern.compile(";");
    private static final Pattern SORT_SEPARATOR = Pattern.compile(",");

    private final List<RSQLPropertyConfiguration> propertyConfigurations = new ArrayList<>();

    /**
     * Creates a configuration for the RSQL filtering, sorting and mapping of fields which are annotated with
     * {@link RSQLProperty} in the given class
     *
     * @param clazz The class like a dto or another Pojo with the annotation on their fields
     * @param <T>   The type of the delivered class
     * @return The instance with all RSQLParameters that are found via the annotation. This is empty if no annotation
     * is found
     */
    public static <T> RSQLPropertyConfigurations fromClass(final Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }

        final var instance = new RSQLPropertyConfigurations();
        instance.extractFromAnnotation(clazz);
        return instance;
    }

    /**
     * Use to add new or additional RSQLPropertyConfiguration(s)
     */
    public RSQLPropertyConfigurations add(@NotNull final RSQLPropertyConfiguration... propertyConfigurations) {
        this.propertyConfigurations.addAll(Arrays.stream(propertyConfigurations).toList());

        return this;
    }

    /**
     * Validating the given rsql strings against the RSQLPropertyConfigurations. On success, it delivers the
     * Specification that can use for database query
     */
    public <U> Specification<U> getValidatedSpec(final PageingAndRSQLQueryParams queryParams) {
        return getValidatedSpec(queryParams.getFilter(), queryParams.getSort());
    }

    /**
     * Validating the given rsql strings against the RSQLPropertyConfigurations. On success, it delivers the
     * Specification that can use for database query
     */
    public <U> Specification<U> getValidatedSpec(
            final String filter,
            final String sort
    ) {
        validate(filter, sort);

        final Specification<U> filterSpec = RSQLJPASupport
                .toSpecification(filter, propertyPathMapper());

        final Specification<U> sortSpec = RSQLJPASupport
                .toSort(sort, propertyPathMapper());

        return filterSpec.and(sortSpec);
    }

    private Map<String, String> propertyPathMapper() {
        return propertyConfigurations.stream()
                .filter(RSQLPropertyConfiguration::hasMapper)
                .collect(
                        Collectors.toMap(RSQLPropertyConfiguration::getPropertyName,
                                RSQLPropertyConfiguration::getPropertyPathMapping)
                );
    }

    private void validate(
            final String filter,
            final String sort
    ) {
        final var deliveredParameters = RSQLCommonSupport
                .toMultiValueMap(filter)
                .toSingleValueMap()
                .keySet();

        checkWhitelist(
                deliveredParameters,
                propertyConfigurations.stream().filter(RSQLPropertyConfiguration::isFilterable).toList(),
                "filter"
        );
        checkWhitelist(
                parseSort(sort),
                propertyConfigurations.stream().filter(RSQLPropertyConfiguration::isSortable).toList(),
                "sort"
        );
    }

    private static Set<String> parseSort(final String sortString) {
        if (sortString == null || sortString.isBlank()) {
            return new HashSet<>();
        }

        return MULTIPLE_SORT_SEPARATOR.splitAsStream(sortString)
                .map(RSQLPropertyConfigurations::split)
                .map(List::getFirst)
                .collect(Collectors.toSet());
    }

    private static List<String> split(final String sort) {
        return SORT_SEPARATOR.splitAsStream(sort)
                .filter(StringUtils::hasText)
                .toList();
    }

    private void checkWhitelist(
            final Set<String> deliveredProperties,
            final List<RSQLPropertyConfiguration> whitelist,
            final String parameterType
    ) {
        deliveredProperties.forEach(property -> {
            final var isContainedInWhitelist = whitelist.stream()
                    .anyMatch(rsqlPropertyConfiguration ->
                            Objects.equals(rsqlPropertyConfiguration.getPropertyName(), property)
                    );

            if (!isContainedInWhitelist) {
                throw new RuntimeException(
                        "The requested " + parameterType + " property " + property + " is not in whitelist!"
                );
            }

        });
    }

    private void extractFromAnnotation(final Class<?> clazz) {
        if (clazz == null || clazz == Object.class) {
            return;
        }

        Arrays.stream(clazz.getDeclaredFields()).forEach(this::createPropertySetting);

        extractFromAnnotation(clazz.getSuperclass());
    }


    private void createPropertySetting(
            final Field field
    ) {
        final var annotation = field.getAnnotation(RSQLProperty.class);

        if (annotation == null) {
            return;
        }

        final var rsqlPropertyConfiguration = RSQLPropertyConfiguration
                .forName(field.getName())
                .setFilterable(annotation.filterable())
                .setSortable(annotation.sortable());

        if (!annotation.propertyPathMapping().isBlank()) {
            rsqlPropertyConfiguration.setPropertyPathMapping(annotation.propertyPathMapping());
        }

        this.add(rsqlPropertyConfiguration);
    }

}
