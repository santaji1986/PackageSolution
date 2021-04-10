package com.mobiquity.packer.validator;

import java.util.List;
import java.util.function.Predicate;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.PackageDTO;
import com.mobiquity.packer.model.PackageDetails;

public class InputValidator {
	public final static Double MAX_ITEM_WEIGHT = 100.0;
	public final static Double MAX_ITEM_COST = 100.0;
	public final static Integer ITEM_COUNT = 15;
	static Predicate<PackageDTO> itemPredicate = item -> item.getWeight() > MAX_ITEM_WEIGHT
												|| item.getPrice() > MAX_ITEM_COST;

	public static void validedInput(PackageDetails packageDTO) throws APIException {
		List<PackageDTO> dtos = packageDTO.getPackageDTOs();
		boolean isInvalidItem = dtos.stream().anyMatch(itemPredicate);
		if (dtos.size() > ITEM_COUNT || isInvalidItem) {
			// @formatter:off
			throw new APIException(
					"Max number of items allowed is " + ITEM_COUNT 
					+ ", item weight should not be more than " + MAX_ITEM_WEIGHT 
					+ ", price should not be more than " + MAX_ITEM_COST);
			// @formatter:on
		}

	}

}
