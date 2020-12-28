package com.hashedin.repositories;

import com.hashedin.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmailAndDeletedFalse(String email);

    //Boolean existByEmailAndDeletedFalse(String email);
}
