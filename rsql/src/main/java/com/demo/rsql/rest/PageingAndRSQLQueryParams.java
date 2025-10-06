package com.demo.rsql.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

/**
 * This class should be used for the standard list REST-GET endpoints to keep the API consistent
 *
 * @see "documentation/domain/paging-filtering-sorting/01_documentation.md"
 */
@Getter
public class PageingAndRSQLQueryParams {

    public PageingAndRSQLQueryParams(
            final Integer page,
            final Integer size,
            final String filter,
            final String sort
    ) {
        this.page = Optional.ofNullable(page).orElse(0);
        this.size = Optional.ofNullable(size).orElse(50);
        this.filter = filter;
        this.sort = sort;
    }

    @Schema(description = "Zero-based page index (0..N)", defaultValue = "0")
    private final int page;

    @Schema(description = "The size of the page to be returned", defaultValue = "50")
    private final int size;

    @Schema(description = "A RSQL filter string. " +
            "See: https://github.com/perplexhub/rsql-jpa-specification?tab=readme-ov-file#rsql-syntax-reference")
    private final String filter;

    @Schema(description = "A RSQL sort string. " +
            "See: https://github.com/perplexhub/rsql-jpa-specification?tab=readme-ov-file#sort-syntax")
    private final String sort;

    public String getFilter() {
        return filter;
    }

    public String getSort() {
        return sort;
    }

    public PageRequest getPageable() {
        return PageRequest.of(page, size);
    }
}
