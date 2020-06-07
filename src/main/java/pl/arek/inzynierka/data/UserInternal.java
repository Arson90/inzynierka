package pl.arek.inzynierka.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class UserInternal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String username;
    private String password;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<UsersRoles> roles;

    public UserInternal(String username, String password, Set<UsersRoles> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserInternal(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }

}
