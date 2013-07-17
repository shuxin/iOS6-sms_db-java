package com.siata.smsmerge;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.siata.sms.DatabaseConnection;

public class ApplicationPrepare {

	public static final String jdbcString = "jdbc:sqlite:";
	private CopyOption REPLACE_EXISTING = StandardCopyOption.REPLACE_EXISTING;

	public ApplicationPrepare(File... files) {

		// Create empty DB
		// File mother = new File("db/mother/new.sqlite");

		if (files.length > 1) {
			File result = new File(files[0].toPath().getParent()+"/result.sqlite");
			if (result.exists()) {
				result.delete();
			}
			try {
				Files.copy(files[0].toPath(), result.toPath(), REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			DatabaseHolder.getInstance().finalDatabase = new DatabaseConnection(
					jdbcString + result.getPath());
			DatabaseHolder.getInstance().remoteDatabase = new DatabaseConnection(
					jdbcString + files[1].getPath());
		} else {
			DatabaseHolder.getInstance().finalDatabase = new DatabaseConnection(
					jdbcString + files[0].getPath());
		}
	}
}
