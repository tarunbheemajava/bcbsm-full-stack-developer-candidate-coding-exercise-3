package com.feedback.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.feedback.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, updatable = false, nullable = false, name = "username")
    private String username;
    private String name;
    @JsonIgnore
    private String password;

    @JsonIgnore
    private  Boolean accountNonExpired =true;
    @JsonIgnore
    private  Boolean accountNonLocked=true;
    @JsonIgnore
    private  Boolean credentialsNonExpired=true;
    @JsonIgnore
    private  Boolean enabled=true;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "IS_ACTIVE")
    @JsonIgnore
    @ColumnDefault(value = "true")
    private Boolean isActive = Boolean.TRUE;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void eraseCredentials() {
        this.password = null;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(" [");
        sb.append("Username=").append(this.username).append(", ");
        sb.append("Password=[PROTECTED], ");
        sb.append("Enabled=").append(this.enabled).append(", ");
        sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
        sb.append("credentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
        sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
        sb.append("Granted Authorities=").append(this.role).append("]");
        return sb.toString();
    }
}
