# Framework d'injection de dependances basé sur les annotations

## Le but
le but de cette partie est de créer un framework comme spring qui permet de faire
**l'injection des dépendances**, à travers plusieurs **annotations** qu'on a créés.


## La structure de projet

notre projet est divisé sur deux modules:
* le premier module `DI-core` qui contient l'implementation du framework.
* le second module `DI-test` pour tester notre solution sur un cas réél.

![project-structure](./images/project-structure.png)


## le module DI-core 

dans ce module on a essayé d'implementer une solution qui permete 
de faire l'injection des dépendances, ce module est organisé comme ceci:

![DI-core-design](./images/di-core.png)

### package annotations
commencant par le package des `annotations`, on a créé plusieurs annotations 
qui seront détéctés au moment d'éxécution, ces annotations sont :

* `@Component` : cette annotation est distinée aux classes, que l'on souhaite les instancier, on peut spécifie le nom du bean qui sera créé:
```java
    @Target(METHOD)
    @Retention(RUNTIME)
    public @interface Bean {
        String value() default "";
    }
```
* `@BeansFactory`: cette annotation est similaire de l'annotation `@Configuration` du **spring** distiniée aux classes, 
    ces classes vont contient des méthodes spécifique (annoté par `@Bean`) qui vont applés au moment du démarrage de l'application
    pour créer un nouvean bean,comme **spring** généralement on utilise ce genre d'instanciation pour les classes qui 
    sont déja définis et qu'on ne peut pas les ajoutées l'annotation `@Component`.
```java
    @Target(TYPE)
    @Retention(RUNTIME)
    public @interface BeansFactory {
    }


```
* `@Bean`: équivalent de l'annotation `@Bean` celle de **Spring**, elle permet de créer des beans en exécutant la méthode annoté, 
    le nom du bean sera le nom passé en paramètre de l'annotation 
    , sinon le nom du bean sera le nom du méthode , ainsi le type du bean sera le type de retour de la méthode, on peut spécifier le nom du bean:
```java
    @Target(METHOD)
    @Retention(RUNTIME)
    public @interface Bean {
        String value() default "";
    }

```

* `@inject`: cette annotation permet d'injecter des beans dans les éléments annotés, qui sont :
    - les constructeurs
    - le attribus
    - les setters
```java
    @Target({FIELD, CONSTRUCTOR, METHOD})
    @Retention(RUNTIME)
    public @interface Inject {
    }
```
* `@Prefer`: cette annotation peut etre utilisé avec `@Inject`, pour favoriser ou spécifier qu'il est le bean
  (en donnant le nom du bean en paramètre) :
```java
    @Target({FIELD, PARAMETER})
    @Retention(RUNTIME)
    public @interface Prefer {
        String value();
    }
```




### package repository

dans ce package on a essayé d'implémenter `le pattern singleton` 
pour avoir une sorte d'une répositorie dans 
laquelle les notre bean créés seront stocké, on sait bien que 
la meuilleure facon d'implémenter le pattern singleton se sont des `Enumérations`, car ils ne permettent
de bien controler le nombre des instances qu'on veut dans notre application:

![Context-enum](./images/repo-pattern.png)

* `INSTANCE`: la seule instance qu'on a crée:
* `context`: c'est un attribut de type `Map<String, Object>`:
```java
    private final Map<String, Object> context = new ConcurrentHashMap<>();
```
* les autres méthodes comme : `size()` `saveBean(...)`, `beanExist(...)`, `getBean(...)`: pour manipuler l'attribut context.

### package resolvers

![resolvers](./images/Resolver-pattern.png)

ce package contient l'interface `BeanResolver`
qui permet de faire la résolution d'un bean à l'aide de son nom, ou son type 
(trover un bean qui a le meme type ou un sous type de type déclaré)
,c'est le role de la méthode `resolve`
,aussi elle permet de tester l'existence d'un bean dans notre repository Context
,c'est le role de la méthode `canBeResolved`:

```java
    public interface BeanResolver {
        Object resolve();
        boolean canBeResolved();
    }
```


l'importance de cette interface qu'elle résout le problème d'identification des beans, c'est pour cela on distingue deux
implémentations de cette interface :
* `BeanNameResolver` : qui permet d'identifier un bean a l'aide d'un nom.
* `BeanTypeResolver`: qui permet d'identifier un bean par un type, on peut accepter n'import
    qu'il bean de mem type ou d'un sous type:

l'interface `BeanResolver` est comme un promèsse ou pointeur sur un bean dans `Context Repository`, et lorsqu'on aura
besoin de ce bean et qu'il est disponible (`canBeResolver()` retournera true), il suffit juste d'invoquer `resolve()`
ces implémentations sont liées directemet à notre `Context Repository`.

le package contient aussi l'enregistrement `UnresolvedBean` qui est de type `record`, c'est un object immuable
sert juste à stocker la raison pour laquelle le bean n'est pas résolu :

```java
    public record UnresolvedBean(String raison) {}
```

la classe `UnresolvedBeans` est une classe `Factory` qui permit de convertir un `BeanResolver` vers une instance de type 
`UnresolvedBean`:


```java

public class UnresolvedBeans {
    private static String UNRESOLVED_BEAN_NAME = "unresolved bean with name: ";
    private static String UNRESOLVED_BEAN_TYPE = "unresolved bean with type: ";

    public static UnresolvedBean from(BeanResolver beanResolver){
        if (beanResolver instanceof BeanNameResolver beanNameResolver)
            return new UnresolvedBean(UNRESOLVED_BEAN_NAME + beanNameResolver.getName());
        return new UnresolvedBean(UNRESOLVED_BEAN_TYPE + ((BeanTypeResolver) beanResolver).getType().getName());
    }
}
```

`UnresolvedBeanException` est une classe aui modelise l'exception qui sera jete si le bean n'est pas trouve au cours d'initialiser ou d'injection des autres bean dont qu'ils depends:
```java
    public class UnresolvedBeanException extends RuntimeException{
    
        public UnresolvedBeanException(UnresolvedBean unresolvedBean) {
            super(unresolvedBean.raison());
        }
    }

```


### packages injectors

ce package contient :

![injectors](./images/injector-pattern.png)

* l'interface `Injector` permet de modeliser l'action d'injection d'un 
    bean dans un champs specifique, a travers un setter ou injection par attribut (field)

  cette interface dispose des methodes suivantes :
    - `inject` : injecter un bean dans un `champs`.
    - `canBeInjected` : verifie si la dependance est satisfaite, c'est a dire que le bean que l'on veut l'injecte est existe au niveau du `Context` repository.
    - `findFirstUnresolvedBean` : permet de retourner le premier bean qui n'existe pas dans le `Context` repo, encapsuler d'un enregistrement `UnresolvedBean`.

pour reqliser cette interface on a cree deux implementations :
* `FieldInjector` : qui modelise l'injection par attribut.
* `SetterInjector` : qui modelise l'injection par setter.

dans ce package aussi on dispose de la classe factory `Injectors` qui permet de creer un des object de type `Injector` 
on lui fourni des parametres qui convients.


### package initializer

![initializer](./images/intializer-pattern.png)

on a dit que le package `injectors` est mise en place pour implementer l'injection par setter ou par attribut (Field),
d'abord il faut comprendre une chose, ce que l'injection par setter ou par attribut s'effectues apres l'initialisation du bean,
est c'est tres facile a faire si les beans a injectes sont tous pretes, mais le probleme se provoque si l'injection se faite via
un constructeur (ou une methode annotee par l'annotation `@Bean`), pour cela on a utilise le pattern ullistre dans le diagramme de classe 
precedent.

* `Initializer` : est une interface qui modelise l'injection des dependences au moment de l'initialisation des classes via la methode `initialize`,
    la methode `canBeInitialized` c'est pour verifier est-ce que tous les dependences sont bien etablis ou pas,
    la methode `findFirstUnresolvedBean` a pour effet comme celle des injecteurs (definit dans l'interface `Injector`).
* pour la realisation de ctte interface, on distingue deux types d'implemetations :
    - `SimpleInitializer` : classe abstraite qui reflete les constructeurs sans parametres et qui ont annoté par `@Inject`, ou les methodes annotees par `@Bean` definitis dans les classes annotees 
        par l'annotation `@BeansFactory` et qui ne reçoivent aucune parametres.
        on a distingue deux types des initialisateurs sinples : `SimpleConstructorInitializer` et `SimpleMethodInitializer`.
    - `ParametricInitializer` : aussi, c'est une classe abstraite, et comme son nom montre, il diffère a `SimpleInitializer` par la presence d'une ensemble des parametres.
        on a distingué deux types des initialisateurs parametriques : `ParametricConstructorInitializer` et `ParametricMethodInitializer`.

* `Initalizers` : est une classe factory pour parser un intitalizer à partir des classes et des méthodes annotées.


### package scanners

![scanners](./images/scanner-pattern.png)

pour le scanning on a utilise l'outil `Reflections` fourni par la dependance :

```xml
        <dependency>
            <groupId>org.reflections</groupId>
            <artifactId>reflections</artifactId>
            <version>0.10.2</version>
        </dependency>
```

### package strategies

![strategies](./images/strategy-pattern.png)

### package converters

![converters](./images/converts-pattern.png)
### package core

![core](./images/application-context.png)

    


