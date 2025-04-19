package com.shop.shop.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name= "carProduct")
public class CarProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    // @JsonIgnore
    private ProductModel productModel;

    @ManyToOne
    @JoinColumn(name = "carId", nullable = false)
    // @JsonIgnore
    private CarModel carModel;

    private int quantity;
//    private double price;

    public CarProductModel(){}

    public CarProductModel(Long id, ProductModel productModel, CarModel carModel, int quantity) {
        this.id = id;
        this.productModel = productModel;
        this.carModel = carModel;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
