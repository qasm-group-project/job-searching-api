package uk.ac.le.qasm.job.searching.api.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@TableName("job_seeker_account")
@Table(name = "job_seeker_account", schema = "job_searching")
public class JobSeekerAccount {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    @TableField("id")
    private UUID id;

    @Column(name = "ac_username")
    @JsonProperty(value = "ac_username")
    @TableField("ac_username")
    private String ac_username;

    @Column(name = "ac_password")
    @JsonProperty(value = "ac_password")
    @TableField("ac_password")
    private String ac_password;

    @Column(name = "nickname")
    @JsonProperty(value = "nickname")
    @TableField("nickname")
    private String nickname;

    @Column(name = "email")
    @JsonProperty(value = "email")
    @TableField("email")
    private String email;

    @Column(name = "phone")
    @JsonProperty(value = "phone")
    @TableField("phone")
    private String phone;

    @Column(name = "firstname")
    @JsonProperty(value = "firstname")
    @TableField("firstname")
    private String firstname;

    @Column(name = "lastname")
    @JsonProperty(value = "lastname")
    @TableField("lastname")
    private String lastname;

    @Column(name = "gender")
    @JsonProperty(value = "gender")
    @TableField("gender")
    private String gender;


}
