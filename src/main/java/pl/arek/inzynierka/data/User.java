package pl.arek.inzynierka.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String userName;
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UsersRoles> roles;

    public User(String userName, String password, Set<UsersRoles> roles)
    {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

}
