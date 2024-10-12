package com.example.seaSideEscape.model;
import jakarta.persistence.*;

@Entity
public class Guest extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Account account;

    private String address;
    private String creditCardNumber;
    private String creditCardExpiration;

    public Long getId() {return id;}

    public void setID(Long id){this.id = id;}

    public Account getAccount(){return account;}

    public void setAccount(Account account){this.account = account;}

    public String getAddress(){return address;}

    public void setAddress(String address){this.address = address;}

    public String getCreditCardNumber(){return creditCardNumber;}

    public void setCreditCardNumber(String creditCardNumber){this.creditCardNumber = creditCardNumber;}

    public String getCreditCardExpiration(){return creditCardExpiration;}

    public void setCreditCardExpiration(String creditCardExpiration){this.creditCardExpiration = creditCardExpiration;}
}
