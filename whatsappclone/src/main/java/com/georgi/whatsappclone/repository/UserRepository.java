package com.georgi.whatsappclone.repository;

import com.georgi.whatsappclone.model.constant.UserConstants;
import com.georgi.whatsappclone.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query(name = UserConstants.FIND_USER_BY_EMAIL)
    Optional<UserEntity> findByEmail(String email);

    @Query(name = UserConstants.FIND_USER_BY_PUBLIC_ID)
    Optional<UserEntity> findByPublicId(String publicId);
}
