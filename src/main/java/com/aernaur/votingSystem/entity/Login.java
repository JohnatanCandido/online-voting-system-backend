package com.aernaur.votingSystem.entity;

import com.aernaur.votingSystem.entity.types.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "login")
public class Login implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String password;

    private UserRole role;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Login(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == UserRole.ADMIN) {
            return Arrays.stream(UserRole.values()).map(r -> new SimpleGrantedAuthority(r.getRole())).toList();
        }
        return List.of(new SimpleGrantedAuthority(role.getRole()));
    }
}