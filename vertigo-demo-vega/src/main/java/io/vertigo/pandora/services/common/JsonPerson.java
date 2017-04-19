package io.vertigo.pandora.services.common;

public final class JsonPerson {

	private long code;
	private String fullName;
	private String firstName;
	private String lastname;
	private String biography;
	private String shortBiography;
	private String sex;
	private String photoURL;
	private String birthDate;
	private String birthPlace;
	private String activity;
	private String movies;

	public long getCode() {
		return code;
	}

	public String getFullName() {
		return fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public String getBiography() {
		return biography;
	}

	public String getShortBiography() {
		return shortBiography;
	}

	public String getSex() {
		return sex;
	}

	public String getActivity() {
		return activity;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public String getMovies() {
		return movies;
	}

}
