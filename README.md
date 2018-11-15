
# Spring injection within the Fitnesse framework.


## Features
Create an applicationContext in a Suite.Setup page in Fitnesse. Once the initial applicationContext
has been created, it can be used to inject beans into Fitnesse fixtures.

## Setup
```gradle
dependencies {
    compile('com.bigdullrock:fitnesse-spring:1.0.0')
}
```
```xml
<dependency>
    <groupId>com.bigdullrock</groupId>
    <artifactId>fitnesse-spring</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

## Usage
* Add `SymbolTypes` to the plugin.properties file

```text
SymbolTypes = com.bigdullrock.fitnesse.symbolsInheritClasspathSymbolType
```

* Add the classpath to your Suite:

```text
!define TEST_SYSTEM {slim}
!*> Class path for these examples
!inheritClasspath
*!
```

* If running tests via Maven and using the `fitnesse-maven-classpath` plugin, use this `SymbolTypes`

```text
SymbolTypes = fitnesse.wikitext.widgets.MavenClasspathSymbolType
```
* And this classpath:

```text
!define TEST_SYSTEM {slim}
!*> Class path for these examples
!pomFile /pom.xml@compile,test
*!
```

* Now we need to import the SpringContextFactory and tell it where to scan for classes.
Add the following import and script either to a SuiteSetup page or to the test page directly:

```text
!*> Suite Set Up For Scanning Base Packages

!|import                        |
|com.bigdullrock.fitnesse.spring|

!|script                            |spring context factory      |test|
|create spring context base packages|com.bigdullrock.fitnesse.sut     |
*!
```

* OR

```text
!*> Suite Set Up For Annotated Classes

!|import                        |
|com.bigdullrock.fitnesse.spring|

!|script                                |spring context factory                       |test|
|create spring context annotated classes|com.bigdullrock.fitnesse.sut.SystemUnderTestConfig|
*!
```

* OR

```text
!*> Suite Set Up For XML Files

!|import                        |
|com.bigdullrock.fitnesse.spring|

!|script                                 |spring context factory     |test|
|create spring context resource locations|classpath:applicationContext.xml|
*!
```

* OR

```text
!*> Suite Set Up For Spring Boot Application class

!|import                        |
|com.bigdullrock.fitnesse.spring|

!|script              |spring boot context factory                       |test|
|create spring context|com.bigdullrock.fitnesse.sut.SystemUnderTestApplication|
*!
```

* Now add `@Autowired` annotations to fields and a constructor that wires the object.

```java
import static com.bigdullrock.fitnesse.spring.SpringContextFactory.currentSpringContext;
// or use the following import for Spring Boot applications
// import static com.bigdullrock.fitnesse.spring.SpringBootContextFactory.currentSpringContext;

import org.springframework.beans.factory.annotation.Autowired;

public class ThingFixture {

  @Autowired
  private ThingDao ThingDao;

  private long thingId;

  public ThingFixture() {
    currentSpringContext().autowire(this);
  }

  public Thing getThingByThingId() {
    return thingDao.getThingByThingId(thingId);
  }

  public void setThingId(long thingId) {
    this.thingId = thingId;
  }
}
```

## Special Thanks
I copied a lot of this code from [Arnout Engelen](https://github.com/raboof/fitnesse-meetup-slimtables).
It looks like he made a code repository for a presentation he gave. I wanted to make that code into
a library and package it up for distribution.


## License
Apache 2.0
