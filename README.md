# LMS (Spring Boot + JPA + PostgreSQL)

Учебная платформа с курсами, модулями, уроками, заданиями и тестами.

## Требования
- JDK 17+
- Maven 3.9+
- PostgreSQL (локально) или Docker (для тестов Testcontainers)

## Быстрый старт
1. Настройте переменные окружения или редактируйте `src/main/resources/application.yml`:
   - `SPRING_DATASOURCE_URL` (по умолчанию `jdbc:postgresql://localhost:5432/lms`)
   - `SPRING_DATASOURCE_USERNAME` (по умолчанию `postgres`)
   - `SPRING_DATASOURCE_PASSWORD` (по умолчанию `postgres`)
2. Запуск:
```bash
mvn spring-boot:run
```

После запуска Swagger UI доступен по адресу:
`http://localhost:8080/swagger-ui/index.html`

## REST
- `GET /api/users`, `POST /api/users`, `GET /api/users/{id}`, `PUT /api/users/{id}`, `DELETE /api/users/{id}`
- `GET /api/courses`, `POST /api/courses`, `GET /api/courses/{id}`, `PUT /api/courses/{id}`, `DELETE /api/courses/{id}`

## Тесты
- Используется Testcontainers (PostgreSQL). Запуск:
```bash
mvn -Dtest=*Test test
```

## Стек
- Spring Boot 3, Spring Data JPA, Hibernate
- PostgreSQL, Testcontainers

## Аутентификация и роли
- Включён Spring Security (HTTP Basic) с тестовыми пользователями (in-memory):
  - admin/admin (ROLE_ADMIN)
  - teacher/teacher (ROLE_TEACHER)
  - student/student (ROLE_STUDENT)
- Доступ:
  - `/api/users/**` — только ADMIN
  - `/api/submissions/**`, `/api/quiz-submissions/**` — TEACHER или ADMIN
  - остальное — доступно без аутентификации по умолчанию (можно усилить по необходимости)


