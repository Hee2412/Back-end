package com.example.heecoffee.Repository;

import com.example.heecoffee.Model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
}
