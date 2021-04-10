package com.mobiquity.packer.model;

import java.util.List;

import lombok.Value;

@Value
public class PackageDetails {
	private Double weight;
	private List<PackageDTO> packageDTOs;
}
