# MCC Credit Disbursement Service

Microservicio de desembolso de crédito para una arquitectura de microservicios bancaria. Este servicio es responsable de efectuar depósitos y transferencias a las cuentas de los clientes, integrándose de manera síncrona y asíncrona con otros microservicios del ecosistema.

## 📋 Descripción General

El presente repositorio forma parte de un total de 6 repositorios que constituyen una **arquitectura de microservicios para un sistema bancario**. El microservicio `mcc-credit-disbursement-service` cumple funciones críticas de procesamiento de transacciones financieras (depósitos y transferencias) con comunicación tanto síncrona como asíncrona en el ecosistema de microservicios.

## 🏗️ Arquitectura de Microservicios

El microservicio se integra en la siguiente arquitectura:

![Arquitectura de Microservicios MCC](https://github.com/victordaniel123rt-lang/mcc-credit-disbursement-service/raw/master/docs/architecture.png)

### Microservicios Relacionados

- **mcc-gateway-service**: Punto de entrada único para todas las solicitudes
- **mcc-account-service**: Gestión de cuentas bancarias (integración síncrona)
- **mcc-customer-service**: Gestión de clientes
- **mcc-credit-disbursement-service**: Desembolso de crédito ⭐ (Este repositorio)
- **mcc-security-service**: Autenticación y autorización
- **mcc-notification-service**: Envío de notificaciones (integración asíncrona)

## 🛠️ Tecnologías

- **Java 17**: Lenguaje de programación principal
- **Spring Boot 4.0.7**: Framework web y base de datos
- **Spring Cloud 2025.1.2**: Componentes para arquitectura de microservicios
- **Spring Cloud Azure 7.3.0**: Integración con servicios de Azure
- **Azure Service Bus**: Mensajería asíncrona y gestión de colas
- **Spring Data JPA**: ORM con Hibernate para persistencia
- **Spring Cloud OpenFeign**: Cliente HTTP declarativo para comunicación síncrona
- **Resilience4j**: Circuit breaker para resiliencia
- **Spring Boot Actuator**: Monitoreo y métricas
- **Azure Spring Cloud Actuator**: Monitoreo integrado con Azure
- **MariaDB**: Base de datos relacional
- **Lombok**: Generación automática de getters, setters y constructores
- **SpringDoc OpenAPI**: Documentación automática con Swagger/OpenAPI
- **Maven**: Gestor de dependencias y construcción

## 📦 Dependencias Principales

```xml
<!-- Spring Boot Starters -->
- spring-boot-starter-actuator
- spring-boot-starter-data-jpa
- spring-boot-starter-webmvc

<!-- Azure Spring Cloud -->
- spring-cloud-azure-starter-servicebus       (Mensajería asíncrona)
- spring-cloud-azure-starter-actuator         (Monitoreo Azure)

<!-- Spring Cloud -->
- spring-cloud-starter-openfeign              (Comunicación síncrona)
- spring-cloud-starter-circuitbreaker-resilience4j  (Resiliencia)

<!-- Base de Datos -->
- mariadb-java-client

<!-- Utilidades -->
- lombok

<!-- API Documentation -->
- springdoc-openapi-starter-webmvc-ui (v3.0.3)

<!-- Testing -->
- spring-boot-starter-actuator-test
- spring-boot-starter-data-jpa-test
- spring-boot-starter-webmvc-test
```

## 🌟 Características Principales

### Comunicación Asíncrona con Azure Service Bus
Utiliza **Azure Service Bus** para:
- Envío de mensajes de notificación al `mcc-notification-service`
- Procesamiento desacoplado de eventos
- Garantía de entrega de mensajes
- Escalabilidad horizontal

### Comunicación Síncrona Inter-Microservicios
Implementa **Spring Cloud OpenFeign** para:
- Llamadas síncronas al `mcc-account-service`
- Validación y actualización de cuentas en tiempo real
- Integración transparente

### Resiliencia y Tolerancia a Fallos
**Resilience4j** proporciona:
- Circuit breaker para servicios dependientes
- Manejo automático de reintentos
- Fallback automático
- Tolerancia a fallos temporales

### Monitoreo Integrado
**Spring Boot Actuator + Azure Spring Cloud Actuator**:
- Endpoints de salud (`/actuator/health`)
- Métricas de rendimiento
- Información del estado de transacciones
- Integración con Azure Monitor

## 🚀 Instalación y Configuración

### Requisitos Previos

- JDK 17 o superior
- Maven 3.8+
- MariaDB 10.4+
- Cuenta de Azure con Service Bus configurado
- Docker (opcional, para containerización)

### Clonar el Repositorio

```bash
git clone https://github.com/victordaniel123rt-lang/mcc-credit-disbursement-service.git
cd mcc-credit-disbursement-service
```

### Compilar el Proyecto

```bash
mvn clean install
```

### Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8082` (puerto configurable).

## 📖 Documentación de API

Una vez la aplicación esté en ejecución, puedes acceder a la documentación interactiva de la API:

- **Swagger UI**: `http://localhost:8082/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8082/v3/api-docs`

## 🐳 Docker

### Construir la Imagen

```bash
docker build -t mcc-credit-disbursement-service:latest .
```

### Ejecutar en Contenedor

```bash
docker run -d \
  --name mcc-credit-disbursement \
  -p 8082:8082 \
  -e SPRING_DATASOURCE_URL=jdbc:mariadb://mariadb:3306/disbursements \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=password \
  -e AZURE_SERVICE_BUS_CONNECTION_STRING=Endpoint=sb://your-namespace.servicebus.windows.net/;... \
  mcc-credit-disbursement-service:latest
```

## 🔧 Configuración

Las propiedades de configuración se encuentran en `application.properties` o `application.yml`:

```yaml
spring:
  application:
    name: mcc-credit-disbursement-service
  datasource:
    url: jdbc:mariadb://localhost:3306/disbursements
    username: root
    password: password
    driver-class-name: org.mariadb.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MariaDBDialect
  cloud:
    azure:
      servicebus:
        connection-string: Endpoint=sb://your-namespace.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=your-key
        processor:
          prefetch-count: 10

server:
  port: 8082

# Spring Cloud OpenFeign
feign:
  client:
    config:
      mcc-account-service:
        connectTimeout: 5000
        readTimeout: 5000

# Resilience4j Circuit Breaker
resilience4j:
  circuitbreaker:
    instances:
      mcc-account-service:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5000
        recordExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.io.IOException
  retry:
    instances:
      mcc-account-service:
        maxAttempts: 3
        waitDuration: 1000

# Actuator
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: always
    
# Azure Monitor Integration
azure:
  application-insights:
    instrumentation-key: your-instrumentation-key
```

## 📁 Estructura del Proyecto

```
mcc-credit-disbursement-service/
├── src/
│   ├── main/
│   │   ├── java/com/vdgarcia/
│   │   │   ├── controller/      # Controladores REST
│   │   │   ├── service/         # Lógica de negocio
│   │   │   ├── repository/      # Acceso a datos (JPA)
│   │   │   ├── entity/          # Entidades JPA
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── client/          # Clientes Feign para otros microservicios
│   │   │   ├── messaging/       # Productores/consumidores de mensajes
│   │   │   ├── config/          # Configuración
│   │   │   └── exception/       # Excepciones personalizadas
│   │   └── resources/
│   │       └── application.yml   # Configuración
│   └── test/
│       └── java/                # Tests unitarios e integración
├── pom.xml                       # Configuración Maven
├── Dockerfile                    # Configuración Docker
└── README.md                     # Este archivo
```

## 💬 Flujos de Comunicación

### Flujo de Depósito (Síncrono)

```
Cliente → API REST (mcc-credit-disbursement-service)
         ↓
      Validación de datos
         ↓
      Llamada Feign → mcc-account-service (Actualizar cuenta)
         ↓
      Almacenar transacción en BD
         ↓
      Publicar evento en Service Bus → mcc-notification-service
         ↓
      Respuesta al cliente
```

### Flujo de Transferencia (Síncrono + Asíncrono)

```
Cliente → API REST (mcc-credit-disbursement-service)
         ↓
      Validación de cuentas origen y destino
         ↓
      Llamadas Feign → mcc-account-service (Validar y actualizar)
         ↓
      Almacenar transacción
         ↓
      Publicar eventos en Service Bus → mcc-notification-service
         ↓
      Respuesta al cliente
```

## 🔐 Transaccionalidad

El servicio garantiza:
- **ACID**: Transacciones ACID en base de datos
- **Compensación**: Rollback automático en caso de fallos
- **Idempotencia**: Prevención de duplicados en transacciones

## 🧪 Testing

Ejecutar las pruebas unitarias:

```bash
mvn test
```

Ejecutar las pruebas de integración:

```bash
mvn verify
```

Ejecutar las pruebas con cobertura:

```bash
mvn test jacoco:report
```

## 📊 Monitoreo y Métricas

Ver el estado de la aplicación:

```bash
curl http://localhost:8082/actuator/health
```

Ver métricas:

```bash
curl http://localhost:8082/actuator/metrics
```

Ver métricas de Service Bus:

```bash
curl http://localhost:8082/actuator/metrics/azure.servicebus.messages
```

## 🤝 Contribución

Las contribuciones son bienvenidas. Para reportar problemas o proponer mejoras:

1. Abre un **Issue** describiendo el problema o mejora
2. Crea una rama con tu característica (`git checkout -b feature/AmazingFeature`)
3. Realiza commit de tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un **Pull Request**

## 📝 Licencia

Este proyecto está bajo licencia por definir. Ver el archivo LICENSE para más detalles.

## 👨‍💻 Autor

- **victordaniel123rt-lang** - Desarrollador principal

## 📞 Soporte

Para reportar problemas o solicitar ayuda, abre un Issue en este repositorio.

---

**Nota**: Este microservicio forma parte de un ecosistema completo de microservicios. Asegúrate de que:
- El `mcc-account-service` esté disponible y configurado correctamente
- Azure Service Bus esté configurado con la conexión correcta
- La base de datos MariaDB esté disponible
- Los temas/colas de Service Bus estén creados (`notifications-queue`, etc.)
- El `mcc-notification-service` esté suscrito a los eventos publicados
