package io.mars.datageneration;

public final class FakeDataUtil {
	public static <T extends Enum<?>> T randomEnum(final Class<T> clazz) {
		final int x = DataGenerator.RND.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
}
