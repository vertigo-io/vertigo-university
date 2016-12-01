package io.vertigo.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertigo.samples.dto.StarbucksDto;

/**
 *
 * @author dt
 *
 */
public class StarbucksService {

	private StarbucksDto mapCsvToStarbucksGeoloc(final String line) {
		final String[] fields = line.split(";");

		final StarbucksDto ret = new StarbucksDto();
		ret.setBrand(fields[0]);
		ret.setStoreNumber(Integer.valueOf(fields[1]));
		ret.setName(fields[2]);
		ret.setOwnershipType(fields[3]);
		ret.setFacilityID(Integer.valueOf(fields[4]));
		ret.setFeaturesService(fields[5]);
		ret.setFeaturesStations(fields[6]);
		ret.setFoodRegion(fields[7]);
		ret.setVenueType(fields[8]);
		ret.setPhoneNumber(fields[9]);
		ret.setStreetAddress(fields[10]);
		ret.setStreetLine1(fields[11]);
		ret.setStreetLine2(fields[12]);
		ret.setCity(fields[14]);
		ret.setState(fields[15]);
		ret.setZip(fields[16]);
		ret.setCountry(fields[17]);
		ret.setCoordinates(fields[18]);
		ret.setLatitude(fields[19]);
		ret.setLongitude(fields[20]);
		ret.setInsertDate(fields[21]);
		return ret;
	}

	/**
	 *
	 * @return
	 * @throws IOException
	 */
	public List<StarbucksDto> getStarbucks(final Path path) throws IOException {
		try (Stream<String> lines = Files.lines(path)) {

			return lines.skip(1).map(line -> mapCsvToStarbucksGeoloc(line))
					.collect(Collectors.toList());
		}
	}

	public long countCity(final List<StarbucksDto> starbucks) {
		return starbucks.stream()
				.count();
	}

	public Map<String, Long> villeHistogram(final List<StarbucksDto> starbucks) {
		return starbucks.stream()
				.collect(Collectors.groupingBy(StarbucksDto::getCity, Collectors.counting()));
	}

	public Optional<String> villeAvecLePlusDeStarbucks(final List<StarbucksDto> starbucks) {
		final Optional<Map.Entry<String, Long>> opt = starbucks.stream()
				.collect(Collectors.groupingBy(StarbucksDto::getCity, Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue());

		return opt.map(Map.Entry::getKey);
	}

	public Optional<String> villeAvecLePlusDeStarbucksFilter(final List<StarbucksDto> starbucks, final Predicate<StarbucksDto> filterPredicate) {

		final Optional<Map.Entry<String, Long>> opt = starbucks.stream()
				.filter(filterPredicate)
				.collect(Collectors.groupingBy(StarbucksDto::getCity, Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue());

		return opt.map(Map.Entry::getKey);
	}

}
