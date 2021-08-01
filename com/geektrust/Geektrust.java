package com.geektrust;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

public class Geektrust {

	public static void main(String[] args)  {
		String filePath = args[0];
		Loans loans = new Loans();
		String line;

		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
		    while ((line = br.readLine()) != null) {
		    	processLine(line, loans);
		    }
		    br.close();
		} catch (FileNotFoundException ex) {
			System.out.println(ex.toString());
		} catch (IOException ex) {
			System.out.println(ex.toString());
		}
	}

	public static void processLine(String line, Loans loans) {
		String[] tokens = line.split("\\s+");
		String command = tokens[0];

		switch (command) {
			case "LOAN":
				BigDecimal interestRate = new BigDecimal(tokens[5]);
				interestRate = interestRate.divide(new BigDecimal(100));
				loans.addLoan(tokens[1], tokens[2], new BigDecimal(tokens[3]), new BigDecimal(tokens[4]), interestRate);
				break;
			case "PAYMENT":
				loans.addPayment(tokens[1], tokens[2], new BigDecimal(tokens[3]), Integer.parseInt(tokens[4]));
				break;
			case "BALANCE":
				loans.balance(tokens[1], tokens[2], Integer.parseInt(tokens[3]));
				break;
			default:
				break;
		}
	}

}