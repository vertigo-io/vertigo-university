package io.vertigo.samples.account.authorization;

import io.vertigo.account.authorization.metamodel.Authorization;
import io.vertigo.account.authorization.metamodel.AuthorizationName;
import io.vertigo.account.authorization.metamodel.OperationName;
import io.vertigo.core.node.Node;
import io.vertigo.samples.account.domain.Movie;

/**
 * Warning. This class is generated automatically !
 *
 * Enum of the security authorizations and operations associated with secured entities known by the application.
 */
public final class SecuredEntities {

	private SecuredEntities() {
		//private constructor
	}

	/**
	 * Authorizations of Movie.
	 */
	public enum MovieAuthorizations implements AuthorizationName {
		/** read. */
		AtzMovie$read;

		/**
		 * Get the associated authorization.
		 *
		 * @param code authorization code
		 * @return authorization
		 */
		public static Authorization of(final String code) {
			return Node.getNode().getDefinitionSpace().resolve(code, Authorization.class);
		}

		/**
		 * Get the associated authorization.
		 *
		 * @return role
		 */
		public Authorization getAuthorization() {
			return of(name());
		}
	}

	/**
	 * Operations of Movie.
	 */
	public enum MovieOperations implements OperationName<Movie> {
		/** read. */
		read;
	}
}
