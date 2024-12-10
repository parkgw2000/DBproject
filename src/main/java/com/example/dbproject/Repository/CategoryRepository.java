package com.example.dbproject.Repository;

import com.example.dbproject.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    // 모든 카테고리 정보 가져오기
    List<Category> findAll();
}
