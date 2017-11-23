Инициализация проекта с нуля, мануал + команды

1) Сначала скачиваем gradle отсюда https://gradle.org/releases/ нажимаем там на кнопку "complete"
2) Прописываем путь до gradle-4.3.1\bin\ в переменные среды Paths (чтобы работало из командной строки), затем перезапускаем intellij idea, ибо консолька тупая, и поймёт прописанные пути после рестарта.
3) Некоторые команды. Если хотим очиститить проект от всякой хуеты и сделать по адекватному проделываем следующее
	• Очищаем проект до такой вот структуры примерно
	src
    -main
        -java
        -resources
    -test
        -java
        -resources
	
	• Выполняем команду "gradle init" генерятся файлы
	• Заходим в build.gradle и пишем туда вот такой вот примерно такой шаблон:
						buildscript {


							repositories {
								mavenCentral()
								jcenter()
							}
							dependencies {

							}
						}	

						allprojects {
							apply plugin: "java"
							apply plugin: "idea"


							ext {

							}

							repositories {
								mavenLocal()
								mavenCentral()
								maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
								maven { url "https://oss.sonatype.org/content/repositories/releases/" }
							}
						}

						project(":sampleModule") { 	//повторить этот блок на все модули


							dependencies {
								compile fileTree(dir: 'libs', include: ['*.jar'])
							}
						}
	• Дальше заходим в settings.gradle, и прописываем туда имена модулей в таком виде include 'sampleModule1', 'sampleModule2', 'sampleModule3', 'sampleModule4'
	• Дальше выполняем gradle build 
	• Дальше выполянем gradle cleanIdea
	• Дальше выполянем gradle idea
	• Заходим в настройки проекта, выбираем Modules, нажимаем на зелёный +, выбираем Import Module, выбираем папку с модулем, повторяем X раз
	• Нажимаем improt module from external model, выбираем Gradle, дальше некст и ОК