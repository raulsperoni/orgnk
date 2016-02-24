# Nitro

## Ambiente

Instalar docker

Instalar docker-compose

Clonar este repositorio

Desde dentro del directorio del repositorio ejecutar el siguient comando:

```docker-compose compile```
```docker-compose up```

Para que quede de fondo:

```docker-compose up -d```

## Run

Ejecutar ```mvn package```

## Que hace?

El contenedor monta la carpeta deployments en el deployments del wildfy.
El target de maven copia el ear a esa carpeta.

## TODO:

* Lograr hacer deploy del driver de postgres y el datasource.
* Lograr conectar el intellij al docker y controlarlo con plugins de maven.
