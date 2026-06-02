## Как установить Allure на Linux

### Вариант 1. Через официальный скрипт установки (рекомендуемый)

```bash
# Скачиваем и запускаем установочный скрипт
wget https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.25.0/allure-commandline-2.25.0.zip
unzip allure-commandline-*.zip
```

Затем перемещаем в системную папку и добавляем в PATH:

```bash
sudo mv allure-2.25.0 /opt/allure
sudo ln -s /opt/allure/bin/allure /usr/local/bin/allure
```

### Вариант 2. Ручная настройка PATH

Если не хотите создавать симлинк, добавьте путь в переменную окружения:

```bash
echo 'export PATH=$PATH:/opt/allure/bin' >> ~/.bashrc
source ~/.bashrc
```

Или для zsh:

```bash
echo 'export PATH=$PATH:/opt/allure/bin' >> ~/.zshrc
source ~/.zshrc
```

### Вариант 3. Через пакетный менеджер (если доступно)

Некоторые дистрибутивы поддерживают установку через пакетные менеджеры. Например, для Arch Linux:

```bash
yay -S allure
```

---

## Проверка установки

После установки проверьте, что команда работает:

```bash
allure --version
```

Если видите номер версии — всё настроено правильно.

---

## Детально о команде `allure generate target/allure-results --clean`

### Что делает команда

- `allure generate` — запускает генерацию HTML-отчёта из сырых данных.
- `target/allure-results` — путь к папке с результатами тестов (именно туда Selenide+TestNG пишут JSON-файлы, скриншоты и вложения).
- `--clean` — перед генерацией удаляет предыдунюю версию отчёта (папку `allure-report`), чтобы не было «мусора» от старых прогонов.

### Дополнительные опции, которые могут пригодиться

- `--output` или `-o` — задать кастомную папку для отчёта (по умолчанию `allure-report`):
  ```bash
  allure generate target/allure-results -o my-custom-report --clean
  ```
- `--host` и `--port` — если собираетесь сразу открывать отчёт через `allure open`, можно заранее указать настройки сервера:
  ```bash
  allure generate target/allure-results --clean --host 0.0.0.0 --port 8085
  ```

### Пример полного цикла работы

1. Запускаем тесты:
   ```bash
   mvn clean test
   ```
2. Генерируем отчёт:
   ```bash
   allure generate target/allure-results --clean
   ```
3. Открываем в браузере:
   ```bash
   allure open
   ```

По умолчанию `allure open` запустит локальный веб-сервер на порту 5000 и откроет отчёт в браузере. Если нужно указать другой порт:
```bash
allure open --port 8080
```

---

## Если что-то пошло не так

- **Ошибка «command not found»**: значит, Allure не добавлен в PATH. Перепроверьте шаги по добавлению в `.bashrc` или созданию симлинка.
- **Пустой отчёт**: убедитесь, что тесты действительно выполнялись и в `target/allure-results` появились файлы (там должно быть много JSON-ов и папок).
- **Отчёт не обновляется**: попробуйте явно указать `--clean`, чтобы гарантированно удалить старый отчёт перед генерацией.
### dop

Ошибка говорит о том, что вы запустили `mvn clean test` в папке, где нет файла `pom.xml` — а он обязателен для работы Maven. Вы находитесь в `~/.cache/selenium`, это служебная директория, там проекта нет.

## Что делать

1. **Найдите корневую папку вашего Maven-проекта** — там, где лежит `pom.xml`. Обычно это папка с именем вашего проекта, например `my-test-project/`.
2. **Перейдите в неё в терминале**:
   ```bash
   cd /путь/к/вашему/проекту
   ```
   Например:
   ```bash
   cd ~/projects/my-selenide-tests
   ```
3. **Убедитесь, что `pom.xml` на месте**:
   ```bash
   ls -la pom.xml
   ```
   Если файл виден в выводе — всё хорошо, можно запускать тесты.
4. **Запустите тесты**:
   ```bash
   mvn clean test
   ```

---

## Как понять, где ваш проект

Если вы не помните, куда сохранили проект, попробуйте поискать его так:

- **Поиск по домашнему каталогу** (самый простой способ, если проект где‑то рядом):
  ```bash
  find ~/ -name "pom.xml" -type f 2>/dev/null
  ```
- **Если используете IDE** (IntelliJ IDEA, Eclipse): посмотрите, какой путь указан в окне проекта — это и будет корневая папка.
- **Если скачивали архив с проектом** — распакуйте его в удобное место (например, в `~/projects/`) и перейдите туда.

---

## Пример полного рабочего сценария

Допустим, вы скачали проект в папку `~/projects/awesome-tests/` и хотите его запустить:
```bash
cd ~/projects/awesome-tests  # переходим в папку проекта
ls -la                      # проверяем, что видим pom.xml и src/
mvn clean test              # запускаем тесты
allure generate target/allure-results --clean  # генерируем отчёт
allure open                 # открываем отчёт в браузере
```

---

## Если проекта пока нет

Если вы только начинаете и у вас ещё нет структуры проекта, создайте её вручную:

1. **Создайте папку и перейдите в неё**:
   ```bash
   mkdir ~/projects/my-first-selenide
   cd ~/projects/my-first-selenide
   ```
2. **Создайте `pom.xml`** (скопируйте туда пример из предыдущего ответа или минимальный шаблон ниже).
3. **Создайте структуру папок для тестов**:
   ```bash
   mkdir -p src/test/java/com/example/tests
   ```
4. **Положите туда Java‑класс с тестом** (например, `LoginTest.java` из примера выше).
5. **Запускайте `mvn clean test`** — теперь всё должно работать.

Минимальный `pom.xml` для старта (если нужно):
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>selenide-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <allure.version>2.25.0</allure.version>
        <testng.version>7.8.0</testng.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.codeborne</groupId>
            <artifactId>selenide</artifactId>
            <version>6.14.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>${testng.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-testng</artifactId>
            <version>${allure.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.1.2</version>
                <configuration>
                    <systemProperties>
                        <allure.results.directory>${project.build.directory}/allure-results</allure.results.directory>
                    </systemProperties>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```