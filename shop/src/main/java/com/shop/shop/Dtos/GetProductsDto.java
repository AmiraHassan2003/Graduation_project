package com.shop.shop.Dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.shop.shop.Model.ProductModel;

public class GetProductsDto {
    public Long id;
    public String category;
    public String image;
    public String p_description;
    public String p_name;
    public BigDecimal price;
    public int quantity_sold;
    public int stock_quantity;



    public GetProductsDto(Long id, String category, String image, String p_description, String p_name, BigDecimal price, int quantity_sold, int stock_quantity) {
        this.id = id;
        this.category = category;
        this.image = image;
        this.p_description = p_description;
        this.p_name = p_name;
        this.price = price;
        this.quantity_sold = quantity_sold;
        this.stock_quantity = stock_quantity;
    }

    public static List<GetProductsDto> convertProductsToDto(List<ProductModel> products){
        List<GetProductsDto> productsDto = new ArrayList<>();
            
            for (ProductModel product : products) {
                GetProductsDto productDto = new GetProductsDto(
                    product.getId(),
                    product.getCategory(),
                    product.getImage1(),
                    product.getpDescription(),
                    product.getpName(),
                    product.getPrice(),
                    product.getQuantitySold(),
                    product.getStockQuantity()
                );
                productsDto.add(productDto);
            }

            return  productsDto;
    }

    public static GetProductsDto convertProductToDto(ProductModel product){
        GetProductsDto productDto = new GetProductsDto(
                    product.getId(),
                    product.getCategory(),
                    product.getImage1(),
                    product.getpDescription(),
                    product.getpName(),
                    product.getPrice(),
                    product.getQuantitySold(),
                    product.getStockQuantity()
                );

            return  productDto;
    }
}

