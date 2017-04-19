package io.vertigo.pandora.services.common;

public final class JsonMovie {

	private long code;
	private String title;
	private String originalTitle;
	private String synopsis;
	private String shortSynopsis;
	private String keywords;
	private String poster;
	private String trailerName;
	private String trailerHRef;
	private Integer runtime;
	private String movieType;
	private int productionYear;
	private int userRating;
	private int pressRating;
	private JsonPersonLink[] actors;
	private JsonPersonLink[] writers;
	private JsonPersonLink[] camera;
	private JsonPersonLink[] producers;
	private JsonPersonLink[] directors;

	public long getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public String getShortSynopsis() {
		return shortSynopsis;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public String getKeywords() {
		return keywords;
	}

	public String getTrailerName() {
		return trailerName;
	}

	public String getTrailerHRef() {
		return trailerHRef;
	}

	public Integer getRuntime() {
		return runtime;
	}

	public String getMovieType() {
		return movieType;
	}

	public int getProductionYear() {
		return productionYear;
	}

	public JsonPersonLink[] getActors() {
		return actors;
	}

	public String getPoster() {
		return poster;
	}

	public JsonPersonLink[] getDirectors() {
		return directors;
	}

	public JsonPersonLink[] getWriters() {
		return writers;
	}

	public JsonPersonLink[] getCamera() {
		return camera;
	}

	public JsonPersonLink[] getProducers() {
		return producers;
	}

	public int getUserRating() {
		return userRating;
	}

	public int getPressRating() {
		return pressRating;
	}
}
