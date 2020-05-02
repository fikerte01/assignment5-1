package com.meritamerica.assignment5.controller;

import javax.validation.Valid;
import com.meritamerica.assignment4.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment4.AccountHolder;

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
	public AccountHolder[] getAccountHolders() throws ExceedsCombinedBalanceLimitException {
		return MeritBank.getAccountHolders();
	}
}
