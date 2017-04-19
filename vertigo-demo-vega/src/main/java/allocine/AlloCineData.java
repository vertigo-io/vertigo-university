package allocine;

import java.io.Serializable;

public class AlloCineData implements Serializable {
	private static final long serialVersionUID = 8240495508734492080L;
	private String data;

	public AlloCineData setData(final String data) {
		this.data = data;
		return this;
	}

	public String getData() {
		return data;
	}

}
