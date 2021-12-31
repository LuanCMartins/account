package com.pingr.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}

//docker container run --rm --name spring-pg -e POSTGRES_PASSWORD=secret -e POSTGRES_USER=root -p 5432:5432 -d postgres
//docker container exec -it spring-pg psql
//CREATE DATABASE accounts;
//GRANT ALL PRIVILEGES ON DATABASE "accounts" TO root;