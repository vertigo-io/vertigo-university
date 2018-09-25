package io.vertigo.mars.services.users;

import io.vertigo.core.component.Component;
import io.vertigo.mars.domain.users.ApplicationUser;

public interface UserServices extends Component {

	ApplicationUser loginUser(final String login, final String password);

}
