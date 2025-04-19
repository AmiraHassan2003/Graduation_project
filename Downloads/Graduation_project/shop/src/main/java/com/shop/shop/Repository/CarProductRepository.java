package com.shop.shop.Repository;
import java.util.List;

import com.shop.shop.Model.CarProductModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarProductRepository extends JpaRepository<CarProductModel, Long> {
    CarProductModel findByCarModelIdAndProductModelId(Long carId, Long productId);
    List<CarProductModel> findByCarModelId(Long carId);
    
}
