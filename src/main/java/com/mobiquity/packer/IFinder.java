package com.mobiquity.packer;

import com.mobiquity.packer.model.FinalPackageDTO;
import com.mobiquity.packer.model.PackageDetails;

public interface IFinder {

	FinalPackageDTO getIndexes(PackageDetails packageDTO);

}
