package io.vertigo.samples.crystal.services;

import io.vertigo.account.account.Account;
import io.vertigo.dynamo.store.StoreServices;

public interface LoginServices extends StoreServices {

	Account login(final String login, final String password);

}
