# Репозиторий для практической работы на языке программирования Java
# Авторы: Адамов Ярослав, Алясова Анастасия, Габов Егор
## 1) Правила создания и работы с ветками
#### Ветка master 
- Главная ветка, в ней содержится только отлаженный рабочий код. Заливать изменения в master напрямую нельзя !!! Все изменения в master должны происходить только через pull request.  
#### Как правильно создать ветку и внести изменения в рабочий код
- Необходимо определить, в какую именно ветку Вам необходимо внести изменения (обычно это master, но в общем случае это не всегда так). Необходимо перейти на ветку, в которой вы хотите совершить изменения и создать от данной ветки новую с помощью команды "git chechout -b "new branch name"".  
- Имя ветки начинается с номера задачи в трекере!!! (в issue) 
- Вносятся изменения в коде
- Из измененных файлов формируется коммит - для этого с помощью команды "git status" необходимо посмотреть какие файлы были изменены. С помощью команды "git add "changed file name"" файлы вносятся в коммит. Если в коммите оказались лишние файлы - их можно убрать командой "git reset HEAD "file name"". С помощью команды "git commit -m "some comment"" - создается коммит и описывают изменения, которые он вносит. Командой "git push origin "branch name""  коммит отправляется на удаленный репозиторий. - В удаленном репозитории необходимо создать pull request - в pull request коллеги смогут посмотреть какие изменения вносит ваш код в основной код и обсудить ваше решение. Pull request обязателен!!!
## 2) Issue
#### Что из себя представляет issue
- Issue - это удобный инструмент для совместной работы и планирования задач. Находится это здесь: https://github.com/GabovYegor/Java_practice/issues
#### Как это работает
- Коллеги обсуждают и решают, какие задачи им требуется решить. Каждая такая задача заносится в issue. С помощью issue легко отслеживать состояние задач своих коллег и следить за продвижением работы.
#### Базовые сведения об Issue
- У issue - есть 3 состояния: task - задания, требующие выполнения, Done - выполненная задача, Help wanted - задача требует обсуждения с коллегами. 
- У Issue есть поле assignees - в нем задача назначается на определенного участника проекта 
- Следует создавать issue для каждой задачи, которую Вы решаете выполнить - это позволяет коллегам отслеживать Ваши достижения, а также упорядочить ваши собственные задачи 
- В Issue необходимо подробно описывать задачу, стоящую перед Вами
