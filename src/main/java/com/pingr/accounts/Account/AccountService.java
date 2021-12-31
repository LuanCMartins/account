package com.pingr.accounts.Account;

import com.pingr.accounts.Account.exceptions.InvalidAccountCreationException;
import com.pingr.accounts.Account.exceptions.InvalidArgumentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//No service vai todo o gerenciamento da lógica de aplicação. A notação Service, entre outras coisas, também diz que a classe é um Service e pode ser injetada
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final ProducerService producerService;
    private final AccountUpdatedProducer accountUpdatedProducer;
    private final AccountDeletedProducer accountDeletedProducer;

    @Autowired //Usada em cima do construtor, acessa os parâmetros do construtor, vê os tipos dos parâmetros,
    // e caso haja uma classe daquele tipo injetável, ele injeta. O autowired é um decorador para construtor.
    //Quando se usa um decorator, ele espera alguns metadados, o decorator @Autowired espera metadados de um construtor,
    // ou seja, não é possível utilizar ele em um atributo
    public AccountService(
            AccountRepository accountRepository,
            ProducerService producerService,
            AccountUpdatedProducer accountUpdatedProducer,
            AccountDeletedProducer accountDeletedProducer
    ) {
        this.accountRepository = accountRepository;
        this.producerService = producerService;
        this.accountUpdatedProducer = accountUpdatedProducer;
        this.accountDeletedProducer = accountDeletedProducer;
    }

    public Account createAccount(Account account) {
        if (account == null) throw new InvalidAccountCreationException("conta não pode ser nula");

        try {
            Account savedAccount = this.accountRepository.save(account);
            this.producerService.sendMessage(savedAccount);
            return savedAccount;
        } catch (Exception e) {
            throw new InvalidAccountCreationException("conta inválida para criação");
        }
    }

    public Account updateAccount(Account account) {
        if (account == null) throw new InvalidAccountCreationException("conta não pode ser nula");

        try {
            if(account.getId() != null) {
                Optional<Account> optionalAccount = this.accountRepository.findAccountById(account.getId());
                if (optionalAccount.isPresent()) {
                    Account contaEncontrada = optionalAccount.get();
                    //System.out.println("ENCONTROU CONTA: " + contaEncontrada.toString());
                    if (account.getUsername() != null) {
                        contaEncontrada.setUsername(account.getUsername());
                    }
                    if (account.getEmail() != null) {
                        contaEncontrada.setEmail(account.getEmail());
                    }
                    if (account.getPassword() != null) {
                        contaEncontrada.setPassword(account.getPassword());
                    }
                    //Tirar dúvida sobre o flush
                    this.accountRepository.saveAndFlush(contaEncontrada);
                    this.accountUpdatedProducer.sendMessage(contaEncontrada);
                    return contaEncontrada;
                }
            }
            //Tratar retorno depois
            return new Account();
        } catch (Exception e) {
            System.out.println("Erro ao Atualizar conta:");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Account deleteAccount(Long accountId) {
        if (accountId == null) throw new InvalidAccountCreationException("conta não pode ser nula");

        try {
            Optional<Account> optionalAccount = this.accountRepository.findAccountById(accountId);
            if (optionalAccount.isPresent()) {
                Account contaEncontrada = optionalAccount.get();
                this.accountRepository.delete(contaEncontrada);
                this.accountRepository.flush();
                //System.out.println("CONTA APAGADA");
                this.accountDeletedProducer.sendMessage(contaEncontrada);
                return contaEncontrada;
            }
            return new Account();
        } catch (Exception e) {
            System.out.println("Erro ao deletar conta:");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Account readAccount(Long accountId) {
        if (accountId == null) throw new InvalidAccountCreationException("conta não pode ser nula");

        try {
            Optional<Account> optionalAccount = this.accountRepository.findAccountById(accountId);
            if (optionalAccount.isPresent()) {
                return optionalAccount.get();
            }
            return new Account();
        } catch (Exception e) {
            System.out.println("Erro ao ler conta:");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<AccountIdAndUsername> searchByUsernameAlike(String usernameAlike) {
        if (usernameAlike.length() == 0) throw new InvalidArgumentsException("termo de busca vazio");

        return this.accountRepository.searchByUsernameAlike(usernameAlike);
    }
}
