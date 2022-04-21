package com.ReactiveProductCatalogService;

import java.util.Map;

public class CatalogBoundary {
    private String productId;
    private String name;
    private Double price;
    private String description;
    private Map<String, Object> productDetails;
    private String category;
    

    public CatalogBoundary() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Map<String, Object> productDetails) {
        this.productDetails = productDetails;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CatalogEntity [category=" + category + ", description=" + description + ", name=" + name + ", price="
                + price + ", productDetails=" + productDetails + ", productId=" + productId + "]";
    }
}
