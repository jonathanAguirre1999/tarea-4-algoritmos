# Tarea 4: Análisis de Complejidad Algorítmica

Este repositorio contiene la solución a la Tarea de la Unidad #4, enfocada en la medición de eficiencia, complejidad temporal y optimización de algoritmos utilizando Java y JavaFX.

## Descripción del Proyecto

El software está dividido en dos grandes módulos según lo solicitado en la guía de actividades:

### Actividad 1: Gestión de Matriz (1,000,000 de datos)
Generación de una matriz de 1000x1000 con números aleatorios (positivos y negativos) para realizar:
* **Búsqueda (x y -x):** Comparación de tiempos entre Búsqueda Secuencial, Binaria e Interpolación .
* **Ordenamiento:** Implementación y benchmarking de algoritmos cuadráticos (Bubble, Insertion) vs. logarítmicos/lineales (Merge, Shell, Counting, Radix) .
* *Nota:* Se implementaron límites de seguridad para los algoritmos lentos ($O(n^2)$) para evitar el congelamiento de la interfaz con grandes volúmenes de datos.

### Actividad 2: Problema de Optimización (Caso "Farmacia")
Solución a un problema de la vida real: *Encontrar dos productos cuyo precio sumado sea igual al valor exacto de una Gift Card.*
* **Solución Genérica:** Enfoque de Fuerza Bruta con complejidad $O(n^2)$.
* **Solución Optimizada:** Enfoque utilizando `HashSet` con complejidad lineal $O(n)$.
* **Resultados:** Se demuestra una reducción drástica del tiempo de ejecución (ej. de ~800ms a ~18ms en escenarios de estrés).

## Tecnologías Utilizadas
* **Lenguaje:** Java 21+
* **Interfaz Gráfica:** JavaFX (Modular)
* **Gestor de Dependencias:** Maven
* **IDE Recomendado:** IntelliJ IDEA

##  Instrucciones de Ejecución

1.  **Clonar el repositorio:**
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    ```
2.  **Compilar con Maven:**
    Asegúrese de recargar las dependencias de Maven para descargar las librerías de JavaFX.
3.  **Ejecutar:**
    * Ubique la clase `ups.algoritmos.tarea4.Launcher`.
    * Ejecute esta clase (Click derecho -> Run 'Launcher') para evitar conflictos de módulos de JavaFX.

### Opcional
Puede ejecutar el .jar compilado adjunto en la carpeta del proyecto directamente.


## Entorno de Pruebas
Las mediciones de tiempo presentadas en la interfaz fueron realizadas en el siguiente hardware :
* **Equipo:** Lenovo Legion 5 Pro
* **Sistema Operativo:** Windows 11
* **Procesador:** AMD Ryzen 7 6800H
* **RAM:** 16gb DDR5
* **Almacenamiento:** 512gb SSD NVME
* **GPU:** NVidia GeForce GTX 3070ti

## Licencia y Propósito
Este código fue desarrollado estrictamente con fines académicos para la asignatura de Algoritmos Y Estructura de Datos, carrera de Ing. Software de la Universidad Politécnica Salesiana.

* **Autor:** Jonathan Daniel Aguirre Coronado