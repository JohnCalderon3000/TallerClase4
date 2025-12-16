# Login en App Bancaria (Selenium Java)

Proyecto de automatización con Selenium WebDriver 4, Java 17 y TestNG.

Requisitos:
- Java 17
- Maven

Estructura mínima:
- `src/main/java/pages/LoginPage.java` - Page Object
- `src/test/java/tests/LoginTest.java` - TestNG test

Ejecutar tests:

1) Desde la raíz del proyecto (donde está `pom.xml`):

```bash
mvn test
```

2) Ejecutar en headless:

```bash
mvn test -Dheadless=true
```

Capturas de pantalla en fallos se guardan en la carpeta `screenshots`.
