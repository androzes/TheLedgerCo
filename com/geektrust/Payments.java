package com.geektrust;

import java.math.BigDecimal;
import java.lang.Comparable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

class Payments {

	// sorted set of installments paid in lumpsum
	public Set<Integer> installments;

	// lumpsum amount for installment number map
	public Map<Integer, BigDecimal> paymentMap;


	public Payments() {
		installments = new TreeSet();
		paymentMap = new HashMap();
	}

	public void add(int installmentNumber, BigDecimal lumpsumAmount) {
		if (!paymentMap.containsKey(installmentNumber)) {
			paymentMap.put(installmentNumber, lumpsumAmount);
			installments.add(installmentNumber);
		} else {
			paymentMap.put(installmentNumber, paymentMap.get(installmentNumber).add(lumpsumAmount));
		}
	}

	public BigDecimal getTotalLumpsumAmountTillInstallment(int installmentNumber) {
		BigDecimal sum = BigDecimal.ZERO;
		for (Integer i : installments) {
			if (i > installmentNumber) {
				break;
			}
			sum = sum.add(paymentMap.get(i));
		}
		return sum;
	}

}
