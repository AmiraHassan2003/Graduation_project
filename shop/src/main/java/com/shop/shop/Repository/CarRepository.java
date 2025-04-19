package com.shop.shop.Repository;

import com.shop.shop.Model.CarModel;

import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;

// import com.shop.shop.Dtos.GetCarsOfUserDto;

public interface CarRepository extends JpaRepository<CarModel, Long> {
    CarModel findByUserModelId(Long userId);
}
