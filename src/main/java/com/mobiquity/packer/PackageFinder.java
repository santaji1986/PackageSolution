package com.mobiquity.packer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobiquity.packer.model.FinalPackageDTO;
import com.mobiquity.packer.model.PackageDTO;
import com.mobiquity.packer.model.PackageDetails;

public class PackageFinder implements IFinder {
	@Override
	public FinalPackageDTO getIndexes(PackageDetails packageDTO) {
		List<PackageDTO> packageDTOs = packageDTO.getPackageDTOs();
		packageDTOs.sort((i1, i2) -> i1.getWeight().compareTo(i2.getWeight()));
		Double[] weights = packageDTOs.stream().map(i -> i.getWeight()).toArray(Double[]::new);
		Double[] prices = packageDTOs.stream().map(i -> i.getPrice()).toArray(Double[]::new);

		Boolean[] visited = new Boolean[packageDTOs.size()];
		Arrays.fill(visited, Boolean.FALSE);

		Double maxPrice = getMaxPrice(packageDTO.getWeight(), weights, prices, packageDTOs.size(), visited);

		List<PackageDTO> finalPackageDTOs = new ArrayList<PackageDTO>();
		for (int i = 0; i < packageDTOs.size(); i++) {
			if (visited[i]) {
				finalPackageDTOs.add(packageDTOs.get(i));
			}
		}
		finalPackageDTOs.sort((i1, i2) -> i1.getIndex().compareTo(i2.getIndex()));

		FinalPackageDTO finalPackageDTO = new FinalPackageDTO(maxPrice, finalPackageDTOs);

		return finalPackageDTO;
	}

	private static Double getMaxPrice(Double targetWeight, Double[] weights, Double[] prices, int size,
			Boolean[] visited) {
		if (size == 0 || targetWeight == 0)
			return 0.0;
		if (weights[size - 1] > targetWeight)
			return getMaxPrice(targetWeight, weights, prices, size - 1, visited);
		else {
			Boolean v1[] = new Boolean[visited.length];
			Boolean v2[] = new Boolean[visited.length];

			System.arraycopy(visited, 0, v1, 0, v1.length);
			System.arraycopy(visited, 0, v2, 0, v2.length);

			v1[size - 1] = true;

			Double maxPrice1 = prices[size - 1]
					+ getMaxPrice(targetWeight - weights[size - 1], weights, prices, size - 1, v1);
			Double maxPrice2 = getMaxPrice(targetWeight, weights, prices, size - 1, v2);

			if (maxPrice1 > maxPrice2) {
				System.arraycopy(v1, 0, visited, 0, v1.length);
				return maxPrice1;
			} else {
				System.arraycopy(v2, 0, visited, 0, v2.length);
				return maxPrice2;
			}
		}
	}
}
