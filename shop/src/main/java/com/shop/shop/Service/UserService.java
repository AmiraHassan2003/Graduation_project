package com.shop.shop.Service;
import com.shop.shop.Dtos.GetProductsDto;
import com.shop.shop.Holder.TokenHolder;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shop.shop.Model.ProductModel;
import com.shop.shop.Model.UserModel;
import com.shop.shop.Repository.ProductRepo;
import com.shop.shop.Repository.UserRepository;
import com.shop.shop.config.JwtService;


@Service
public class UserService {
    @Autowired
    private  ProductRepo productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // list of all products
       public  List <GetProductsDto>  getAllProducts(){
            List<ProductModel> products = productRepository.findAll();
            return GetProductsDto.convertProductsToDto(products);
    }

    // list of products in the same category
    public  List<GetProductsDto> getbycategory(String category) {
            List<ProductModel> products = productRepository.findByCategory(category);
            return GetProductsDto.convertProductsToDto(products);
    }

    // search for product by name
    public  ResponseEntity <GetProductsDto> search(String pName) {
        try {
            ProductModel product = productRepository.findBypName(pName);
            GetProductsDto productDto = GetProductsDto.convertProductToDto(product);
            return  new ResponseEntity<>( productDto ,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    //signup
    //public UserModel signup( UserModel user) {
        
    //   return userRepository.save(user);
    // }

    //login
    //public ResponseEntity <UserModel> login(String email, String password) {
    //    try {
    //        UserModel user = userRepository.findByEmailAndPassword(email , password);
    //        return new ResponseEntity<>(user ,HttpStatus.OK);
    //    } catch (Exception e) {
    //        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //    }

    // }
    //edit should send password and send role with data 
    public ResponseEntity<UserModel> editUesr(UserModel user) {
        try {
            
            UserModel updateUser =userRepository.findByEmail(JwtService.extractUsername(TokenHolder.getToken()));
            updateUser.setFirst_name(user.getFirst_name());
            updateUser.setLast_name(user.getLast_name());
            updateUser.setAddress(user.getAddress());
            updateUser.setEmail(user.getEmail());
            updateUser.setPhone(user.getPhone());
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
            updateUser.setPicture(user.getPicture());
            updateUser.setRole(user.getRole());
            userRepository.save(updateUser);
            return new ResponseEntity<>(updateUser,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);
        }

    }
    //deleteuser

    public ResponseEntity<Void> deleteUsr() {
        try {
            UserModel user=userRepository.findByEmail(JwtService.extractUsername(TokenHolder.getToken()));
            userRepository.delete(user);
            TokenHolder.clearToken();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("the user cant delete (not found)");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        
    }

    public void logout() {
        TokenHolder.clearToken();
    }
  


}


