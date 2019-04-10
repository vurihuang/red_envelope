package com.upeoe.redenvelope.repository;

import com.upeoe.redenvelope.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author upeoe
 * @create 2019/4/11 05:23
 */
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
