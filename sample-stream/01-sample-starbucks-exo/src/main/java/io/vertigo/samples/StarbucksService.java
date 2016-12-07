package io.vertigo.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertigo.samples.dto.StarbucksDto;

/**
 *
 * @author dt
 *
 */
public class StarbucksService {

	private StarbucksDto mapCsvToStarbucks(final String line) {
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
	 * Construit une liste d'object StarbucksDto à partir d'un csv.
	 * @return
	 * @throws IOException
	 */
	public List<StarbucksDto> getStarbucks(final Path path) throws IOException {
		try (Stream<String> lines = Files.lines(path)) {

			return lines.skip(1).map(line -> mapCsvToStarbucks(line))
					.collect(Collectors.toList());
		}
	}

	/**
	 * Ecrit sur stdout le nom des Starbucks en paramètre.
	 * @param starbucks
	 */
	public void printStarbucks(final List<StarbucksDto> starbucks) {
		starbucks.stream()
				.forEach(s -> System.out.println(s.getName()));
	}

}
