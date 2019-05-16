package io.mars.datageneration;

public final class DensityDistribution {

	private final RangeMap<Double, Double> myRangeMap = new RangeMap<>();

	public void addSegment(final double minValue, final double maxValue, final double density) {
		myRangeMap.addSegment(minValue, maxValue, density);
	}

	public double getDensity(final double event) {
		return myRangeMap.getValue(event);
	}

}
