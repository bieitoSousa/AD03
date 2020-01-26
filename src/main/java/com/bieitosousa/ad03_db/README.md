#Tarefa AD03
Imos partir da tarefa AD02 modificando certas cousas e engadindo novas funcionalidades.
##Descripción do problema
-------------
A situación é a seguinte: necesitamos un programa para xestionar as tendas dunha franquicia de venta de equipos informáticos.
Necesitamos gardar a seguinte información:

Clases : 
Provincia {idPro,NomPro} ==> provincias.json
Tenda {nomTen, provTen, cidTen}
Productos {nomPro, descPro, prezPro}
Franquicia {listProdFran, listCliFran, listEmplFran, listTendaFran}
Empleado { listHorTrabSemEmp, nomEmp, apelEmp }
Cliente {nomCli, apelCli, mailCli}
DB{}
Driver {connDB}

Operaciones :

Cargar datos
----------------------------------
Driver.pullProvincias("provincias.json").
Driver.pullFranquicias(connDB).
==================================

guardarDatos
----------------------------------
Driver.update(connDB);
==================================

Engadir unha nova tenda. 
----------------------------------
Franquicia.addTenda(nomTen, provTen, cidTen);
LenguajeException{ solo letras a-Z}
ProvinciaException{ solo provincias de la lista}
Driver.update(connDB);
 =================================

Mostrar as tendas.
----------------------------------
Franquicia.showTenda();
TendaException{ solo tendas de la lista}
Driver.update(connDB);
==================================

Eliminar unha tenda.
----------------------------------
Franquicia.deleteTenda(nomTen);
TendaException{ solo tendas de la lista}
Driver.update(connDB);
==================================

Engadir un producto.
----------------------------------
Franquicia.addProd(nomPro, descPro, prezPro);
Driver.update(connDB);
==================================

Mostrar os productos da franquicia.
----------------------------------
Franquicia.showProd();
==================================

Mostrar os productos dunha tenda.
----------------------------------
Franquicia.tenda.showProd(nomPro);
TendaException{ solo tendas de la lista}
==================================

Engadir un producto a unha tenda.
----------------------------------
Franquicia.tenda.addProd(nomPro);
lenguajeTendaException{ a-Z, a-Z , 0-9}
TendaProductoException{ solo productos de la tienda}
==================================

Actualizar o stock dun producto nunha determinada tenda.
----------------------------------
Driver.update(connDB);
Franquicia.tenda.updateProduct(nomPro);
TendaProductoException{ solo productos de la tienda}
==================================

Mostrar o stock dun producto dunha tenda.
----------------------------------
Franquicia.tenda.showProduct(nomPro);
TendaProductoException{ solo productos de la tienda}
==================================

Eliminar un producto dunha determinada tenda.
----------------------------------
Franquicia.tenda.deleteProduct(nomPro);
TendaProductoException{ solo productos de la tienda}
==================================

Eliminar un producto.
----------------------------------
Franquicia.deleteProd();
Driver.update(connDB);
==================================

Engadir un cliente.
----------------------------------
Franquicia.addCli(nomCli, apelCli, mailCli);
ClienteLenguajeException{a-Z,a-Z,(a-Z)@(a-Z).(a-Z) }
Driver.update(connDB);
==================================

Mostrar os clientes
----------------------------------
Franquicia.showCli();
==================================

Eliminar un cliente.
----------------------------------
Franquicia.deleteCli(nomCli);
ClienteException{ solo clientes de la franquicia}
Driver.update(connDB);
==================================


Ler os titulares do periódico El Pais. 
----------------------------------
RSS.read();
==================================

Sair da aplicación.









Temos que gardar :
AS provincias de España. Esta telas no arquivo provincias.json. Deberase gardar o id e o nome da provincia.
As tendas co seu nome, a provincia e a súa cidade.
Os productos co seu nome, descripción e prezo.
Cada tenda terá unha selección de productos. Para cada tenda temos que gardar o stock que ten dese productos.
Cada tenda terá unha serie de empragados. Cada empregado pode traballar en unha ou varias tendas. Necesitamos gardar o número de horas semanais que traballa en cada tenda. Ademais necesitamos gardar o se nome e apelidos.
Os clientes da franquicia. Estes non son clientes de cada tenda, senón da franquicia en xeral. De cada cliente debemos gardar o seu nome, apelidos e email.
A aplicación deberá poder facer as seguintes accións aos seus usuarios:

Engadir unha nova tenda.
Mostrar as tendas.
Eliminar unha tenda.
Engadir un producto.
Mostrar os productos da franquicia.
Mostrar os productos dunha tenda.
Engadir un producto a unha tenda.
Actualizar o stock dun producto nunha determinada tenda.
Mostrar o stock dun producto dunha tenda.
Eliminar un producto dunha determinada tenda.
Eliminar un producto.
Engadir un cliente.
Mostrar os clientes
Eliminar un cliente.
Ler os titulares do periódico El Pais. (Explícase máis abaixo)
Sair da aplicación.
Non é necesario realizar unha interface gráfica. Pódese facer un menú que pida os datos pola consola.
A persistencia debe de facerse do seguinte xeito:

Deberás realizar un diagrama entidade-relación do problema a resolver. (Isto non é necesario entregalo)
A continuación deberás transformalo ao modelo relacional para obter as táboas necesarias. (Isto non é necesario entregalo)
Débese gardar os datos da aplicación utilizando a base de datos SQLLite.
Cando se inicie o programa comprobará se existe a base de datos. Se non existe creará a nova base de datos así como todas as súas táboas. tamén se deberán de cargar tódalas provincias de España.
Cada vez que se produza un cambio nos datos teremos que actualizar a base de datos.
Para realizar a carga de datos das provincias débese de utilizar a liberías GSON.

En canto a lectura dos titulares de “El País” a aplicación tera que ler un RSS. Estes están en formato XML. Tan só se deberán mostrar por pantalla os titulares. Para iso utiliza SAX para parsear o documento XML. O arquivo XML telo debaixo da tarefa. Como ampliación e optativo sería interesante en lugar de ler o arquivo descargado, poder ler o arquivo online. Así sempre teriamos os titulares actualizados. O enlace dese XML é o seguinte: RSS de El País.

Observacións
Aqueles que coñezan Maven ou Gradle e conveniente que o usedes. Para os que non, tedes dúas opcións: engadir as liberías en formato json ou coñecer un pouco Maven e utilizalo (os que usedes NetBeans é moi faciliño de usar). Deixovos un par de enlaces:
http://www.decodigo.com/java-crear-proyectos-maven-en-netbeans-y-eclipse
https://soajp.blogspot.com/2017/02/anadir-librerias-manualmente-usando.html (Deste enlace só necesitades o último punto engadindo a libería que corresponda, NetBeans xa a descarga dos repositorios de Maven).

Avaliación Tarefa
Para que a tarefa se considere APTA terá que funcionar aplicación na súa totalidade.

Como entregar a Tarefa
Unha vez rematada sigue as seguintes indicacións para entregar a tarefa:

Sube a tarefa a un repositorio en GitHub.
Crea un arquivo zip co seguinte contido e enviao nesta tarefa:
– Un arquivo txt de nome repositorio.txt co enlace ao repositorio git da aplicación.
– Unha carpeta co código da aplicación creada.
O nome do arquivo zip será o seguinte: Apelido1_apelido2_nome_AD03.zip