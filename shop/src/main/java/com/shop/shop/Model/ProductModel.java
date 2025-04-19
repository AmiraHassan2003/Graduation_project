package com.shop.shop.Model;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name= "product")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pName;
    private String pDescription;
    private String category;
    private String image1;
    private String image2;
    private String image3;
    private BigDecimal price;
    private int stockQuantity;
    private int quantitySold;

    @OneToMany(mappedBy = "productModel", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonIgnoreProperties("productModel")
    private List<CarProductModel> carProducts = new ArrayList<>();
    
    public ProductModel(){

    }

    public ProductModel(Long id, String pName, String pDescription, String category, String image1, String image2, String image3, BigDecimal price,
            int stockQuantity, int quantitySold) {
        this.id = id;
        this.pName = pName;
        this.pDescription = pDescription;
        this.category = category;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.quantitySold = quantitySold;
    }

    public ProductModel(String pName, String pDescription, String category, String image1, String image2, String image3, BigDecimal price, int stockQuantity,
            int quantitySold) {
        this.pName = pName;
        this.pDescription = pDescription;
        this.category = category;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.quantitySold = quantitySold;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image) {
        this.image1 = image;
    }
    public String getImage2() {
        return image2;
    }

    public void setImage2(String image) {
        this.image2 = image;
    }
    public String getImage3() {
        return image3;
    }

    public void setImage3(String image) {
        this.image3 = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
    
    public List<CarProductModel> getCarProducts() {
        return carProducts;
    }

    public void setCarProducts(List<CarProductModel> carProducts) {
        this.carProducts = carProducts;
    }

    
}
