package ru.yandex.practicum.gym;

import java.util.*;

public class GymMaster {
    // Расписание тренировок
    public static TimeTable timeTable = new TimeTable();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GymMaster gym = new GymMaster();


        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Усольцев", "Василий", "Николаевич");


        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach2);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach);



        timeTable.addNewTrainingSession(mondayChildTrainingSession,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timeTable.addNewTrainingSession(thursdayChildTrainingSession,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        //gym.start();
    }

    public void start() {
        boolean isRunning = true;
        int currentMenu = 0;

        while (isRunning) {
            currentMenu = menu();
            switch (currentMenu) {
                case 1:
                    // Добавление тренировки
                    addTraining();
                    break;
                case 2:
                    // Получение всех тренировок за конкретный день недели
                    getAllTrainingsDayOfWeek();
                    break;
                case 3:
                    // Получение всех тренировок, начинающихся в конкретное время, за конкретный день недели
                    getAllTrainingsDayOfWeekAndTime();
                    break;
                case 4:
                    // Расчет кол-ва тренировок у тренеров
                    int currentCoach = 1;
                    List<CounterOfTrainings> listCounterOfTrainings = timeTable.getCountByCoaches();

                    if (listCounterOfTrainings == null) {
                        System.out.println("Тренировки не проводились!");
                        break;
                    }
                    for (CounterOfTrainings entry: listCounterOfTrainings) {
                        System.out.println(currentCoach++ + ") Тренер " + entry + " провел - " + entry.getCounter());
                    }
                    break;
                case 5:
                    // Выход
                    isRunning = false;
                    break;
                default:
                    System.out.println("Выбран неверный пункт меню!");
            }
        }

    }

    // Меню
    public int menu() {
        System.out.println("-".repeat(20));
        System.out.println("Меню системы гимнастического зала:");
        System.out.println("1 - Добавление тренировки");
        System.out.println("2 - Получение всех тренировок за конкретный день недели");
        System.out.println("3 - Получение всех тренировок, начинающихся в конкретное время, за конкретный день недели");
        System.out.println("4 - Расчет кол-ва тренировок у трениров");
        System.out.println("5 - Выход");
        System.out.print("---> ");
        return scanner.nextInt();
    }

    // Добавление тренировки
    public void addTraining() {
        // Группа
        String titleGroup = null;
        System.out.print("Введите название группы: ");
        titleGroup = scanner.next();

        Age age = null;
        System.out.print("Введите тип группы(1 - взрослая или 2 - детская): ");
        int inputAge = scanner.nextInt();
        if (inputAge == 1) {
            age = Age.ADULT;
        } else if (inputAge == 2) {
            age = Age.CHILD;
        } else {
            System.out.println("Введен некорректный тип группы!");
            return;
        }

        Integer duration = null;
        System.out.print("Введите длительность занятия в группе(в минутах): ");
        duration = scanner.nextInt();

        Group group = new Group(titleGroup, age, duration);

        // Тренер
        String surname, name, middleName;

        System.out.print("Введите фамилию тренера: ");
        surname = scanner.next();

        System.out.print("Введите имя тренера: ");
        name = scanner.next();

        System.out.print("Введите отчество тренера: ");
        middleName = scanner.next();

        Coach coach = new Coach(surname, name, middleName);

        // День недели
        DayOfWeek dayOfWeek = inputDayOfWeek();

        if (dayOfWeek == null) {
            return;
        }

        // Время
        TimeOfDay timeOfDay = inputTimeOfDay();

        if (timeOfDay == null) {
            return;
        }

        // Записываем в расписание тренировку
        timeTable.addNewTrainingSession(new TrainingSession(group,coach), dayOfWeek, timeOfDay);
    }

    public DayOfWeek inputDayOfWeek() {
        System.out.print("Введите день недели(1 - ПН, 2 - ВТ и тд): ");
        switch (scanner.nextInt()) {
            case 1:
                return DayOfWeek.MONDAY;
            case 2:
                return DayOfWeek.TUESDAY;
            case 3:
                return DayOfWeek.WEDNESDAY;
            case 4:
                return DayOfWeek.THURSDAY;
            case 5:
                return DayOfWeek.FRIDAY;
            case 6:
                return DayOfWeek.SATURDAY;
            case 7:
                return DayOfWeek.SUNDAY;
            default:
                System.out.println("Введен неверный день недели!");
                return null;
        }
    }

    public TimeOfDay inputTimeOfDay() {
        System.out.print("Введите время ");
        System.out.print("Часы(от 0 до 23): ");
        int hours = scanner.nextInt();

        if (hours > 23 || hours < 0) {
            System.out.println("Часы некорректно введены!");
            return null;
        }

        System.out.print("Минуты (от 0 до 59): ");
        int minutes = scanner.nextInt();

        if (minutes > 59 || minutes < 0) {
            System.out.println("Минуты некорректно введены!");
            return null;
        }

        return new TimeOfDay(hours, minutes);
    }

    public void getAllTrainingsDayOfWeek() {
        DayOfWeek dayOfWeek = inputDayOfWeek();

        if (dayOfWeek == null) {
            return;
        }

        Map<TimeOfDay, List<TrainingSession>> tempWeek = timeTable.getTrainingSessionsForDay(dayOfWeek);
        int currentTraining = 1;

        if (tempWeek == null) {
            System.out.println("В рамках данного дня тренировок нет!");
            return;
        }

        for (Map.Entry<TimeOfDay, List<TrainingSession>> entry: tempWeek.entrySet()) {
            for (TrainingSession tempTrainingSession: entry.getValue()) {
                System.out.println(currentTraining++ + ") " + entry.getKey() + " - "
                        + tempTrainingSession.getGroup().getTitle());

                System.out.print("Тип: ");
                if (tempTrainingSession.getGroup().getAge() == Age.CHILD) {
                    System.out.println("Детская");
                } else {
                    System.out.println("Взрослая");
                }

                System.out.println("Продолжительность: " + tempTrainingSession.getGroup().getDuration());
                System.out.println("Тренер: " + tempTrainingSession.getCoach());
            }
        }
    }

    public void getAllTrainingsDayOfWeekAndTime() {
        // День недели
        DayOfWeek dayOfWeek = inputDayOfWeek();

        if (dayOfWeek == null) {
            return;
        }

        // Время
        TimeOfDay timeOfDay = inputTimeOfDay();

        if (timeOfDay == null) {
            return;
        }
        List<TrainingSession> tempTrainingSessins = timeTable.getTrainingSessionsForDayAndTime(
                dayOfWeek, timeOfDay);

        if (tempTrainingSessins == null) {
            System.out.println("На данное время тренировок нет!");
            return;
        }

        int currentTraining = 1;

        for (TrainingSession trainingSession: tempTrainingSessins) {
            System.out.println(currentTraining++ + ") " + trainingSession.getGroup().getTitle());

            System.out.print("Тип: ");
            if (trainingSession.getGroup().getAge() == Age.CHILD) {
                System.out.println("Детская");
            } else {
                System.out.println("Взрослая");
            }

            System.out.println("Продолжительность: " + trainingSession.getGroup().getDuration());
            System.out.println("Тренер: " + trainingSession.getCoach());
        }
    }
}
