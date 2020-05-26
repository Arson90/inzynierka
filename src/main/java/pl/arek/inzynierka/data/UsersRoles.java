package pl.arek.inzynierka.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="users_roles")
@NoArgsConstructor
public class UsersRoles {

    @ManyToOne
    private UserInternal userInternal;
    private Roles role;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public UsersRoles(Roles role){
        this.role = role;
    }

    public UsersRoles(UserInternal userInternal, Roles role) {
        this.userInternal = userInternal;
        this.role = role;
    }
}
