package com.pingr.accounts.Account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity //Notação do JPA para o Spring identificar como uma entidade
@Table //Notação do JPA para mapear a entidade em uma tabela especificamente
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    @Id //Notação do JPA definindo o id da tabela
    @SequenceGenerator(
            name = "account_seq_generator",
            sequenceName = "account_seq_generator",
            allocationSize = 1
    ) //Gerador de sequência, define o primeiro valor como 1
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_seq_generator"
    ) //Define que o valor gerado será sequêncial e aponta como o gerador sendo o sequenceGenerator definido anteriormente
    //Caso seja utilizado o mesmo gerador em duas tabelas do banco, ambas compartilharão o ID entre os dois registros,
    //por exemplo 'Account' recebe id 1, 'AdminAccount' recebe id 2, o novo registro do 'AdminAccount' receberá id 3
    //caso as duas entidades utilizem o mesmo sequenceGenerator
    private Long id;

    @Column(
            name = "username",
            nullable = false,
            unique = true
    )
    private String username;

    @Column(
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    public Account(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
