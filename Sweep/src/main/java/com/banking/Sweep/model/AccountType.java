package com.banking.Sweep.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

//@Entity
//@Table(name="accountType")
/*public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountTypeId;

    @NaturalId
    @Size(min=3, max=10, message = "TypeofAccount must be between 3 and 10 characters")
    private String accountTypeName;

//    @OneToMany(mappedBy = "accountType",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Account> accounts;


    public AccountType() {
    }

    public AccountType(Long accountTypeId, String accountTypeName) {
        this.accountTypeId = accountTypeId;
        this.accountTypeName = accountTypeName;
    }

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public @Size(min = 3, max = 10, message = "TypeofAccount must be between 3 and 10 characters") String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(@Size(min = 3, max = 10, message = "TypeofAccount must be between 3 and 10 characters") String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }
}
*/
public enum AccountType{
    SAVINGS,
    CURRENT,
    BUSINESS,
    LOAN,
    FIXED_DEPOSIT
}