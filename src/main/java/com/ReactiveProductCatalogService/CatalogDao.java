package com.ReactiveProductCatalogService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CatalogDao extends ReactiveMongoRepository<CatalogEntity, String>{

	public Flux<CatalogEntity> findAllByProductIdNotNull(Pageable pageable);
	public Mono<CatalogEntity> findByProductId(String id);

}
