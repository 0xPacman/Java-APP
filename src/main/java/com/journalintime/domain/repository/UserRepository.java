package com.journalintime.domain.repository;

import com.journalintime.domain.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface du repository User (couche domaine).
 */
public interface UserRepository {
    
    User save(User user);
    
    Optional<User> findById(Long id);
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    List<User> findAllActive();
    
    void delete(User user);
    
    void softDelete(User user);
}
