package com.shop.shop.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shop.Dtos.GetProductsDto;
import com.shop.shop.Model.ProductModel;
import com.shop.shop.Model.UserModel;
import com.shop.shop.Service.UserService;
@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getallproducts")
    public List<GetProductsDto> getProducts(){
        return userService.getAllProducts();
    }

    @GetMapping("/getbycategory")
    public List<GetProductsDto> getbycategory(@RequestParam(name = "category")String category){
        return userService.getbycategory(category);
    }
    
    @GetMapping("/search")
    public ResponseEntity <GetProductsDto> search(@RequestParam(name = "pName")String pName){
        return userService.search(pName);
    }

    //@PostMapping("/register")
    //public UserModel Register( @RequestBody UserModel newUesr){
    //    return userService.signup(newUesr);
    //}

    // @GetMapping("/signin")
    //public  ResponseEntity <UserModel> Signin(@RequestParam(name="email") String email , @RequestParam(name="password") String password)
    //{
    //    return userService.login(email, password);
    //}
    @PostMapping("/edituser")
    public ResponseEntity<UserModel> editUesr(@RequestBody UserModel user) {
        return userService.editUesr(user);
    }
    @PostMapping("/deleteuser")
    public void deleteUser(){
        userService.deleteUsr();
    }
    @PostMapping("/logout")
    public void logout() {
        userService.logout();
    }
    

}
