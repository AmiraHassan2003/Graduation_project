package com.shop.shop.Dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.shop.shop.Model.CarModel;



public class GetCarsOfUserDto {
    public Long id;
    public boolean status;
    public BigDecimal totalAmount;
    public LocalDateTime carDate;
    public List <GetProductsOfCarDto> ProductsOfCarDto;
    public BigDecimal discount;
    public BigDecimal tax;  
    public BigDecimal shippingCost;

    public GetCarsOfUserDto(Long id, LocalDateTime carDate, boolean status, BigDecimal totalAmount, BigDecimal discount, BigDecimal tax, BigDecimal shippingCost, List<GetProductsOfCarDto> ProductsOfCarDto) {
        this.id = id;
        this.carDate = carDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.tax = tax;
        this.shippingCost = shippingCost;
        this.ProductsOfCarDto = ProductsOfCarDto;
    }

    public static GetCarsOfUserDto convertCarsToDto(CarModel car, List <GetProductsOfCarDto> productsOfCarList){

        GetCarsOfUserDto carsOfUserDto = new GetCarsOfUserDto(
            car.getId(),
            car.getCarDate(),
            car.isStatus(),
            car.getTotalAmount(),
            car.getDiscount(),
            car.getTax(),
            car.getShippingCost(),
            productsOfCarList
        );
        return carsOfUserDto;
    }

}