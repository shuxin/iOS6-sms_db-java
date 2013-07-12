package com.siata.sms;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnection {

	private final String dbUser = "";
	private final String dbPass = "";
	private final String dbDriver = "org.sqlite.JDBC";
	private final String dbProfile = "ios6database";

	private EntityManagerFactory factory;

	public DatabaseConnection(String dbUrl) {
		super();
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("javax.persistence.jdbc.user", dbUser);
		vars.put("javax.persistence.jdbc.password", dbPass);
		vars.put("javax.persistence.jdbc.url", dbUrl);
		vars.put("javax.persistence.jdbc.driver", dbDriver);

		factory = Persistence.createEntityManagerFactory(dbProfile, vars);
	}

	public EntityManagerFactory getFactory() {
		return factory;
	}

}
