package com.green.rabbitmqchat.entity;

import com.green.rabbitmqchat.common.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Table(name = "t_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iuser;

    @Column(length = 100, nullable = false, unique = true)
    private String uid;

    @Column(length = 300, nullable = false)
    private String upw;

    @Column(length = 25, nullable = false)
    private String nm;

    @Column(length = 2100)
    private String pic;

    @Column(length = 10, nullable = false)
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'USER'")
    private RoleEnum role;
}
