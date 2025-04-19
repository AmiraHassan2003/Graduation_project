package com.shop.shop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.shop.shop.Model.ProductModel;
import com.shop.shop.Repository.ProductRepo;


@Service
public class AdminService {

    @Autowired 
    private ProductRepo productsRepo;

    public ProductModel AddProduct( ProductModel product) {
        product.setImage1("images/"+product.getImage1());
        product.setImage2("images/"+product.getImage2());
        product.setImage3("images/"+product.getImage3());
        product.setQuantitySold(0);
        productsRepo.save(product);
        return product; 
    }

    public ResponseEntity <ProductModel> EditProduct(ProductModel productold) {
                try {
                ProductModel productnew =productsRepo.findById(productold.getId()).get();
                productnew.setpName(productold.getpName());
                productnew.setpDescription(productold.getpDescription());
                productnew.setCategory(productold.getCategory());
                productnew.setImage1("images/"+productold.getImage1());
                productnew.setImage2("images/"+productold.getImage2());
                productnew.setImage3("images/"+productold.getImage3());
                productnew.setPrice(productold.getPrice());
                productnew.setQuantitySold(productold.getQuantitySold());
                productnew.setStockQuantity(productold.getStockQuantity());
                productsRepo.save(productnew);
                return new ResponseEntity<>(productnew,HttpStatus.OK); 

            } catch (Exception e) {
                System.out.println("this product not can edit (not found)");
                return new ResponseEntity<>(productold,HttpStatus.NOT_FOUND); 
            }
    }

    public ResponseEntity<Void> delProduct(Long id) {
        try {
            productsRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("this product not can delete (not found)");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        
    }
}
