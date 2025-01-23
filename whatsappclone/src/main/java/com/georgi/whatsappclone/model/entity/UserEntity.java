package com.georgi.whatsappclone.model.entity;

import com.georgi.whatsappclone.model.constant.UserConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = UserConstants.FIND_USER_BY_EMAIL,
query = "SELECT u FROM UserEntity u WHERE u.email = :email")
@NamedQuery(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF,
query = "SELECT u FROM UserEntity  u WHERE u.id != :publicId")
@NamedQuery(name = UserConstants.FIND_USER_BY_PUBLIC_ID,
query = "SELECT u FROM UserEntity u WHERE u.id = :publicId")
public class UserEntity extends BaseAuditingEntity{

    private static final int LAST_ACTIVE_INTERVAL = 5;

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime lastSeen;

    @OneToMany(mappedBy = "sender")
    private List<ChatEntity> chatAsSender;

    @OneToMany(mappedBy = "receiver")
    private List<ChatEntity> chatAsReceiver;

    @Transient
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }
}
