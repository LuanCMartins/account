package com.pingr.accounts.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //Permite que a classe seja injetado e 'por baixo dos panos' faz outras coisas referentes à um repositório
public interface AccountRepository extends JpaRepository<Account, Long> {
    //O construtor do objeto que será retornado pelo Repository, deve aceitar os dados retornados, por exemplo, se for retornar apenas username e id do usuário, deve-se ter um
    //construtor na entidade 'Account' que receba apenas o username e o id
    @Query("SELECT acc FROM Account acc WHERE acc.username = ?1 OR acc.email = ?1")//Possível definir a consulta que será feita no banco, caso não se queira seguir o padrão da JPA
    Optional<Account> findAccountByUsernameOrByEmail(String usernameOrEmail);

    Optional<Account> findAccountByUsername(String username);//Como o nome do método segue um padrão, o JPA sabe buscar os dados mesmo sem o @Query

    Optional<Account> findAccountByEmail(String email);

    @Query(value = "SELECT id, username FROM Account WHERE username like %?1%", nativeQuery = true)
    List<AccountIdAndUsername> searchByUsernameAlike(String usernameAlike);

    Optional<Account> findAccountById(Long id);
    //
}
