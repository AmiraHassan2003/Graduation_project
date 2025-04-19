package com.shop.shop.Model;

import java.math.BigDecimal;

import jakarta.persistence.*;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity
@Table(name="car")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
    private BigDecimal totalAmount;
    private LocalDateTime carDate;
    private BigDecimal discount;
    private BigDecimal tax;  
    private BigDecimal shippingCost;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    // @JsonIgnore
    private UserModel userModel;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonIgnoreProperties("carModel")
    private List<CarProductModel> carProducts = new ArrayList<>();

    public CarModel(){

    }

    public CarModel(Long id, LocalDateTime carDate, boolean status, BigDecimal totalAmount, BigDecimal discount, BigDecimal tax, BigDecimal shippingCost) {
        this.id = id;
        this.carDate = carDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.tax = tax;
        this.shippingCost = shippingCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCarDate() {
        return carDate;
    }

    public void setCarDate(LocalDateTime carDate) {
        this.carDate = carDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<CarProductModel> getCarProducts() {
        return carProducts;
    }

    public void setCarProducts(List<CarProductModel> carProducts) {
        this.carProducts = carProducts;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
