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

	/**
	 * Retourne le nombre de Starbuck en paramètre
	 * @param starbucks
	 * @return
	 */
	public long countStarbucks(final List<StarbucksDto> starbucks) {
		return starbucks.stream()
				.count();
	}

	/**
	 * Retourne la liste des nom de Starbucks d'un état
	 * @param starbucks
	 * @return
	 */
	public List<String> filterStarbucksState(final List<StarbucksDto> starbucks, final String stateCode) {
		return starbucks.stream()
				.filter(s -> stateCode.equals(s.getState()))
				.map(StarbucksDto::getName)
				.collect(Collectors.toList());
	}

	/**
	 * Retourne l'histogramme (table de dréquence) des Starbucks par ville
	 * @param starbucks
	 * @return
	 */
	public Map<String, Long> villeHistogram(final List<StarbucksDto> starbucks) {
		return starbucks.stream()
				.collect(Collectors.groupingBy(StarbucksDto::getCity, Collectors.counting()));
	}

	/**
	 * Retourne la ville ayant le plus de Starbucks
	 * @param starbucks
	 * @return
	 */
	public Optional<String> villeAvecLePlusDeStarbucks(final List<StarbucksDto> starbucks) {
		final Optional<Map.Entry<String, Long>> opt = starbucks.stream()
				.collect(Collectors.groupingBy(StarbucksDto::getCity, Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue());

		return opt.map(Map.Entry::getKey);
	}

	/**
	 * Retourne la ville ayant le plus de Starbucks, filtrée selon un critère
	 * @param starbucks
	 * @param filterPredicate
	 * @return
	 */
	public Optional<String> villeAvecLePlusDeStarbucksFilter(final List<StarbucksDto> starbucks, final Predicate<StarbucksDto> filterPredicate) {

		final Optional<Map.Entry<String, Long>> opt = starbucks.stream()
				.filter(filterPredicate)
				.collect(Collectors.groupingBy(StarbucksDto::getCity, Collectors.counting()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue());

		return opt.map(Map.Entry::getKey);
	}

	/**
	 * Retourne la liste des n premiers couples (ville, fréquence) ayant le plus de Starbucks, triés par ordre desc.
	 * @param starbucks
	 * @param nFirst
	 * @return
	 */
	public List<Map.Entry<String, Long>> villeNMax(final List<StarbucksDto> starbucks, final int nFirst) {

		final List<Map.Entry<String, Long>> ret = starbucks.stream()
				.collect(Collectors.groupingBy(StarbucksDto::getCity, Collectors.counting()))
				.entrySet()
				.stream()
				.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
				.limit(nFirst)
				.collect(Collectors.toList());

		return ret;
	}

}
