# sample-quarto

Vertigo Quarto : génération de documents — un PublisherManager remplit un modèle Word à partir des données du domaine et sort un .docx.

## Démarrage

- Lancer la classe main `io.vertigo.samples.quarto.SampleQuarto` : publie un thème et copie le .docx généré dans le répertoire courant

## Contenu

- `quarto.domain` : Card/Category/Status/Theme + ThemeProvider
- `quarto.services` : le ThemeProvider
- `quarto.config` : PublisherDefinitionProvider + SmartTypes
- `src/main/resources/models/` : le modèle Word (ThemeModel.docx)
