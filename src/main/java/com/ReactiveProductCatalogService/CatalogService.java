package com.ReactiveProductCatalogService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CatalogService {
	public Mono<CatalogBoundary> create (CatalogBoundary catalog);
	public Flux<CatalogBoundary> getAll(int page, int size, String direction, String sortBy,
											String filterType, String filterValue, double minPrice, double maxPrice);
	public Mono<CatalogBoundary> getById (String id);
	public Mono<Void> cleanup();
}
