package dev.gaurav.testjpa.repositorys;

import dev.gaurav.testjpa.DTO.UserDTO;
import dev.gaurav.testjpa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);

    @Query("SELECT new dev.gaurav.testjpa.DTO.UserDTO(u.username, u.email, u.name) FROM User u")
    List<UserDTO> getAllCustomDTO();

    Optional<User> findByEmail(String email);
}
