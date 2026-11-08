# Level Socle 2 - Régénérer le code

Nous allons régénérer le code de l'application avec Studio, et voir ce qu'il lit et ce qu'il produit.
C'est le geste quotidien du développement Vertigo : le modèle se décrit dans des fichiers de définitions, et le code qui en découle est généré.

À la fin de ce Level, vous saurez :
- lancer la génération ;
- retrouver ce que Studio lit et ce qu'il produit ;
- vérifier qu'une régénération sans modification du modèle ne change rien.

## Eléments

- Configuration de la génération : `/studio-config.yaml`
- Définitions : `/src/main/resources/definitions/application.kpr` et les fichiers `.ksp` qu'il liste
- Code généré : `/src/main/javagen`
- Lanceur : `io.vertigo.samples.support.mda.StudioGenerate`
- Concepts illustrés : MDA, KSP, KPR *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*
- Documentation : [vertigo-docs : MDA](https://vertigo-io.github.io/vertigo-docs/#/basic/mda)

### A connaitre : Studio

Studio applique le principe du **MDA** (*Model Driven Architecture*) : le modèle est la source, le code en est déduit. Il lit sa configuration dans `studio-config.yaml` :
- `resources` : le fichier `.kpr`, qui liste les fichiers de définitions `.ksp` à lire, et `masterDataValues.json`, les valeurs des listes de référence statiques ;
- `mdaConfig` : le package de l'application, le répertoire cible, et dans `properties` ce qu'il faut générer : les classes du domaine (`vertigo.domain.java`), le SQL de création de la base pour H2 (`vertigo.domain.sql`), les tâches (`vertigo.task`), la recherche (`vertigo.search`) et un schéma du modèle (`mermaid`).

Il produit dans `src/main/javagen` :
- `domain` : une classe par objet du modèle, et `DtDefinitions` qui les référence ;
- `dao` : un DAO par entité ;
- `VuiPAO` : les tâches qui ne relèvent d'aucun DAO (voir le Level Socle 4) ;
- `search` : le client de la recherche ;
- `sqlgen/crebas.sql` : la création de la base ;
- `mermaid` : le schéma du modèle, à ouvrir dans un navigateur.

**Attention** : le code de `src/main/javagen` ne se modifie jamais à la main. Il est écrasé à chaque génération : si le code est faux, c'est le modèle qui est faux.

## Etapes

1. Ouvrez `studio-config.yaml`, puis `application.kpr`.
**Question** : combien de fichiers de définitions Studio lit-il ?
1. Parcourez `src/main/javagen`.
**Question** : quels objets du modèle ont un DAO ? Repérez ceux qui n'en ont pas : vous saurez pourquoi au Level Socle 3.
1. Lancez `StudioGenerate`, **depuis la racine du module** : il lit `studio-config.yaml` en chemin relatif *(voir le [README](./README.md#lancer-une-classe-main) pour lancer une classe main)*. La console se termine par : `Création de 22 fichiers, Mise à jour de 0 fichiers, 0 fichiers identiques et 0 problemes`.
1. **Observez** `git status` : aucun fichier n'est modifié. Studio a réécrit les 22 fichiers (son compteur les annonce comme des créations), mais leur contenu est identique : le code généré est le reflet exact des définitions.
1. Ouvrez `src/main/javagen/mermaid/mermaid-io-vertigo-samples-vui.html` dans un navigateur : c'est le schéma du modèle que nous allons lire au Level suivant.

### Ce qu'il faut retenir

- Studio lit les définitions listées par le `.kpr`, selon `studio-config.yaml`, et génère le code dans `src/main/javagen`.
- Régénérer sans changer le modèle ne change rien : la génération est reproductible.
- Le code généré ne se modifie jamais à la main : on modifie le modèle, puis on régénère.

# [Suite : Level Socle 3 - Le modèle](./LevelSocle3.md)
