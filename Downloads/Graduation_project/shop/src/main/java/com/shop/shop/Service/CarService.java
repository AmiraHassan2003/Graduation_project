package com.shop.shop.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.shop.Dtos.GetCarsOfUserDto;
import com.shop.shop.Dtos.GetProductsOfCarDto;
import com.shop.shop.Model.CarModel;
import com.shop.shop.Model.CarProductModel;
import com.shop.shop.Model.ProductModel;
import com.shop.shop.Model.UserModel;
import com.shop.shop.Repository.CarProductRepository;
import com.shop.shop.Repository.CarRepository;
import com.shop.shop.Repository.ProductRepo;
import com.shop.shop.Repository.UserRepository;


import jakarta.transaction.Transactional;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CarProductRepository carProductRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void addProductToCar(Long userId, Long productId, int quantity) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        
        ProductModel product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        CarModel car = user.getCars().stream()
                .filter(c -> !c.isStatus())
                .findFirst()
                .orElse(null);

        if (car == null) {
            car = new CarModel();
            car.setUserModel(user);
            car.setStatus(false);
            car.setTotalAmount(BigDecimal.ZERO);
            car.setCarDate(LocalDateTime.now());
            carRepository.save(car);
        }

        if (quantity > product.getStockQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getpName());
        }

        CarProductModel carProduct = carProductRepository.findByCarModelIdAndProductModelId(car.getId(), productId);

        if (carProduct != null) {
            int newQuantity = carProduct.getQuantity() + quantity;
            if (newQuantity > product.getStockQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getpName());
            }
            carProduct.setQuantity(newQuantity);
        } else {
            carProduct = new CarProductModel();
            carProduct.setCarModel(car);
            carProduct.setProductModel(product);
            carProduct.setQuantity(quantity);
        }

        carProductRepository.save(carProduct);

        BigDecimal totalAmount = car.getTotalAmount().add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        car.setTotalAmount(totalAmount);
        carRepository.save(car);
    }

    @Transactional
    public void editQuantity(Long userId, Long productId, int quantity) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CarModel activeCar = user.getCars().stream()
                .filter(car -> !car.isStatus())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active car found for this user"));

        CarProductModel carProduct = carProductRepository.findByCarModelIdAndProductModelId(activeCar.getId(), productId);
        if (carProduct == null) {
            throw new RuntimeException("Car does not contain the product with ID: " + productId);
        }

        ProductModel product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (quantity > product.getStockQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getpName());
        }

        int oldQuantity = carProduct.getQuantity();
        carProduct.setQuantity(quantity);
        carProductRepository.save(carProduct);

        BigDecimal totalAmount = activeCar.getTotalAmount();
        BigDecimal price = product.getPrice();
        

        totalAmount = totalAmount.subtract(price.multiply(BigDecimal.valueOf(oldQuantity))); 
        totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(quantity)));
        activeCar.setTotalAmount(totalAmount);

        carRepository.save(activeCar);
    }



    @Transactional
    public void buyCar(Long userId) {
        CarModel car = carRepository.findByUserModelId(userId);
        if(car.isStatus()){
            throw new RuntimeException("This car has already been sold");
        }
        if(car == null){
            throw new RuntimeException("Car not found");
        }
        
        List<CarProductModel> carProducts = car.getCarProducts();

        for (CarProductModel carProduct : carProducts) {
            ProductModel product = carProduct.getProductModel();
            int quantity = carProduct.getQuantity();
            int stockQuantity = product.getStockQuantity();
            int quantitySolid = product.getQuantitySold() + quantity;
            if (stockQuantity < quantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getpName());
            }

            product.setStockQuantity(stockQuantity - quantity);
            product.setQuantitySold(quantitySolid);
            productRepo.save(product);
        }

        car.setStatus(true);
        carRepository.save(car);
    }


    public GetCarsOfUserDto getAllCarsByUserId(Long userId){
        CarModel car = carRepository.findByUserModelId(userId);
        List<CarProductModel> carProduct = carProductRepository.findByCarModelId(car.getId());
        
        if(carProduct == null){
            throw new RuntimeException("No products found in the cart for user with ID: " + userId);
        }

        List<GetProductsOfCarDto> productsOfCarList = GetProductsOfCarDto.convertProductsOfCarToDto(carProduct);
        GetCarsOfUserDto carsOfUserDto = GetCarsOfUserDto.convertCarsToDto(car, productsOfCarList);
        return carsOfUserDto;
    }

    @Transactional
    public void deleteProductFromCar(Long userId, Long productId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CarModel activeCar = user.getCars().stream()
                .filter(car -> !car.isStatus())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active car found for this user"));

        CarProductModel carProduct = carProductRepository.findByCarModelIdAndProductModelId(activeCar.getId(), productId);
        if (carProduct == null) {
            throw new RuntimeException("Product not found in cart");
        }

        ProductModel product = carProduct.getProductModel();

        BigDecimal productTotal = product.getPrice().multiply(BigDecimal.valueOf(carProduct.getQuantity()));
        BigDecimal updatedTotal = activeCar.getTotalAmount().subtract(productTotal);
        activeCar.setTotalAmount(updatedTotal);

        carProductRepository.delete(carProduct);
        carRepository.save(activeCar); 
    }


    @Transactional
    public void deleteCar(Long carId) {
        CarModel car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found: " + carId));

        if (car.isStatus()) {
            throw new RuntimeException("Cannot delete a car that has already been sold.");
        }

        carRepository.delete(car);
    }

    @Transactional
    public int countOfProducts(Long carId) {
        List<CarProductModel> carProducts = carProductRepository.findByCarModelId(carId);
        return carProducts.size();
    }



}
