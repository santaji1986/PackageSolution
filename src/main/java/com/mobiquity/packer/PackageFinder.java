package com.mobiquity.packer;

import java.util.Comparator;
import java.util.List;

import com.mobiquity.packer.model.PackageDTO;
import com.mobiquity.packer.model.PackageDetails;

public class PackageFinder {

	public static String getIndexes(PackageDetails packageDTO) {
		String indexes = null;
		List<PackageDTO> packageDTOs = packageDTO.getPackageDTOs();
		packageDTOs.sort((o1, o2) -> o1.getWeight().compareTo(o2.getWeight()));
		System.out.println(packageDTOs);
		return indexes;
	}

}
