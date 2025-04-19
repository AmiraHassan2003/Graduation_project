package com.shop.shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shop.Model.ProductModel;
import com.shop.shop.Service.AdminService;


@RestController
@RequestMapping("/Admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @PostMapping("/AddProduct")
    public ProductModel AddProduct( @RequestBody ProductModel product){

        return adminService.AddProduct(product);

    }
    @PostMapping("/EditProduct")
    public ResponseEntity <ProductModel> EditProduct(@RequestBody ProductModel product){
        return adminService.EditProduct(product);

    }
    @PostMapping("/DelProduct")
    public void DelProduct(@RequestParam(name = "id") Long id){
       adminService.delProduct(id);    
    }    

    
}
