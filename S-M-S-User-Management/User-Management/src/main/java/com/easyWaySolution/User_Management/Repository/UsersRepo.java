package com.easyWaySolution.User_Management.Repository;

import com.easyWaySolution.User_Management.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UsersRepo extends JpaRepository<Users, UUID> {
    Users findByEmail(String email);
}
