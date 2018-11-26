package io.mars.datageneration;

import io.vertigo.lang.Assertion;

public class DensityDistribution<T extends Comparable<T>> {
	private RangeMap<T, Double> myRangeMap;

	public void addSegment(final T minValue, final T maxValue, final double density) {
		Assertion.checkNotNull(myRangeMap);
		Assertion.checkNotNull(minValue);
		Assertion.checkNotNull(maxValue);
		//
		myRangeMap.addSegment(minValue, maxValue, density);
	}

	public Double getDensity(final T event) {
		Assertion.checkNotNull(event);
		//
		return myRangeMap.getValue(event);
	}

}
