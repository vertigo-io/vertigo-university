package io.mars.support.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.BiConsumer;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.vertigo.core.resource.ResourceManager;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;

public final class CSVReaderUtil {
	public static void parseCSV(final ResourceManager resourceManager, final String csvFilePath, BiConsumer<String, String[]> recordConsumer) {
		Assertion.checkNotNull(resourceManager);
		Assertion.checkNotNull(csvFilePath);
		Assertion.checkNotNull(recordConsumer);
		//---
		try (Reader reader = new BufferedReader(new InputStreamReader(resourceManager.resolve(csvFilePath).openStream()));
				CSVReader csvReader = new CSVReaderBuilder(reader)
						.withCSVParser(new CSVParserBuilder()
								.withSeparator(';')
								.build())
						.withSkipLines(1)
						.build();) {
			String[] record;
			while ((record = csvReader.readNext()) != null) {
				recordConsumer.accept(csvFilePath, record);
			}
		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't load csv file {0}", csvFilePath);
		}
	}
}
