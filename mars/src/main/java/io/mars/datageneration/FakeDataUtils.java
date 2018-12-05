package io.mars.datageneration;

public class FakeDataUtils {
	public static <T extends Enum<?>> T randomEnum(final Class<T> clazz) {
		final int x = DataGenerator.rnd.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
}
