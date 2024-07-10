package com.example.Blogging.App.Backend.repositories;

import com.example.Blogging.App.Backend.Entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
}
