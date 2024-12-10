package com.example.dbproject.Controller;

import com.example.dbproject.Service.CategoryService;
import com.example.dbproject.Entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 카테고리 JSON 형태로 반환
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }
}
