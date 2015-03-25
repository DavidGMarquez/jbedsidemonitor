# jbedsidemonitor
JBedsideMonitor pretende ser un simulador de un monitor de cabecera

El propósito de JBedsideMonitor es crear un simulador de pacientes extensible que pueda ser empleado por cualquier ingeniero biomédico tanto para validar sus algoritmos funcionando en tiempo real como para probar nuevas metáforas visuales para transmitir la información generada por los algoritmos al personal asistencial. Todo esto limitando, en la medida de lo posible, el trabajo del ingeniero biomédico a la implementación del propio algoritmo, abstrayéndole de problemas como la lectura de los datos desde registros almacenados en formatos heterogéneos, simulación de la llegada de los datos en tiempo real, problemas de concurrencia derivados de la ejecución de múltiples algoritmos en paralelo, comunicación entre distintos algoritmos, etc.


# Explicación Básica de la estructura

-Introduction 

Vamos a explicar un poco la forma de la aplicación.

-Details 

Por un lado tenemos un paquete que es signals, donde reside el núcleo de nuestro sistema.
El paquete algorithm también es importante ya que es donde esta la lógica relacionada con los algoritmos y su implementación
El paquete datasource contiene las implementaciones de las fuentes de datos.
Por otra parte tenemos el paquete threads donde tenemos los hilos necesarios para la ejecución
Y finalmente tenemos un paquete vehicleclass que es donde residen las clases que se usan básicamente para pasarlas de un sitio a otro las cosas y gestionar


El ciclo de la información es el siguiente

Tenemos un datasource que genera los datos.
Estos datos son almacenados en WriteOperation
Estas WriteOperation hay un thread encargado de recibirlas y guardar esos datos en el signalManager con la debida sincronización.

Luego tenemos unos ciertos algoritmos que quieren leer estas señales. Estos algoritmos tienen una suscripción que vigila un thread (Scheduler). Cuando este thread considera que pueden pasar a la acción crea un ReadOperation que contiene las indicaciones de que datos necesita el algoritmo.
Estas ReadOperation son ejecutadas por un thread con la debida sincronización. Este hilo copia la información necesaria para el algoritmo y crea TaskForAlgorithmToExecute

Estas clases son recibidas por otro thread Execution Thread( o conjunto de threads podrían ser)
Estos thread acceden a los algoritmos y ejecutan con un AlgorithmExecutionContext sacado de la TaskForAlgorithmToExecute.


