# Allegro engineering of distributed systems - Project

[![Build](https://github.com/allegro-agh-2023/gr2_hotel_room_reservations/actions/workflows/master-build.yml/badge.svg)](https://github.com/allegro-agh-2023/gr2_hotel_room_reservations/actions/workflows/master-build.yml)
[![Tests](https://github.com/allegro-agh-2023/gr2_hotel_room_reservations/actions/workflows/master-test.yml/badge.svg)](https://github.com/allegro-agh-2023/gr2_hotel_room_reservations/actions/workflows/master-test.yml)

Project written for Applied Computer Science **Allegro engineering of distributed systems** course at [AGH UST](https://www.agh.edu.pl/en) [WFiIS](https://www.fis.agh.edu.pl/en/).

Semester `9`, year `2023`.

## Launch

Use docker compose to launch all services:

```bash
cd ./docker
docker-compose up
```

## Tools

### pgAdmin4

PostgreSQL database can be managed from locally hosted [pgAdmin](https://www.pgadmin.org/) tool.
If the project has been launched with `docker-compose`, [pgAdmin](https://www.pgadmin.org/) provides convenient web interface at [localhost:5050](http://localhost:5050).

Provide the following credentials to access admin console and PostgreSQL database:

```
email:        admin@admin.com
password:     admin
db-password:  postgres
```

### RabbitMQ Management

[RabbitMQ](https://www.rabbitmq.com/) message broker has its official management tool integrated into docker image. The tool can be accessed at [localhost:5051](http://localhost:5051).

Credentials:

```
username: admin
password: admin
```

## Authors

- [Aleksander Kluczka](https://github.com/vis4rd)
- [Jakub Kraśniak](https://github.com/sakor88)
