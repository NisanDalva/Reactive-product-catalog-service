package com.ReactiveProductCatalogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CatalogServiceImpl implements CatalogService {
    private CatalogDao store;
	
	@Autowired
	public CatalogServiceImpl(CatalogDao store) {
		super();
		this.store = store;
	}


    public Mono<Boolean> isExists (CatalogBoundary boundary) {

        return store.findByProductId(boundary.getProductId())
            .hasElement();
    }

    @Override
    public Mono<CatalogBoundary> create(CatalogBoundary catalog) {
        return Mono.just(catalog)
            .flatMap(c -> {
                if (c.getProductId() == null) {
                    return Mono.error(()->new BadRequestException("id must be not null"));
                }
                return Mono.just(c);
            })
            .filterWhen(c -> isExists(c).map(exist -> !exist))
            .switchIfEmpty(Mono.error(()->new BadRequestException("id must be unique")))
            .map(this::boundaryToEntity)
            .flatMap(this.store::save)
		    .map(this::entityToBoundary); 
    }

    @Override
    public Flux<CatalogBoundary> getAll(int size, int page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<CatalogBoundary> getById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Void> cleanup() {
        // TODO Auto-generated method stub
        return null;
    }


    public CatalogBoundary entityToBoundary(CatalogEntity entity) {
        CatalogBoundary boundary = new CatalogBoundary();

        if (entity.getProductId() != null) {
            boundary.setProductId(entity.getProductId());
        }

        boundary.setCategory(entity.getCategory());
        boundary.setDescription(entity.getDescription());
        boundary.setName(entity.getName());
        boundary.setPrice(entity.getPrice());
        boundary.setProductDetails(entity.getProductDetails());

        System.err.println("in entityToBoundary");
        return boundary;

    }

    public CatalogEntity boundaryToEntity(CatalogBoundary boundary) {
        CatalogEntity entity = new CatalogEntity();

        if (boundary.getProductId() != null) {
            entity.setProductId(boundary.getProductId());
        }

        entity.setCategory(boundary.getCategory());
        entity.setDescription(boundary.getDescription());
        entity.setName(boundary.getName());
        entity.setPrice(boundary.getPrice());
        entity.setProductDetails(boundary.getProductDetails());

        System.err.println("in boundaryToEntity");
        return entity;

    }


}
