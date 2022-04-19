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

	// @RequestMapping(
	// 		path="/keyvalue",
	// 		method = RequestMethod.GET,
	// 		produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	// public Flux<DemoBoundary> getAll(
	// 		@RequestParam(name = "size", required = false, defaultValue = "10") int size, 
	// 		@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
	// 	return demoService.getAll(size, page);
	// }

	// @RequestMapping(
	// 		path="/keyvalue/{id}",
	// 		method = RequestMethod.GET,
	// 		produces = MediaType.APPLICATION_JSON_VALUE)
	// public Mono<DemoBoundary> getById(@PathVariable("id") String id) {
	// 	return demoService.getById(id);
	// }

	// @RequestMapping(
	// 		path="/keyvalue",
	// 		method = RequestMethod.DELETE)
	// public Mono<Void> cleanup() {
	// 	return demoService.cleanup();
	// }

}
