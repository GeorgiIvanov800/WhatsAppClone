package com.georgi.whatsappclone.repository;

import com.georgi.whatsappclone.model.constant.UserConstants;
import com.georgi.whatsappclone.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query(name = UserConstants.FIND_USER_BY_EMAIL)
    Optional<UserEntity> findByEmail(String email);

    @Query(name = UserConstants.FIND_USER_BY_PUBLIC_ID)
    Optional<UserEntity> findByPublicId(String publicId); // TODO add Param

    @Query(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF)
    List<UserEntity> findAllUsersExceptSelf(@Param("publicId") String senderId);
}
