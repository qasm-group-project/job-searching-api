package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uk.ac.le.qasm.job.searching.api.enums.Role;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seeker", schema = "job_searching")
public class JobSeeker implements UserDetails {
    @Getter
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id",access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Column(name = "username", unique = true)
    @JsonProperty(value = "username")
    @NotEmpty(message = "The username is required.")
    private String username;

    @Column(name = "password")
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "The password is required.")
    private String password;

    @Column(name = "nickname")
    @JsonProperty(value = "nickname")
    @NotEmpty(message = "The nickname is required.")
    private String nickname;

    @Column(name = "email")
    @JsonProperty(value = "email")
    @NotEmpty(message = "The email is required.")
    private String email;

    @Column(name = "phone")
    @JsonProperty(value = "phone")
    @NotEmpty(message = "The phone is required.")
    private String phone;

    @Column(name = "firstname")
    @JsonProperty(value = "firstname")
    @NotEmpty(message = "The firstname is required.")
    private String firstname;

    @Column(name = "lastname")
    @JsonProperty(value = "lastname")
    @NotEmpty(message = "The lastname is required.")
    private String lastname;

    @Column(name = "gender")
    @JsonProperty(value = "gender")
    @NotEmpty(message = "The gender is required.")
    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public void setId(String id_String) {
        if (id_String != null){
            this.id = UUID.fromString(id_String);
        }else {
            this.id=null;
        }
    }

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
