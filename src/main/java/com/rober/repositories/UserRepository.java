package com.rober.repositories;

import com.rober.entity.User;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;


@Registered
public interface UserRepository extends JpaRepository<User, Integer> {
}
