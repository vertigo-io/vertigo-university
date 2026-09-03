# sample-component

Composants Vertigo et injection de dépendances : composants simples, API, paramètres, activeables, plugins et aspects AOP.

## Démarrage

- Lancer la classe main `io.vertigo.samples.Samples`
- Exécute la progression `step1()` → `step8()` (Calculateurs 1 à 8), résultats en console

## Contenu

- `samples.components.a_basics` : composants de base (Calculator1→4)
- `samples.components.b_plugins` : composants paramétrés par plugins (Calculator5→6)
- `samples.components.c_aop` : aspects (Spy) sur les composants (Calculator7→8)
- `samples.plugins` : les plugins d'opération (Min/Max/Sum/Mult)
- `samples.aspects` : l'aspect Spy et son manager
- `samples.config` : la configuration du node
