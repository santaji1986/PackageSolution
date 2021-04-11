package com.mobiquity.packer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.FinalPackageDTO;
import com.mobiquity.packer.model.PackageDetails;
import com.mobiquity.packer.utility.LineParser;
import com.mobiquity.packer.validator.InputValidator;

public class Packer {
	/*
	 * Strategy Design Pattern
	 */
	private static IFinder finder = new PackageFinder();

	private Packer() {
	}

	public static String pack(String filePath) throws APIException {
		final StringBuilder output = new StringBuilder();
		if (null == filePath || filePath.isBlank()) {
			throw new APIException("Filepath is " + filePath);
		}
		try {
			final List<String> lines = Files.readAllLines(Path.of(filePath));
			for (final String line : lines) {
				final String indexes = getPackageIndexes(line);
				output.append(indexes).append("\r\n");
			}
		} catch (final IOException | APIException e) {
			if (e instanceof APIException)
				throw (APIException) e;
			else
				throw new APIException("Error occured while reading file.");
		}
		return output.toString();
	}

	public static String getPackageIndexes(String line) throws APIException {
		String indexes = null;
		PackageDetails packageDTO = LineParser.parseLine(line);
		if (InputValidator.validateInput(packageDTO)) {

			FinalPackageDTO finalPackageDTO = null;
			if (null != packageDTO && !packageDTO.getPackageDTOs().isEmpty()) {
				finalPackageDTO = finder.getIndexes(packageDTO);
			}
			if (finalPackageDTO.getPackageDTOs().isEmpty()) {
				indexes = "-";
			} else {
				indexes = finalPackageDTO.getPackageDTOs().stream().map(i -> i.getIndex().toString())
						.collect(Collectors.joining(","));
			}
		}
		return indexes;
	}
}
