package io.vertigo.samples.quarto.services;

import java.util.Arrays;

import io.vertigo.core.node.component.Component;
import io.vertigo.datamodel.data.model.DataList;
import io.vertigo.datamodel.data.util.VCollectors;
import io.vertigo.samples.quarto.domain.Card;
import io.vertigo.samples.quarto.domain.Category;
import io.vertigo.samples.quarto.domain.Status;
import io.vertigo.samples.quarto.domain.Theme;

public class ThemeProvider implements Component {

	public Theme getSampleTheme() {
		final Theme theme = new Theme();

		theme.setName("SampleTheme");
		final DataList<Category> categories = Arrays.asList(
				buildCategory("Test", "youjo",
						Arrays.asList(buildCard("carte1", "desc cart 1", "recommandé"))
								.stream().collect(VCollectors.toDataList(Card.class))))
				.stream()
				.collect(VCollectors.toDataList(Category.class));
		theme.setCategories(categories);

		return theme;

	}

	private static Category buildCategory(final String name, final String description, final DataList<Card> cards) {
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
		final DataList<Status> statusList = new DataList<>(Status.class);
		final Status oneStatus = new Status();
		oneStatus.setName(statusName);
		statusList.add(oneStatus);
		card.setStatus(statusList);
		return card;
	}

}
