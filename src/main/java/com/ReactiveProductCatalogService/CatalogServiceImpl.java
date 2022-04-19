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
        
        // return Mono.just(catalog)
        //     .filterWhen(c -> isExists(c).map(exist -> !exist))
        //     .switchIfEmpty(Mono.error(()->new RuntimeException("id must be unique")))
        //     .map(this::boundaryToEntity)
        //     .flatMap(this.store::save)
		//     .map(this::entityToBoundary);


        return Mono.just(catalog)
            .flatMap(c -> {
                if (c.getProductId() == null) {
                    return Mono.error(()->new RuntimeException("id must be not null"));
                }
                return Mono.just(c);
            })
            .filterWhen(c -> isExists(c).map(exist -> !exist))
            .switchIfEmpty(Mono.error(()->new RuntimeException("id must be unique")))
            .map(this::boundaryToEntity)
            .flatMap(this.store::save)
		    .map(this::entityToBoundary); 

        // return null;
    }

    // @Override
    // public Mono<CatalogBoundary> create(CatalogBoundary catalog) {
        
    //     return Mono.just(catalog)
    //         .filterWhen(c -> isExists(c).map(exist -> !exist))
    //         .switchIfEmpty(Mono.error(()->new RuntimeException("id must be unique")))
    //         .map(this::boundaryToEntity)
    //         .flatMap(this.store::save)
	// 	    .map(this::entityToBoundary); 
    // }

    // @Override
    // public Mono<CatalogBoundary> create(CatalogBoundary catalog) {
    //     System.err.println("catalog - " +  catalog.toString());
    //     return Mono.just(catalog)
    //         .flatMap((boundary) -> {

    //             if (boundary.getProductId() == null) {
    //                 return Mono.error(()->new RuntimeException("id must be not null"));
    //             }
                
    //             System.err.println("boundary - " +  boundary.toString());

    //             // store.findByProductId(boundary.getProductId())
    //             //     .flatMap(entity -> {
    //             //         System.err.println("########################");
    //             //         // if (entity != null && entity.getProductId() == boundary.getProductId()) {
    //             //         //     // System.err.println(entity.toString());
    //             //         //     return Mono.error(()->new RuntimeException("id must be unique"));
    //             //         // } else {
    //             //         //     System.err.println("in else - " + entity.toString());
    //             //         //     return Mono.just(boundary);
    //             //         // }

    //             //             if (entity.getProductId().equals(boundary.getProductId())) {
    //             //                 return Mono.error(()->new RuntimeException("id must be unique"));
    //             //             }
    //             //             return Mono.just(boundary);

    //             //     });

    //             // return store.findByProductId(boundary.getProductId())
    //             // .hasElement(bool -> {
    //             //     if (bool)
    //             //         return Mono.error(()->new RuntimeException("id must be unique"));
    //             //     else
    //             //         return Mono.just(boundary);

    //             // });

    //             // store.findByProductId(boundary.getProductId())


    //             // return store.findByProductId(boundary.getProductId())
    //             //     .switchIfEmpty(Mono.just(this.boundaryToEntity(boundary)));

                
    //             // return store
	// 			// .findByProductId(boundary.getProductId())
	// 			// .then(Mono.error(()->new NotFoundException("could not find content for id: " + boundary.getProductId())))
	// 			// .defaultIfEmpty(Mono.just(this.boundaryToEntity(boundary)));



    //             return Mono.just(boundary);

    //         })
    //         .map(this::boundaryToEntity)
    //         .flatMap(this.store::save) 
	// 	    .map(this::entityToBoundary); 

    //     // return Mono.just(catalog)
    //     // .map(this::boundaryToEntity)
    //     //     .flatMap(this.store::save) 
	// 	//     .map(this::entityToBoundary); 

    // }

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
