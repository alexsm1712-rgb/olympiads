# Olympiads

Система управления олимпиадами с поддержкой избранного, ролей пользователей и статистики.

## Требования

- Java 17+
- Maven 3.8+
- PostgreSQL (локально или облачная БД)
- Git (для клонирования репозитория)

---

## Настройка базы данных

Создать базу данных PostgreSQL:

sql

CREATE DATABASE olympdb;

Создать пользователя (если ещё нет):

CREATE USER postgres WITH PASSWORD '1';

GRANT ALL PRIVILEGES ON DATABASE olympdb TO postgres;



В src/main/resources/application.properties проверить настройки подключения:

spring.datasource.url=jdbc:postgresql://localhost:5432/olympdb

spring.datasource.username=postgres

spring.datasource.password=1

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

server.port=8081

spring.thymeleaf.encoding=UTF-8

При необходимости замени username и password на свои."

## Запуск проекта через IntelliJ IDEA

Открой IntelliJ IDEA.

Проверить JDK проекта

В IntelliJ: File → Project Structure → Project

Project SDK: 17

Project language level: 17 – Sealed types, pattern matching, records

В Run/Debug Configurations тоже проверь, чтобы использовался JDK 17.

Выбери File → Open и открой корень проекта (где находится pom.xml).

Подождите, пока Maven загрузит все зависимости.

Найди класс с аннотацией @SpringBootApplication (обычно Main.java).

Кликни правой кнопкой на классе → Run 'Main'.

После запуска приложение будет доступно по адресу:

Главная страница: http://localhost:8081/home

Все олимпиады: http://localhost:8081/olympiads

Избранное: http://localhost:8081/olympiads/favorites

Но сначала надо пройти авторизацию:
РОЛИ ADMIN: логин: admin, пароль: 1

     ORGANIZER: логин: organizer, пароль: 1
     
     USER: логин user, пароль: 1

## Функционал

Администратор:

Полный доступ ко всем олимпиадам

Добавление, редактирование, удаление олимпиад

Просмотр статистики добавления олимпиад пользователями

Организатор:

Управление только своими олимпиадами

Просмотр статистики только по своим олимпиадам

Пользователь:

Просмотр списка олимпиад

Добавление и удаление олимпиад из избранного

## Автор: Алексей Мушенков
