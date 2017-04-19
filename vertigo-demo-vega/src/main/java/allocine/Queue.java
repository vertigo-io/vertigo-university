package allocine;

import io.vertigo.dynamo.kvstore.KVStoreManager;
import io.vertigo.lang.Assertion;

import java.util.List;

public final class Queue {
	private final KVStoreManager kvStoreManager;

	public Queue(final KVStoreManager kvStoreManager) {
		Assertion.checkNotNull(kvStoreManager);
		//-----
		this.kvStoreManager = kvStoreManager;
	}

	public boolean enqueue(final String collection, final String code) {
		kvStoreManager.put(collection, code, new AlloCineId().setId(code));
		//System.out.println("|---add  queue " + collection + " : " + code);
		return true;
	}

	public String dequeue(final String collection) {
		final List<AlloCineId> list = kvStoreManager.findAll(collection, 0, 1, AlloCineId.class);
		if (list.isEmpty()) {
			return null;
		}
		final String code = list.get(0).getId();
		kvStoreManager.remove(collection, "" + code);
		System.out.println("|---dequeue " + collection + " : " + code);
		return code;
	}
}
