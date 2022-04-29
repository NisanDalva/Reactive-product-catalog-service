package com.ReactiveProductCatalogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CatalogController {
    private CatalogService catalogService;

	@Autowired
	public CatalogController(CatalogService catalogService) {
		super();
		this.catalogService = catalogService;
	}


    @RequestMapping(
		path="/catalog",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<CatalogBoundary> create(@RequestBody CatalogBoundary catalog) {
		return catalogService.create(catalog);
	}

	@RequestMapping(
		path="/catalog/{productId}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<CatalogBoundary> getById(@PathVariable("productId") String productId) {
		return catalogService.getById(productId);
	}

	@RequestMapping(
			path="/catalog",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<CatalogBoundary> getAll(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "sortBy", required = false, defaultValue = "_id") String sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String sortOrder,
			@RequestParam(name = "filterType", required = false, defaultValue = "") String filterType,
			@RequestParam(name = "filterValue", required = false) String filterValue,
			@RequestParam(name = "minPrice", required = false, defaultValue = "0") Double minPrice,
			@RequestParam(name = "maxPrice", required = false, defaultValue = "100") Double maxPrice
			) {
		return catalogService.getAll(page, size, sortOrder, sortBy, filterType, filterValue, minPrice, maxPrice);
	}

	@RequestMapping(
			path="/catalog",
			method = RequestMethod.DELETE)
	public Mono<Void> cleanup() {
		return catalogService.cleanup();
	}
}
