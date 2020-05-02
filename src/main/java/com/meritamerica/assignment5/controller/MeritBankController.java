package com.meritamerica.assignment5.controller;

import javax.validation.Valid;
import com.meritamerica.assignment4.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.exceptions.*;

@RestController
public class MeritBankController {
	@RequestMapping("/")
	@ResponseBody
	public String home() {
		return "Hi";
	}
	
	@PostMapping(value = "/AccountHolders") 
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public AccountHolder createAccountHolder(@RequestBody @Valid AccountHolder newAccountHolder) {
		MeritBank.addAccountHolder(newAccountHolder);
		return newAccountHolder;
	}
	
	@GetMapping(value="/AccountHolders")
	public AccountHolder[] getAccountHolders() {
		return MeritBank.getAccountHolders();
	}
	
	@GetMapping(value="/AccountHolders/{id}") 
	public AccountHolder getAccountHolder(@PathVariable("id") long id) throws NotFoundException
	{
		AccountHolder account = MeritBank.getAccountHolder(id);

		if (account == null) {
			throw new NotFoundException("No account exists");
		}
	
		return account;
	}
	
	@PostMapping(value="/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addChecking(@PathVariable("id") long id, @RequestBody CheckingAccount checking ) throws NotFoundException, ExceedsCombinedBalanceLimitException,
	NegativeAmountException
	{
//		AccountHolder account = MeritBank.getAccountHolder(id);
//		
//		if (account == null) {
//			throw new NotFoundException("No account exists");
//		}
				
		AccountHolder account = this.getAccountHolder(id);
		
		if (checking.getBalance() < 0) {
			throw new NegativeAmountException();
		}
		
		account.addCheckingAccount(checking);
		
		return checking;
	}
	
	@GetMapping(value="/AccountHolders/{id}/CheckingAccounts")
	public CheckingAccount[] getChecking(@PathVariable("id") long id) throws NotFoundException {
		AccountHolder account = this.getAccountHolder(id);
		
		return account.getCheckingAccounts();
	}
}
