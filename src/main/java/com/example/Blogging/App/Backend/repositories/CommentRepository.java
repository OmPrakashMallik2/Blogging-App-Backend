package com.example.Blogging.App.Backend.repositories;

import com.example.Blogging.App.Backend.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}
