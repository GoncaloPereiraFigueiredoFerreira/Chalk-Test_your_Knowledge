package pt.uminho.di.chalktyk.models.nonrelational.payments;

import java.time.LocalDate;

public class CreditCard extends Payment {
	private String name;
	private String number;
	private String cvv;
	private LocalDate expirationDate;
}