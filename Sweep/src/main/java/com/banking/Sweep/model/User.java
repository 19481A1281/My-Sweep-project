package com.banking.Sweep.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

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

   @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;


    @OneToMany(mappedBy = "user",cascade = {CascadeType.MERGE,CascadeType.MERGE,CascadeType.REFRESH},orphanRemoval = false)        // if any changes made  associated account will also be effected
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


    public User(Long userId, String userName, String userEmail, String password, UserType userType) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.userType = userType;
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

    public  String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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
        ALL: Combines all the above operations.

*/


/*
@OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},orphanRemoval = false)
private List<Account> account;

Annotation: @OneToMany
This specifies a one-to-many relationship where:

One User can be associated with many Account instances.
The relationship is bidirectional since it references the user field in the Account entity.
Attributes Explained
mappedBy = "user"

Indicates that the Account entity is the owner of the relationship, not the User entity.
The user field in the Account entity is mapped to this relationship.
This prevents Hibernate from creating a redundant join table, as the relationship is already defined in the Account entity.
cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}

Specifies the cascading operations allowed on Account entities when performed on the User entity:
PERSIST: When a User is saved, the associated Account entities are also saved automatically.
MERGE: When a User is updated, the associated Account entities are also updated.
REFRESH: When a User is refreshed from the database, the associated Account entities are also refreshed.
Cascade operations simplify managing child entities, but be cautious to avoid unintended operations like cascading deletions.
orphanRemoval = false

If true, it would automatically delete any Account entities that are removed from the User's account list.
Here, false means orphaned Account entities are not automatically deleted; they remain in the database unless explicitly removed.
private List<Account> account;

This is the collection of Account entities associated with the User.
The List is a good choice when order matters; you could also use a Set if order isn’t important and duplicates need to be avoided.
How the Relationship Works
In the Account entity, you must define a @ManyToOne mapping like this:

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")  // Foreign key column in the Account table
private User user;
This sets up the relationship where:

The Account table will have a user_id column as a foreign key referencing the User table.
The mappedBy attribute in the User entity points to this user field in the Account entity.
Real-World Scenario
For example, if a User object has multiple Account objects, you can:

Add new Account objects to the User's account list, and they’ll be persisted to the database (due to CascadeType.PERSIST).
Update the User, and associated Account entities will also be updated (due to CascadeType.MERGE).
fetch = FetchType.LAZY: The User entity will not be loaded from the database until it is explicitly accessed (lazy loading).
This helps improve performance by deferring the loading of associated data.
*/
