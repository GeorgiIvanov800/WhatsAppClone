package com.georgi.whatsappclone.configuration.mapper;

import com.georgi.whatsappclone.model.entity.UserEntity;
import com.georgi.whatsappclone.model.entity.UserResponse;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class UserMapper {

    public UserEntity fromTokenAttributes(Map<String, Object> attributes) {
        UserEntity user = new UserEntity();

        if (attributes.containsKey("sub")) {
            user.setId(attributes.get("sub").toString());
        }

        if (attributes.containsKey("given_name")) {
            user.setFirstName(attributes.get("given_name").toString());
        } else if (attributes.containsKey("nickname")) {
            user.setLastName(attributes.get("nickname").toString());
        }

        if (attributes.containsKey("family_name")) {
            user.setLastName(attributes.get("family_name").toString());
        }

        if (attributes.containsKey("email")) {
            user.setEmail(attributes.get("email").toString());
        }

        user.setLastSeen(LocalDateTime.now());

        return user;
    }

    public UserResponse toUserResponse(UserEntity userEntity) {

        return UserResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .lastSeen(userEntity.getLastSeen())
                .email(userEntity.getEmail())
                .isOnline(userEntity.isUserOnline())
                .build();
    }
}
