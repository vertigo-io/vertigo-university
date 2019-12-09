package io.vertigo.samples.quarto.services;

import java.util.Arrays;

import io.vertigo.core.node.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.util.VCollectors;
import io.vertigo.samples.quarto.domain.Card;
import io.vertigo.samples.quarto.domain.Category;
import io.vertigo.samples.quarto.domain.Status;
import io.vertigo.samples.quarto.domain.Theme;

public class ThemeProvider implements Component {

	public Theme getSampleTheme() {
		final Theme theme = new Theme();

		theme.setName("SampleTheme");
		final DtList<Category> categories = Arrays.asList(
				buildCategory("Test", "youjo",
						Arrays.asList(buildCard("carte1", "desc cart 1", "recommandé"))
								.stream().collect(VCollectors.toDtList(Card.class))))
				.stream()
				.collect(VCollectors.toDtList(Category.class));
		theme.setCategories(categories);

		return theme;

	}

	private static Category buildCategory(final String name, final String description, final DtList<Card> cards) {
		final Category category = new Category();
		category.setName(name);
		category.setDescription(description);
		category.setCards(cards);
		return category;
	}

	private static Card buildCard(final String name, final String description, final String statusName) {
		final Card card = new Card();
		card.setName(name);
		card.setDescription(description);
		final DtList<Status> statusList = new DtList<>(Status.class);
		final Status oneStatus = new Status();
		oneStatus.setName(statusName);
		statusList.add(oneStatus);
		card.setStatus(statusList);
		return card;
	}

}
