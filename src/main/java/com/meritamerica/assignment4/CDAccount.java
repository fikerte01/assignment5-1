package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

   // CDAccount(child class) inherit methods and variables from BankAccount(parent class)
public class CDAccount extends BankAccount {
	private CDOffering offering;
	@NotNull
	@Positive
	private int terms;
	
	CDAccount(CDOffering offering, double openingBalance) {
		super(openingBalance, offering.getInterestRate());
		this.offering = offering;
	}	
	
	CDAccount(int term, double openingBalance, double interestRate) {
		this(new CDOffering(term, interestRate), openingBalance);
	}
	
	
	CDAccount(int accNumb, double openingBalance, double interestRate, Date openDate, int term) {
		super(accNumb, openingBalance, interestRate, openDate);
		this.offering = new CDOffering(term, interestRate);
	}	
	
	public CDAccount() {
		super();
		this.offering = new CDOffering();
	}
	
	public static CDAccount readFromString(String accountData) throws ParseException {
		String[] data = accountData.split(",");
		
		// Create a date formatter
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		int accNumb = Integer.parseInt(data[0]);
		double balance = Double.parseDouble(data[1]);
		double interestRate = Double.parseDouble(data[2]);
		Date openDate = formatter.parse(data[3]);
		int term = Integer.parseInt(data[4]);
	    
	    return new CDAccount(accNumb, balance, interestRate, openDate, term);
	}
		
	public double futureValue() {
		double futureVal = this.getBalance() * Math.pow(1 + this.getInterestRate(), offering.getTerm());
		return futureVal;
	}
	
	public int getTerm() {
		return offering.getTerm();
	}
	
	public void setTerm(int years) {
		this.terms = years;
		offering.setTerm(years);
	}
	
	@Override
	public void setInterestRate(double interestRate) throws FieldErrorException {
		super.setInterestRate(interestRate);
		offering.setInterestRate(interestRate);
	}
	
	public double getInterestRate() {
		return offering.getInterestRate();
	}
	
	
	@Override
	public String writeToString() {
		StringBuilder str = new StringBuilder();
		
		str.append(super.writeToString());
		str.append("," + offering.getTerm());
		return str.toString();
	}

	// CDA account can not do withdraw or deposit within the term period
	@Override
	public boolean withdraw(double amount) {
		return false;
	}

	@Override
	public boolean deposit(double amount) {
		return false;
	}
}
