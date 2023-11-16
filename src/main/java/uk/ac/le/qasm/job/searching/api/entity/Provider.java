package uk.ac.le.qasm.job.searching.api.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "provider",schema = "job_searching")
public class Provider implements UserDetails{
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    @TableId("id")
    private UUID id;

    @Getter
    @Column(name = "username", unique = true)
    @JsonProperty(value = "username")
    @TableField("username")
    private String username;

    @Getter
    @Column(name = "password")
    @JsonProperty(value = "password")
    @TableField("password")
    private String password;

    @Column(name = "company_name")
    @JsonProperty(value = "company_name")
    @TableField("company_name")
    private String company_name;

    @Column(name = "company_contact_number")
    @JsonProperty(value = "company_contact_number")
    @TableField("company_contact_number")
    private String company_contact_number;

    @Column(name = "company_location")
    @JsonProperty(value = "company_location")
    @TableField("company_location")
    private String company_location;

    @Enumerated(EnumType.STRING)
    @TableField("role")
    private Role role;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    @TableField(exist = false)
    private List<JobPost> jobPosts;

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
