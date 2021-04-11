package com.mobiquity.packer.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.PackageDTO;
import com.mobiquity.packer.model.PackageDetails;

class InputValidatorTest {
	@Test
	void testValidateInputSuccess() throws APIException {
		PackageDetails details = new PackageDetails(90.0, Arrays.asList(new PackageDTO(1, 53.38, 45.0)));
		assertTrue(InputValidator.validateInput(details));
	}

	@ParameterizedTest
	@MethodSource("packageDetailsProvider")
	void testValidateInputThrowsException(PackageDetails details) throws APIException {
		assertThrows(APIException.class, () -> InputValidator.validateInput(details));
	}

	static Stream<Arguments> packageDetailsProvider() {
		List<PackageDTO> list = new ArrayList<PackageDTO>();
		for (int i = 0; i < 16; i++) {
			list.add(new PackageDTO(i, 53.38, 45.0));
		}
		return Stream.of(Arguments.of(new PackageDetails(90.0, Arrays.asList(new PackageDTO(1, 153.38, 45.0)))),
				Arguments.of(new PackageDetails(90.0, Arrays.asList(new PackageDTO(1, 53.38, 145.0)))),
				Arguments.of(new PackageDetails(90.0, list)));
	}
}
