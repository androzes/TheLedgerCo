package com.geektrust;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeSet;

class Loan {

	private static final int MONTHS_IN_YEAR = 12;
	private static final int SCALE = 10;

	private String bankName;

	private String borrowerName;

	private BigDecimal principalAmount;

	// in years
	private BigDecimal timePeriod; 

	// rate per annum (0.09 for 9%)
	private BigDecimal interestRate;

	// lumpsum payment after installment i
	private Payments payments;

	public Loan (String bankName, String borrowerName, BigDecimal principalAmount, BigDecimal timePeriod, BigDecimal interestRate) {
		this.bankName = bankName;
		this.borrowerName = borrowerName;
		this.principalAmount = principalAmount;
		this.timePeriod = timePeriod;
		this.interestRate = interestRate;
		this.payments = new Payments();
	}

	private BigDecimal getTotalAmount() {
		return principalAmount.add(getInterestAmount());
	}

	private BigDecimal getInterestAmount() {
		return principalAmount.multiply(timePeriod).multiply(interestRate);
	}

	private BigDecimal getMonthlyInstallmentAmount() {
		return getTotalAmount()
		.divide(timePeriod.multiply(new BigDecimal(MONTHS_IN_YEAR)), SCALE, RoundingMode.CEILING)
		.setScale(0, RoundingMode.CEILING);
	}

	public void addPayment(int installmentNumber, BigDecimal lumpsumAmount) {
		payments.add(installmentNumber, lumpsumAmount);
	}

	public BigDecimal getTotalPaidAmountTillInstallment(int installmentNumber) {
		return getMonthlyInstallmentAmount()
			.multiply(new BigDecimal(installmentNumber))
			.add(payments.getTotalLumpsumAmountTillInstallment(installmentNumber));
	}

	private BigDecimal getBalanceAmountTillInstallment(int installmentNumber) {
		return getTotalAmount().subtract(getTotalPaidAmountTillInstallment(installmentNumber));
	}

	public BigDecimal getRemainingInstallmentsAfter(int installmentNumber) {
		return getBalanceAmountTillInstallment(installmentNumber)
		.divide(getMonthlyInstallmentAmount(), SCALE, RoundingMode.CEILING)
		.setScale(0, RoundingMode.CEILING);
	}

}