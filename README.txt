GESTION TIENDA ONLINE - INTEGRADO

Base del proyecto:
- GestionTiendaOnline1

Integración:
- Se conserva la estructura de carpetas y los controladores/DAOs originales de GestionTiendaOnline1.
- Se han integrado los casos del Reto 0:
  1. Registrar cliente.
  2. Realizar el pedido de un producto.
  3. Consultar productos disponibles.
- El programa principal integrado es main.Main.
- La clase vista.GestionTiendaOnline1 original se mantiene en el proyecto.
- El pedido se sigue almacenando en pedido.dat con el formato de serialización usado por GestionTiendaOnline.

Casos que ya estaban en GestionTiendaOnline y se conservan:
- Consultar pedidos.
- Consultar productos disponibles.
- Editar stock de un producto.
- Consultar pedidos de un cliente.

Base de datos:
- Se utiliza GestionTiendaOnline.
- El script Reto_0.sql contiene el script proporcionado para PRODUCTO y CLIENTE.
- La conexión utiliza los valores del fichero src/configClase.properties.

IMPORTANTE:
El proyecto referencia mysql-connector-java-8.0.15.jar de forma relativa en src/lib/.
El archivo .jar no estaba incluido en los ZIP proporcionados, por lo que no se ha inventado ni añadido una copia.
