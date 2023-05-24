package fun.tomberg.swedbankhm.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = "(^[A-Za-z]{3,16})([ ]{0,1})([A-Za-z]{3,16})?([ ]{0,1})?([A-Za-z]{3,16})?([ ]{0,1})?([A-Za-z]{3,16})", message = "Your full name is not valid")
    private String fullName;

    @Nullable
    private String dateOfBirth;

    @Nullable
    private String address;

    @Column(name = "email")
    @Pattern(regexp = "^(.+)@(.+)$", message = "It is not an email address")
    private String email;

    @Column(name = "password")
    @Size(min = 8, message = "Password must contains minimum 8 characters")
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Transient
    private String commitPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return this.isEnabled;
    }

    public boolean isPassesMatches() {
        return password.equals(commitPassword);
    }

    public User setForeignParams(User user) {
        this.setFullName(user.getFullName());
        this.setAddress(user.getAddress());
        this.setDateOfBirth(user.getDateOfBirth());
        return this;
    }

    public User adminEditing(User user) {
        this.setForeignParams(user);
        this.setEnabled(user.isEnabled());
        return this;
    }

    public boolean isAdmin() {
        for (Role role : getRoles()) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}

