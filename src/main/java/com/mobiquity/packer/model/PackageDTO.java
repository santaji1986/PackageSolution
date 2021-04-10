package com.mobiquity.packer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class PackageDTO {
	Integer index;
	Double weight;
	Double price;
}
