package com.example.dbproject.Repository;

import com.example.dbproject.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByRecipeNameContainingIgnoreCase(String query);
    List<Recipe> findByMaterialContainingIgnoreCase(String query);
    List<Recipe> findByIntroContainingIgnoreCase(String query);
}
