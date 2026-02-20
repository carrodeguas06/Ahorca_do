# Ahorca.do 🎮

Un juego interactivo implementado en Java donde los jugadores deben adivinar palabras mientras que el tiempo se agota. ¡Intenta no caer ahorcado!

## 📋 Descripción

**Ahorca.do** es una aplicación de escritorio del clásico juego del ahorcado (Hangman), desarrollada en Java con una interfaz gráfica moderna. El proyecto incluye lógica de juego, manejo de palabras aleatorias y un sistema de puntuación.

## 🛠️ Tecnologías

- **Lenguaje**: Java
- **Gestor de Dependencias**: Maven
- **Configuración**: pom.xml
- **IDE Compatible**: IntelliJ IDEA (archivos .idea incluidos)

## 📁 Estructura del Proyecto

```
Ahorca_do/
├── src/                    # Código fuente del proyecto
├── pom.xml                 # Configuración de Maven
├── mvnw                    # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                # Maven Wrapper (Windows)
├── Ahorca.do.pdf           # Documentación del proyecto
├── .gitignore              # Archivos ignorados por Git
└── .idea/                  # Configuración de IntelliJ IDEA
```

## 🚀 Inicio Rápido

### Requisitos
- Java 8 o superior
- Maven 3.6+ (opcional, puedes usar el Maven Wrapper incluido)

### Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/carrodeguas06/Ahorca_do.git
cd Ahorca_do
```

2. Compila el proyecto:

**En Linux/Mac:**
```bash
./mvnw clean compile
```

**En Windows:**
```bash
mvnw.cmd clean compile
```

O con Maven instalado:
```bash
mvn clean compile
```

### Ejecución

Para ejecutar la aplicación:

**En Linux/Mac:**
```bash
./mvnw exec:java -Dexec.mainClass="com.ahorca.Main"
```

**En Windows:**
```bash
mvnw.cmd exec:java -Dexec.mainClass="com.ahorca.Main"
```

O con Maven:
```bash
mvn exec:java -Dexec.mainClass="com.ahorca.Main"
```

## 🎯 Características

- 🎮 Interfaz gráfica intuitiva
- 📊 Sistema de puntuación
- 🔤 Base de palabras variadas
- ⏱️ Modo de juego desafiante
- 🎨 Diseño moderno y responsivo

## 📚 Documentación

Consulta el archivo `Ahorca.do.pdf` para obtener más información sobre el diseño y especificaciones del proyecto.

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Para cambios importantes:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está disponible bajo licencia abierta. Consulta el repositorio para más detalles.

## 👤 Autor

**carrodeguas06** - [GitHub Profile](https://github.com/carrodeguas06)

## 📞 Contacto

Si tienes preguntas o sugerencias, no dudes en abrir un issue en el repositorio.

---

**Última actualización**: Febrero 2026