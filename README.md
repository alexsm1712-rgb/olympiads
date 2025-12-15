# Olympiads

Система управления олимпиадами с поддержкой избранного, ролей пользователей и статистики.

## Требования

- Java 17+
- Maven 3.8+
- PostgreSQL (локально или облачная БД)
- Git (для клонирования репозитория)

---

## Запуск локально

1. Клонируем репозиторий:

```bash
git clone https://github.com/alexsm1712-rgb/olympiads.git
cd olympiads
Настроить базу данных PostgreSQL:

Создать базу olympiadsdb

Настроить пользователя и пароль

В src/main/resources/application.properties указать:
spring.datasource.url=jdbc:postgresql://localhost:5432/olympiadsdb
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
Собрать и запустить проект:
mvn clean install
mvn spring-boot:run
Перейти в браузере по адресу:
http://localhost:8081/home
Роли пользователей
ADMIN – полный доступ: добавление, редактирование, удаление, просмотр статистики.

ORGANIZER – может создавать олимпиады, редактировать/удалять свои, смотреть статистику только по своим олимпиадам.

USER – может просматривать олимпиады и добавлять их в избранное.

GitHub и деплой
Для деплоя на облако (Heroku, Railway, Render) подключите этот репозиторий и настройте:

Java 17

Maven build

PostgreSQL (облачная)

Пример для Heroku:

heroku login
heroku create olympiads-app
git push heroku main
Контакты
Автор: Алексей Мушенков
GitHub: alexsm1712-rgb
