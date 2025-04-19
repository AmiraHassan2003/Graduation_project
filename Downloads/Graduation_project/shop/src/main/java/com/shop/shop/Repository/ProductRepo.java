package com.shop.shop.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.shop.Model.ProductModel;

public interface ProductRepo extends JpaRepository< ProductModel, Long> {
    List<ProductModel> findByCategory(String category);
    ProductModel findBypName(String pName);


}
