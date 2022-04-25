package com.ReactiveProductCatalogService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CatalogServiceImpl implements CatalogService {
    private CatalogDao catalogsRepo;
	
	@Autowired
	public CatalogServiceImpl(CatalogDao catalogsRepo) {
		super();
		this.catalogsRepo = catalogsRepo;
	}

    @Override
    public Mono<CatalogBoundary> create(CatalogBoundary catalog) {
        return Mono.just(catalog)
            .flatMap(c -> {
                if (c.getProductId() == null) {
                    return Mono.error(()->new BadRequestException("id must be not null"));
                }

                if (c.getName() == null || c.getDescription() == null ||
                        c.getPrice() == null || c.getCategory() == null) {
                            
                            return Mono.error(()->new BadRequestException(
                                "body must include name, description, price and category (productDetails is optional)"));
                }

                return Mono.just(c);
            })
            .filterWhen(c -> isCatalogExists(c).map(exist -> !exist))
            .switchIfEmpty(Mono.error(()->new RuntimeException("id must be unique")))
            .map(this::boundaryToEntity)
            .flatMap(this.catalogsRepo::save)
		    .map(this::entityToBoundary)
            .log(); 
    }

    public Mono<Boolean> isCatalogExists (CatalogBoundary boundary) {
        return this.catalogsRepo.findByProductId(boundary.getProductId())
            .hasElement();
    }

    public Flux<Pageable> pageableFactory(int page, int size, String direction, String sortBy) {
        return Flux.just(PageRequest.of(page, size, Direction.ASC, direction, sortBy))
                    .map(pageable -> {
                        if (direction.equals("desc"))
                            return PageRequest.of(page, size, Direction.DESC, sortBy);
                        return pageable;
                    });
    }

    public Flux<CatalogEntity> queryExecuter(int page, int size, String direction, String sortBy, String filterType, String filterValue,
                                        Double minPrice, Double maxPrice) {

        return pageableFactory(page, size, direction, sortBy)
            .flatMap(pageable -> {

                switch(filterType) {
                    case "byName":
                        if (filterValue == null || filterValue.trim().length() == 0)
                            return Mono.error(()->new BadRequestException("filterValue cant be null or includes only spaces"));
                        
                        return this.catalogsRepo.findAllByName(filterValue, pageable);
                    
                    case "byCategoryName":
                        if (filterValue == null || filterValue.trim().length() == 0)
                            return Mono.error(()->new BadRequestException("filterValue cant be null or includes only spaces"));

                        return this.catalogsRepo.findAllByCategory(filterValue, pageable);

                    case "byPrice":
                        if (minPrice < 0 || maxPrice < 0 || maxPrice < minPrice)
                            return Mono.error(()->new BadRequestException("invalid range, got: " + minPrice + " and " + maxPrice));

                        Range<Double> range = Range.closed(minPrice, maxPrice);
                        return this.catalogsRepo.findAllByPriceBetween(range, pageable);

                    case "":
                        return this.catalogsRepo.findAllByProductIdNotNull(pageable);
                    
                    default:
                        return Mono.error(()->new BadRequestException("invalid filterType, got: " + filterType));
                }
            });
    }


    @Override
    public Flux<CatalogBoundary> getAll(int page, int size, String direction, String sortBy, String filterType, String filterValue,
                                            double minPrice, double maxPrice) {
        
        return queryExecuter(page, size, direction, sortBy, filterType, filterValue, minPrice, maxPrice)
            .map(this::entityToBoundary)
            .log();
    }

    @Override
    public Mono<CatalogBoundary> getById(String id) {
        return this.catalogsRepo
			.findByProductId(id)
			.switchIfEmpty(Mono.empty())
			.map(this::entityToBoundary)
			.log();
    }

    @Override
	public Mono<Void> cleanup() {
		return this.catalogsRepo
			.deleteAll()
			.log();
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

        return entity;
    }
}
