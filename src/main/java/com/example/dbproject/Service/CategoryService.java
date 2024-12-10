package com.example.dbproject.Service;

import com.example.dbproject.Entity.Category;
import com.example.dbproject.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // 모든 카테고리 정보를 JSON 형식으로 반환
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
