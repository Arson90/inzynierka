package pl.arek.inzynierka.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="USERS_ROLES")
@NoArgsConstructor
public class UsersRoles {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Roles role;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public UsersRoles(User user, Roles role) {
        this.user = user;
        this.role = role;
    }
}
