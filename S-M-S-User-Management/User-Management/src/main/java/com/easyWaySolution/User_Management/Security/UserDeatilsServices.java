package com.easyWaySolution.User_Management.Security;

import com.easyWaySolution.User_Management.Entity.Users;
import com.easyWaySolution.User_Management.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDeatilsServices implements UserDetailsService {

   @Autowired
   private  UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepo.findByEmail(username);

        if(user == null){
            System.out.println(" user not found ");
            throw  new UsernameNotFoundException("User not found "+ username );
        }
        System.out.println(user.toString());
        return new LoggedInUser(user);
    }
}
