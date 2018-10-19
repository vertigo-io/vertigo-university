package io.mars.users.services;

import io.mars.domain.users.ApplicationUser;
import io.vertigo.core.component.Component;

public interface UserServices extends Component {

	ApplicationUser loginUser(final String login, final String password);

}
