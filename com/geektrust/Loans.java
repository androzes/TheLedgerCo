package com.geektrust;

import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;

class Loans {

	Map<String, Loan> loanMap;

	public Loans() {
		loanMap = new HashMap();
	}

	public void addLoan(String bankName, String borrowerName, BigDecimal principalAmount, BigDecimal timePeriod, BigDecimal interestRate) {
		String key = getKey(bankName, borrowerName);
		loanMap.put(key, new Loan(
			bankName, 
			borrowerName, 
			principalAmount, 
			timePeriod, 
			interestRate
		));
	}

	public void addPayment(String bankName, String borrowerName, BigDecimal amount, int emiNumber) {
		String key = getKey(bankName, borrowerName);
		checkLoanExists(key);
		loanMap.get(key).addPayment(emiNumber, amount);
	}

	public void balance(String bankName, String borrowerName, int emiNumber) {
		String key = getKey(bankName, borrowerName);
		checkLoanExists(key);
		BigDecimal paid = loanMap.get(key).getTotalPaidAmountTillInstallment(emiNumber);
		BigDecimal emiLeft = loanMap.get(key).getRemainingInstallmentsAfter(emiNumber);
		System.out.println(bankName + " " + borrowerName + " " + paid + " " + emiLeft);
	}

	private void checkLoanExists(String key) {
		if (!loanMap.containsKey(key)) {
			throw new IllegalStateException();
		}
	}

	private String getKey(String bankName, String borrowerName) {
		return bankName + ":" + borrowerName;
	}
}