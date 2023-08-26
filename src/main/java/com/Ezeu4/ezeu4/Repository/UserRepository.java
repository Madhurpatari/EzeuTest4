package com.Ezeu4.ezeu4.Repository;

import com.Ezeu4.ezeu4.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
}
