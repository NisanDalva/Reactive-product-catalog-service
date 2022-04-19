package com.ReactiveProductCatalogService;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

import org.springframework.data.annotation.Id;

@Document(collection = "catalogs")
public class CatalogEntity {
    // @Id private String id;
    @Id private String productId;
    private String name;
    private double price;
    private String description;
    private Map<String, Object> productDetails;
    private String category;
    
    public CatalogEntity(String productId, String name, double price, String description,
            Map<String, Object> productDetails, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.productDetails = productDetails;
        this.category = category;
    }

    public CatalogEntity() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
