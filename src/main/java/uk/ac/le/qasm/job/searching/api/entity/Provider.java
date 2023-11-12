package uk.ac.le.qasm.job.searching.api.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uk.ac.le.qasm.job.searching.api.Enumeration.Role;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "provider", schema = "job_searching")
public class Provider implements UserDetails{
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Getter
    @Column(name = "username", unique = true)
    @JsonProperty(value = "username")
    private String username;

    @Getter
    @Column(name = "password")
    @JsonProperty(value = "password")
    private String password;

    @Column(name = "company_name")
    @JsonProperty(value = "company_name")
    private String company_name;

    @Column(name = "company_contact_number")
    @JsonProperty(value = "company_contact_number")
    private String company_contact_number;

    @Column(name = "company_location")
    @JsonProperty(value = "company_location")
    private String company_location;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
