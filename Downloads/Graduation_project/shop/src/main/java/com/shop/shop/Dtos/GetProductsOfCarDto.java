package com.shop.shop.Dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.shop.shop.Model.CarProductModel;

public class GetProductsOfCarDto {
    public int quantity;
    public Long productId;
    public String productName;
    public String image;
    public String description;
    public BigDecimal productPrice;
    public BigDecimal allPrice;


    public GetProductsOfCarDto(int quantity, Long productId, String productName, String image, String description, BigDecimal productPrice, BigDecimal allPrice) {
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.image = image;
        this.description = description;
        this.productPrice = productPrice;
        this.allPrice = allPrice;
    }

    public static List<GetProductsOfCarDto> convertProductsOfCarToDto (List<CarProductModel> carProduct){
        List<GetProductsOfCarDto> productsOfCarList = new ArrayList<>();

       for (CarProductModel product : carProduct) {
            BigDecimal productPrice = product.getProductModel().getPrice();
            BigDecimal totalPrice = productPrice.multiply(new BigDecimal(product.getQuantity()));
       
            GetProductsOfCarDto dto = new GetProductsOfCarDto(
                product.getQuantity(), 
                product.getProductModel().getId(), 
                product.getProductModel().getpName(),
                product.getProductModel().getImage1(),
                product.getProductModel().getpDescription(),
                productPrice,
                totalPrice
            );
            productsOfCarList.add(dto);
       }
       return productsOfCarList;
    }
}

