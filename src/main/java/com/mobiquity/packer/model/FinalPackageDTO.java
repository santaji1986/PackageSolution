package com.mobiquity.packer.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class FinalPackageDTO {
	Double maxPrice;
	List<PackageDTO> packageDTOs;
}
