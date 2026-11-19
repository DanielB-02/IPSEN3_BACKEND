# Spine Platform Risk API

REST backend (Spring Boot + PostgreSQL) for assessing and ranking online platforms on risk indicators for online exploitation of young people.

[![CI](https://github.com/DanielB-02/IPSEN3_BACKEND/actions/workflows/ci.yml/badge.svg)](https://github.com/DanielB-02/IPSEN3_BACKEND/actions/workflows/ci.yml)

**Live demo:** [TODO: live demo link, or remove this line]
**Frontend:** [IPSEN3_frontend_angular](https://github.com/DanielB-02/IPSEN3_frontend_angular) [TODO: check link]

![Screenshot](docs/screenshot.png)
[TODO: screenshot — suggestion: the frontend's ranked platform overview, or a Postman call to `GET /api/platform/sorted` next to its JSON response]

## What it does and why

Built as a team project at Hogeschool Leiden (IPSEN3) for Spine, a client that researches how online platforms (social media, messaging apps) are used for exploitation of young people, for example sexual exploitation and recruitment. Researchers fill in a fixed questionnaire per platform: case law, reports, scientific and news articles, reachability, ownership and data hosting. Each answer gets a score, and the API ranks platforms by total risk score so the team can see where to focus.

My role: [TODO: describe your own contributions, e.g. which features or parts you built]

## Key features

- **Platform risk ranking:** total score per platform, sorted by risk; archived platforms can be filtered out
- **Questionnaire per platform:** CRUD for platforms, questions and scored answers
- **JWT authentication:** stateless sign-in with BCrypt-hashed passwords
- **Permission-based authorization:** roles (`ADMIN`, `FICTER`, `READONLY`) map to fine-grained permissions, enforced per service method with custom `@PreAuthorize` annotations
- **Versioned API media type:** `application/vnd.spine.api.v1+json`
- **Demo seed data:** 10 platforms × 18 questions, loaded with the `testdata` profile

## Tech stack

Java 17 · Spring Boot 3.1 (Web, Data JPA, Security, Validation) · PostgreSQL · jjwt · Lombok · JUnit 5, Mockito, H2 (tests) · Maven · GitHub Actions

## How it works

```mermaid
flowchart LR
    FE[Angular frontend] -->|JSON + Bearer JWT| F[JwtAuthenticationFilter]
    F --> C[Controllers<br/>/platform /question /answer /user /role]
    C --> S[Services<br/>@PreAuthorize permission checks]
    S --> R[Spring Data JPA repositories]
    R --> DB[(PostgreSQL)]
    A[POST /auth/signin] -->|username + password| AS[AuthenticationService] -->|signed JWT| FE
```

- **Layers:** `controller` (REST plus request/response objects in `controller/vo`) → `service` (business logic and authorization) → `repository` (Spring Data JPA) → `model` (JPA entities).
- **Scoring:** `PlatformService` sums the answer scores of all questions for a platform and returns the platforms sorted by that total.
- **Security:** `/auth/signin` returns a JWT (HS256) that includes the user's role. Each service method carries a permission annotation (for example `@PermissionLezen` or `@PermissionBeheerGebruikers`), checked by `PermissionChecks` against the role's permissions.

### Main endpoints (base path `/api`)

| Method | Path | Description |
|---|---|---|
| POST | `/auth/signin` | Sign in, returns a JWT |
| POST | `/signup` | Create a user (requires user-management permission) |
| GET | `/platform/sorted` | All platforms, highest risk score first |
| GET | `/platform/sortedDesc` | Active (non-archived) platforms, lowest score first |
| GET/POST/PUT/DELETE | `/platform`, `/question`, `/answer` | CRUD |
| GET/POST/PUT | `/role`, GET `/permission` | Role and permission management |

## Getting started

**Prerequisites:** JDK 17+ and PostgreSQL. Maven is included through the wrapper.

```bash
git clone https://github.com/DanielB-02/IPSEN3_BACKEND.git
cd IPSEN3_BACKEND
cp .env.example .env          # then edit .env
```

| Variable | Required | Description |
|---|---|---|
| `DB_URL` | no | JDBC URL, default `jdbc:postgresql://localhost:5432/postgres` |
| `DB_USERNAME` | no | default `postgres` |
| `DB_PASSWORD` | yes | database password |
| `JWT_SIGNING_KEY` | yes | base64 key of at least 256 bits: `openssl rand -base64 32` |
| `SERVER_PORT` / `DDL_AUTO` / `DEBUG` | no | defaults `8081` / `create-drop` / `false` |

Values can also be set as regular environment variables, which take precedence over `.env`.

Run with demo data. The seed loader lives in the test sources, so use `test-run`:

```bash
./mvnw spring-boot:test-run -Dspring-boot.run.profiles=testdata
```

Without PostgreSQL, you can try it on in-memory H2 instead:

```bash
./mvnw spring-boot:test-run -Dspring-boot.run.profiles=testdata \
  "-Dspring-boot.run.arguments=--spring.datasource.url=jdbc:h2:mem:demo;MODE=PostgreSQL --spring.datasource.username=sa --spring.datasource.password= --spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
```

The API is then available at `http://localhost:8081/api`. Demo accounts (local seed data only): `spineadmin2024`, `spineficter2024` and `spinereadonly2024`, all with the password defined in `TestDataLoader`.

> The schema is recreated on every start (`ddl-auto=create-drop`). Don't point this at a database you want to keep.

## Tests

```bash
./mvnw test
```

Tests run against an in-memory H2 database (profile `test`), so no PostgreSQL is needed. They cover platform score sorting, repository queries, question-service error handling and JWT generation and validation. CI runs the same command on every push and pull request.

## Known limitations

- URL-level security is open (`permitAll` on `/**` in `SecurityConfiguration`). Authorization relies on the method-level permission checks, and an unauthenticated call to a protected method returns 500 instead of 401/403.
- The JWT lifetime is `1000 * 60 * 24` ms, which is 24 minutes. [TODO: confirm whether 24 hours was intended]
- No database migrations (Flyway/Liquibase); the schema is generated by Hibernate.
- Spring Boot 3.1 is end-of-life; an upgrade to a supported 3.x line is the next maintenance step.

## What I learned / next steps

[TODO: rewrite these in your own words]
- Implementing stateless JWT authentication and permission-based authorization in Spring Security
- Working in a team of [TODO: n] students with feature branches and pull requests for a real client
- Next: re-enable URL-level authentication, add Flyway migrations, and upgrade to a supported Spring Boot version
