package com.example.springsocial.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NamedEntityGraphs({@NamedEntityGraph(name = "securityUser"),
                    @NamedEntityGraph(name = "securityUser.roles", attributeNodes = {
                        @NamedAttributeNode(value = "roles")
                        })
})
@Entity
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "fn_validacion_correo",
        procedureName = "check_correo_existe",
        parameters = {
            @StoredProcedureParameter(name = "_correo", mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(name = "result", mode = ParameterMode.OUT, type = Boolean.class)
        }),
    @NamedStoredProcedureQuery(name = "get_correo_by_id",
            procedureName = "get_correo_by_id",
            parameters = {
                    @StoredProcedureParameter(name = "_user_id", mode = ParameterMode.IN, type = Integer.class),
                    @StoredProcedureParameter(name = "result", mode = ParameterMode.OUT, type = String.class)
            })
})
public class SecurityUser{

    @Id
    @GeneratedValue(generator = "sec_user_seq")
    @GenericGenerator(
            name = "sec_user_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @org.hibernate.annotations.Parameter(name = "sec_user_seq", value = "sec_user_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "SecurityUserId")
    private Integer id;
    @Column(name = "Username", unique = true, updatable = false)
    private String username;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column(name = "Enabled", nullable = false)
    private boolean enabled;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "securityUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<SecurityRole> roles;

    public SecurityUser() {
    }

    public SecurityUser(Integer id) {
        this.id = id;
    }

    public SecurityUser(String username, String password) {
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.enabled = true;
    }

    public SecurityUser(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public SecurityUser(String username, String password, Set<SecurityRole> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public SecurityUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<SecurityRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SecurityRole> roles) {
        for (SecurityRole x : roles) {
            x.setSecurityUser(this);
        }
        this.roles = roles;
    }

    public void setRoles(Integer tipoUsuario){
        Set<SecurityRole> rols = new HashSet<>();
        switch(tipoUsuario){
            case 1:
                rols.add(new SecurityRole("ROLE_ADMIN"));
            case 2:
                rols.add(new SecurityRole("ROLE_TRAINER"));
            default:
                rols.add(new SecurityRole("ROLE_RUNNER"));
        }
        setRoles(rols);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
