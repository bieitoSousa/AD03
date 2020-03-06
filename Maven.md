# Maven
## Nociones basicas ::

Maven é unha ferramenta que ten como obxectivo simplificar os procesos de build (compilar e xerar executables a partir do código fonte).
A parte de que esta é a principal característica de Maven e a que imos utilizar nos, tamén ten máis ferramentas nas que non imos entrar.

Maven fai que a xestión de dependencias entre módulos e as destintas versións de librerías sexa moi doada. So temos que indicar os módulos que compoñen o proxecto e as librerías que utiliza o noso programa no ficheiro de configuración de Maven (pon.xml).

No caso das liberías non temos nin que descargalas a man porque Maven ten un repositorio remoto (Maven Central) donde están a maioría de librerías que se utilizan para desenvolvemento de software e que a propia ferramenta descarga.

## Añadimos Dependecias ::

## Para JDB
```xml
		<dependencies>
            <dependency>
                <groupId>org.xerial</groupId>
                <artifactId>sqlite-jdbc</artifactId>
                <version>${version.sqlite}</version>
            </dependency>
```

## Para Json
```xml
   <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.6</version>
        </dependency>
    </dependencies>
```
## Añadimos Propiedades ::	
```xml
       <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <maven.compiler.source>1.8</maven.compiler.source>
            <maven.compiler.target>1.8</maven.compiler.target>
            <maven.compiler.version>3.8.1</maven.compiler.version>
            <maven.jar.version>3.2.0</maven.jar.version>
            <maven.mainClass>com.bieitosousa.ad03_db.Main</maven.mainClass>
            <version.sqlite>3.30.1</version.sqlite>
        </properties>
```