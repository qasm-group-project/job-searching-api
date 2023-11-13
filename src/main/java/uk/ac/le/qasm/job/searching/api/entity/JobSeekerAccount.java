package uk.ac.le.qasm.job.searching.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "seeker", schema = "job_searching")
@TableName(value = "seeker",schema = "job_searching")
public class JobSeekerAccount {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    @TableId(type = IdType.ASSIGN_UUID)
    private UUID id;

    @Column(name = "username")
    @JsonProperty(value = "username")
    @TableField("username")
    private String username;

    @Column(name = "password")
    @JsonProperty(value = "password")
    @TableField("password")
    private String password;

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
