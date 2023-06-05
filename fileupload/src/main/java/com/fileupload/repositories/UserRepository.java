package com.fileupload.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fileupload.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
}
