package com.example.demo.repository;

import com.example.demo.models.Kotoba;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KotobaRepository extends JpaRepository<Kotoba, Long> {
}
