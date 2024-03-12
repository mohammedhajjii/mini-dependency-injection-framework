# Framework d'injection de dependances basé sur les annotations

## Le but
le but de cette partie est de créer un framework comme spring qui permet de faire
**l'injection des dépendances**, à travers plusieurs **annotations** qu'on a créés.


## La structure de projet

notre projet est divisé sur deux modules:
* le premier module `DI-core` qui contient l'implementation du framework.
* le second module `DI-test` pour tester notre solution sur un cas réél.

![project-structure](C:\Users\mdhaj\Desktop\DI_FRAMEWORK_SCREENS\project-structure.png)


## le module DI-core 

dans ce module on a essayé d'implementer une solution qui permete 
de faire l'injection des dépendances, ce module est organisé comme ceci:

![DI-core-design](C:\Users\mdhaj\Desktop\DI_FRAMEWORK_SCREENS\di-core.png)


commencant par le package des `annotations`:
