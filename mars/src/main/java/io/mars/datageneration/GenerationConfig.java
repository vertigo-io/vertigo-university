package io.mars.datageneration;

import java.util.Random;

public final class GenerationConfig {
	private static final Long RANDOM_SEED = 1337L;

	public static final int EQUIPMENT_TYPE_CSV_FILE_COLUMN_NUMBER = 3;
	public static final int PERSON_CSV_FILE_COLUMN_NUMBER = 5;
	public static final int BUSINESS_CSV_FILE_COLUMN_NUMBER = 1;
	public static final int GEOSECTOR_CSV_FILE_COLUMN_NUMBER = 1;
	public static final int EQUIPMENT_UNITS_TO_GENERATE = 1500;
	public static final Random rnd = new Random(RANDOM_SEED);

}
