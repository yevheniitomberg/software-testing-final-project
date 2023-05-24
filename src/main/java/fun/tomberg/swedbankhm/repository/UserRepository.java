package fun.tomberg.swedbankhm.repository;

import fun.tomberg.swedbankhm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u from User u where u.email=:email and u.isEnabled=true")
    User findByEmailAndEnabledTrue(@Param("email") String email);
    LinkedList<User> findAllByEmail(String email);
}