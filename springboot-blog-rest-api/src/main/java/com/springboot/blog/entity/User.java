package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.internal.bytebuddy.dynamic.loading.InjectionClassLoader;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //cause hibernate internally uses proxies to create objects
@Entity
@Table(name="users")
public class User {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "username", nullable = false, unique=true)
    private String username;
    @Column(name = "email", nullable = false, unique=true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name= "role_id", referencedColumnName = "id" )
    )
    private Set<Role> roles;
}
