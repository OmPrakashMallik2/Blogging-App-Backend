package com.example.Blogging.App.Backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer blogId;
    private Integer userId;
    private String heading;
    private String content;
    private Date date = new Date();
    private List<Integer> comments = new ArrayList<>();
    private List<Integer> likes = new ArrayList<>();
}
