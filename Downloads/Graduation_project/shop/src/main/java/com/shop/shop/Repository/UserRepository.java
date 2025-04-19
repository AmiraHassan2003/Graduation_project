package com.shop.shop.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shop.shop.Model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long>  {
    UserModel findByEmailAndPassword(String email, String password);
    UserModel findByEmail(String email);
    UserModel findById(long id);
    


}
