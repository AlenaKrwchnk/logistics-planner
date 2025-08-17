# Система расчета логистических маршрутов (мультимодальные перевозки)  — CRUD-приложение

Система предназначена для расчета оптимальных логистических маршрутов с использованием различных видов транспорта и учетом множества факторов, таких как таможенные пошлины, экологические сборы и время доставки.

---



## Основные возможности

- **Управление сущностями (CRUD):**
  - Товары
  - Сегменты пути(дороги)
  - Таможенные пункты
  - Экологические сборы
  - Локации
  - Категории Товаров
  - Таможенные пошлины
- **Расчет маршрутов с помощью алгоритма Дейкстры**:
  - Выбор кратчайшего пути с учетом стоимости, времени и экологических сборов
  - Оптимизация с учетом фуры, ж/д и морского транспорта
  - Таможенные пошлины по категориям товаров
  - Экологические сборы (CO₂)
  - 
- **Визуализация маршрутов** на карте с помощью [Leaflet](https://leafletjs.com/)

### Связи


<img width="1240" height="730" alt="image" src="https://github.com/user-attachments/assets/8992b988-6326-458c-9528-82f1a462a1f5" />

- **Товары ↔ Транспортные средства**: товар может перевозиться несколькими видами транспорта.
- **Локация ↔ Таможенные пункты**: Локации может соотв тамож пункт .

### Функционал
В проекте уже есть файл data.sql с набором данных для визуализации работы рассчетов.

Пример данных :<img width="668" height="587" alt="image" src="https://github.com/user-attachments/assets/76d15465-19ae-421a-8b8a-91e3cbd74030" />
 
Ниже есть карта с нанесенными на нее локациями и сегментами связи(дорогами) между ними:



<img width="1140" height="889" alt="Group 1" src="https://github.com/user-attachments/assets/e4497615-8b3a-4ba9-86fe-287105e9890c" />

- Расчет **стоимости и времени маршрута** с учетом всех переменных.

<img width="1709" height="540" alt="image" src="https://github.com/user-attachments/assets/8618cbe4-6a49-4bca-b339-ba79e847b420" />


- **Визуализация маршрута на карте** с использованием [Leaflet](https://leafletjs.com/).


<img width="1913" height="778" alt="image" src="https://github.com/user-attachments/assets/9c322f0d-cc9d-4a77-8e57-4b27aff99f12" />


---

## Технологический стек

- **Backend**: Spring, Hibernate, Maven
- **Database**: MySQL (с возможностью запуска через Docker)
- **Testing**: JUnit, Mockito
- **Frontend**: Thymeleaf, JavaScript
- **Логирование**: Logback
- **Прочее**: Docker для контейнеризации базы данных

---

## Установка и запуск

### 1. Клонируем репозиторий
```bash
git clone https://github.com/ваш-пользователь/ваш-проект.git
cd ваш-проект

2. Запуск MySQL через Docker
docker run --name logistics-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=logistics -p 3306:3306 -d mysql:8
В application.yml указать параметры подключения:
  datasource:
    url: jdbc:mysql://localhost:3306/logistics
    username: root
    password: RootRoot123!

4. Сборка и запуск
mvn clean install
mvn spring-boot:run


Приложение доступно по адресу: http://localhost:8080

Тестирование
mvn test

