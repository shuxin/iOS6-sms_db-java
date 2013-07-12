package com.siata.smsmerge;

import com.siata.sms.DatabaseConnection;


public final class DatabaseHolder {

	private static volatile DatabaseHolder instance = null;

	public static DatabaseHolder getInstance() {
		if (instance == null) {
			synchronized (DatabaseHolder.class) {
				if (instance == null) {
					instance = new DatabaseHolder();
				}
			}
		}
		return instance;
	}

	private DatabaseHolder() {
	}

	// Database instances
	public DatabaseConnection remoteDatabase;
	public DatabaseConnection finalDatabase;

	public void closeApplication() {
		this.remoteDatabase.getFactory().close();
		this.finalDatabase.getFactory().close();
	}
}
