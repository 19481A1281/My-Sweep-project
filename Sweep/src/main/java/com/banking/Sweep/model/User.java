package com.banking.Sweep.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
//@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Size(min=2,max=25, message = "Name must be between 2 and 25 characters")
    private String userName;

    @NaturalId(mutable = true)
    @Email
    private String userEmail;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},orphanRemoval = false)        // if any changes made  associated account will also be effected
    private List<Account> account;

    //mappedBy = "user"
    /*Purpose: Indicates that this relationship is mapped by the user field in the Account entity.
     Details: The mappedBy attribute tells JPA that the User entity does not own the relationship.
     Instead, the Account entity owns the relationship via the user field.

    cascade = CascadeType.ALL

    Purpose: Specifies that any operation (persist, merge, remove, refresh, detach) performed on the User
    entity should also cascade to its associated Account entities.

    orphanRemoval = true
    Purpose: Ensures that if an Account is removed from the accounts list in the User entity,
    it will also be deleted from the database.
    Example:If you remove an Account object from the accounts list in the User object and then
    save the User entity, JPA will automatically delete the removed Account from the database.*/


    public User() {
    }


    public User(Long userId, String userName, String userEmail, List<Account> account) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.account = account;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }
}


/*cascade = CascadeType.ALL

Purpose: Specifies that any operation (persist, merge, remove, refresh, detach) performed on the User entity should also cascade to its associated Account entities.
Cascade Types:
PERSIST: Saves the child entities when the parent is saved.
        MERGE: Updates the child entities when the parent is updated.
        REMOVE: Deletes the child entities when the parent is deleted.
        REFRESH: Refreshes the state of the child entities when the parent is refreshed.
DETACH: Detaches the child entities when the parent is detached.
        ALL: Combines all the above operations.*/
