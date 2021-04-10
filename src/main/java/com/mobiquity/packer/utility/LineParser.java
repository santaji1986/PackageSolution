package com.mobiquity.packer.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobiquity.packer.model.PackageDTO;
import com.mobiquity.packer.model.PackageDetails;

public class LineParser {

	public static PackageDetails parseLine(String line) throws NumberFormatException {
		PackageDetails packageDetails = null;
		if (null != line && !line.isBlank()) {
			final String[] weightAndItems = line.split(":");
			final List<PackageDTO> packages = getParsedPackages(weightAndItems[1]);
			if (!packages.isEmpty())
				packageDetails = new PackageDetails(Double.parseDouble(weightAndItems[0]), packages);
		}
		return packageDetails;
	}

	public static List<PackageDTO> getParsedPackages(String indexWeightPriceStr) throws NumberFormatException {
		final Pattern parenthesisPattern = Pattern.compile("\\((.*?)\\)");
		final Matcher matcher = parenthesisPattern.matcher(indexWeightPriceStr);

		final List<PackageDTO> packages = new ArrayList<PackageDTO>();
		while (matcher.find()) {
			final String tuple = matcher.group(1);
			final String[] indexWeightPriceArr = tuple.split(",");
			PackageDTO packageDTO = getPackageDTO(indexWeightPriceArr);
			if (null != packageDTO)
				packages.add(packageDTO);
		}
		return packages;

	}

	public static PackageDTO getPackageDTO(final String[] indexWeightPriceArr) throws NumberFormatException {
		PackageDTO packageDTO = null;
		if (indexWeightPriceArr.length == 3) {
			// @formatter:off
            	packageDTO = PackageDTO.builder()
                                        .index(Integer.parseInt(indexWeightPriceArr[0]))
                                        .weight(Double.parseDouble(indexWeightPriceArr[1]))
                                        .price(Double.parseDouble(indexWeightPriceArr[2].replace("€", "")))
                                        .build();
            // @formatter:on
		}
		return packageDTO;
	}

}
