package com.example.springsocial.repository;

import com.example.springsocial.model.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Integer> {

    @Query("SELECT S.id FROM SecurityUser S WHERE S.username = ?1")
    Integer findUsernameExists(String username);

    @Procedure(name = "fn_validacion_correo")
    Boolean findCorreoExist(@Param("_correo") String correo);

    @Procedure(name = "get_correo_by_id")
    String getCorreoById(@Param("_user_id") Integer id);
}
