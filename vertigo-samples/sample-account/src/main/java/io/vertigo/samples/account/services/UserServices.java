package io.vertigo.samples.account.services;

import io.vertigo.account.account.Account;
import io.vertigo.dynamo.store.StoreServices;

public interface UserServices extends StoreServices {

	Account login(final String login, final String password);

}
