package allocine;

import io.vertigo.app.App;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.pandora.boot.PandoraConfigurator;

public final class Crawler {

	public static void main(final String[] args) throws Exception {
		try (App app = new App(PandoraConfigurator.config(false))) {

			final AlloCineCrawler crawler = Injector.newInstance(AlloCineCrawler.class, app.getComponentSpace());
			crawler.display();
			crawler.crawl(100);
			crawler.display();
		}
	}

}
