package com.mobiquity.packer.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.mobiquity.packer.model.PackageDTO;
import com.mobiquity.packer.model.PackageDetails;

class LineParserTest {

	@Test
	void testParseLine() {
		final List<PackageDTO> dtos = Arrays.asList(new PackageDTO(1, 15.3, 34.0));
		PackageDetails expected = new PackageDetails(8.0, dtos);
		final String indexWeightPriceStr = "8 : (1, 15.3, €34)";
		final PackageDetails actual = LineParser.parseLine(indexWeightPriceStr);
		assertEquals(expected, actual);
	}

	@Test
	void testParseLineEmptyInput() {
		PackageDetails expected = null;
		final String indexWeightPriceStr = "";
		final PackageDetails actual = LineParser.parseLine(indexWeightPriceStr);
		assertEquals(expected, actual);
	}

	@Test
	void testParseLineNullInput() {
		PackageDetails expected = null;
		final String indexWeightPriceStr = null;
		final PackageDetails actual = LineParser.parseLine(indexWeightPriceStr);
		assertEquals(expected, actual);
	}

	@ParameterizedTest
	@ValueSource(strings = { "(1,,�45)", "(,53,�45)", "(,,�45)" })
	void testGetParsedPackagesExpectedException(String indexWeightPriceStr) {
		assertThrows(NumberFormatException.class, () -> LineParser.getParsedPackages(indexWeightPriceStr));
	}

	@ParameterizedTest
	@ValueSource(strings = { "(1,53.38,)", "(,,)" })
	void testGetParsedPackagesExpectedEmpty(String indexWeightPriceStr) {
		List<PackageDTO> parsedPackages = LineParser.getParsedPackages(indexWeightPriceStr);
		assertTrue(parsedPackages.size() == 0);
	}

}
