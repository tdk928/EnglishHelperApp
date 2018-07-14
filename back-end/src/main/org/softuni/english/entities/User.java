package org.softuni.english.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private Date deletedOn;

    private String experience;

    private int points;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_verbs",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="verb_id"))
    private Set<Verb> verbs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="users_roles",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> authorities;


    public User() {
        this.verbs = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) { this.username = username; }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Set<Verb> getVerbs() {
        return verbs;
    }

    public void setVerbs(Set<Verb> verbs) {
        this.verbs = verbs;
    }

    public void createVerb(Verb verb) {
        this.getVerbs().add(verb);
    }



    public void removeVerb(Verb verb) {
        Set<Verb> newVerbSet = this.getVerbs().stream().filter(e -> !e.getFirstForm().equals(verb.getFirstForm())).collect(Collectors.toSet());
        this.setVerbs(newVerbSet);
    }
}
