package com.example.Blogging.App.Backend.repositories;

import com.example.Blogging.App.Backend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
