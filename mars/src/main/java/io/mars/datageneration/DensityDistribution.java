package io.mars.datageneration;

import io.vertigo.lang.Assertion;

public class DensityDistribution {
	private final RangeMap<Double, Double> myRangeMap = new RangeMap<>();

	public void addSegment(final Double minValue, final Double maxValue, final double density) {
		Assertion.checkNotNull(myRangeMap);
		Assertion.checkNotNull(minValue);
		Assertion.checkNotNull(maxValue);
		//
		myRangeMap.addSegment(minValue, maxValue, density);
	}

	public Double getDensity(final Double event) {
		Assertion.checkNotNull(event);
		//
		return myRangeMap.getValue(event);
	}

}
