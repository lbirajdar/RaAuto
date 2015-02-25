package com.waas.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class UserOutput.
 */
public class UserOutput {

	/** The file name. */
	private String fileName;

	/** The output folder. */
	private String outputFolder;

	/** The file. */
	private File file;

	/** The writer. */
	private Writer writer;

	/** The date format txt. */
	private DateFormat dateFormatTxt;

	/** The date format dir. */
	private DateFormat dateFormatDir;

	/** The date. */
	private Date date;

	/** The meta_data_file. */
	private String meta_data_file;

	/**
	 * Instantiates a new user output and enabled the script to write to the new
	 * file when users wishes to write the output for later use
	 *
	 * @param meta_data_file
	 *            the meta_data_file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public UserOutput(String meta_data_file) throws IOException {

		// removing the path
		String outputFile = meta_data_file.substring(
				meta_data_file.lastIndexOf(File.separator) + 1,
				meta_data_file.length());
		if (outputFile.indexOf("\\") != -1) {

			outputFile = outputFile.substring(outputFile.lastIndexOf("\\") + 1,
					outputFile.length());
			

		}
		if (outputFile.indexOf("/") != -1) {

			outputFile = outputFile.substring(outputFile.lastIndexOf("/") + 1,
					outputFile.length());
			

		}

		

		// removing .xml from the end
		this.meta_data_file = outputFile.substring(0, outputFile.indexOf("."));

		date = new Date();

		File directory = new File(getOutputFolder());
		if (!directory.exists()) {
			directory.mkdir();
		}

		file = new File(getOutputFileName());
		System.out.println("User output is being printed to :"
				+ file.getAbsolutePath());

		writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));

	}

	/**
	 * Writes to UserOutput.
	 *
	 * @param txt
	 *            the txt
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void write(String txt) throws IOException {
		writer.append(txt + " | ");
		writer.write(System.getProperty("line.separator"));
	}

	/**
	 * Closes the connection to UserOutput
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void close() throws IOException {
		writer.close();
	}

	private String getOutputFolder() {

		dateFormatDir = new SimpleDateFormat("MM-dd-yy");
		String useroutputBase = "useroutput";
		File directory = new File(useroutputBase);
		if (!directory.exists()) {
			directory.mkdir();
		}
		outputFolder = useroutputBase + File.separator
				+ dateFormatDir.format(date);

		return outputFolder;
	}

	private String getOutputFileName() {

		dateFormatTxt = new SimpleDateFormat("MM-dd-yy_HH-mm-ss");
		fileName = outputFolder + File.separator + meta_data_file + "_"
				+ dateFormatTxt.format(date) + ".txt";

		return fileName;
	}

	/**
	 * Gets the result folder.
	 *
	 * @param basePath
	 *            the base path
	 * @return the result folder
	 */
	public static String getResultFolder(String basePath) {

		SimpleDateFormat dateFormatDir = new SimpleDateFormat(
				"MM-dd-yy_HH-mm-ss");

		String resultOoutputFolder;

		if (basePath != null)

			resultOoutputFolder = basePath + File.separator
					+ dateFormatDir.format(new Date());

		else

			resultOoutputFolder = "output_result" + File.separator
					+ dateFormatDir.format(new Date());

		System.out
				.println("Results will be stored at : " + resultOoutputFolder);

		return resultOoutputFolder;

	}

}
