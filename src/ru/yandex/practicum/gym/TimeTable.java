package ru.yandex.practicum.gym;

import java.util.*;

public class TimeTable {

    private Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timeTable;

    public TimeTable() {
        timeTable = new HashMap<>();
    }

    // Добавление тренировки в расписание(требование к алгоритмической сложности нет)
    public void addNewTrainingSession(TrainingSession trainingSession, DayOfWeek dayOfWeek, TimeOfDay timeOfDay ) {
        Map<TimeOfDay, List<TrainingSession>> temp;

        // Проверяем, есть ли введенный день недели
        if (timeTable.containsKey(dayOfWeek)) {
            // Если введенный день уже есть
            temp = timeTable.get(dayOfWeek);

            // Проверяем, есть ли такое время
            if (temp.containsKey(timeOfDay)) {
                // Есть такое время
                List<TrainingSession> tempArrayTrainingSession = temp.get(timeOfDay);

                tempArrayTrainingSession.add(trainingSession);
                temp.put(timeOfDay, tempArrayTrainingSession);
            } else {
                // Нет такого времени
                List<TrainingSession> tempArrayTrainingSession = new ArrayList<>();

                tempArrayTrainingSession.add(trainingSession);
                temp.put(timeOfDay, tempArrayTrainingSession);
            }

            timeTable.put(dayOfWeek,temp);

        } else {
            // Данный день недели впервые ввели
            temp = new TreeMap<>();
            List<TrainingSession> tempArrayTrainingSession = new ArrayList<>();

            tempArrayTrainingSession.add(trainingSession);
            temp.put(timeOfDay, tempArrayTrainingSession);

            timeTable.put(dayOfWeek,temp);
        }
    }

    // Получение всех тренировок, упорядоченных по времени начала, за конкретный день недели
    // Требуемая алгоритмическая сложность O(1) - достигнута HashMap
    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timeTable.get(dayOfWeek);
    }

    // Получение всех тренировок, начинающихся в конкретное время, за конкретный день недели
    // Требуемая алгоритмическая сложность O(log(n)) - достигнута HashMap и TreeMap
    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> temp = timeTable.get(dayOfWeek);
        if (temp!= null) {
            return temp.get(timeOfDay);
        }
        return null;
    }

    // Считаем тренировки
    public List<CounterOfTrainings> getCountByCoaches() {
        List<CounterOfTrainings> countByCoaches = null;

        // Пробегаемся по расписанию для выявления тренеров и их подсчета
        // В первом цикле пробегаемся по дням недели
        for (Map.Entry<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> entryDayOfWeek: timeTable.entrySet()) {
            // Во втором цикле пробегаемся времени занятия
            for (Map.Entry<TimeOfDay, List<TrainingSession>> entryTimeOfDay: entryDayOfWeek.getValue().entrySet()) {
                // В третьем цикле, пробегаемся по списку тренировок
                for (TrainingSession temp: entryTimeOfDay.getValue()) {
                    CounterOfTrainings tempCoach = new CounterOfTrainings(temp.getCoach());

                    if (countByCoaches != null && countByCoaches.contains(tempCoach)) {
                        // Если тренер уже есть в списке, то увеличиваем кол-во тренировок
                        int index = countByCoaches.indexOf(tempCoach);
                        CounterOfTrainings counterOfTrainings = countByCoaches.get(index);
                        counterOfTrainings.increment();
                        countByCoaches.set(index, counterOfTrainings);
                    } else {
                        // Если такого тренера нет, то заносим его и ставим 1
                        if (countByCoaches == null) countByCoaches = new ArrayList<>();
                        tempCoach.increment();
                        countByCoaches.add(tempCoach);
                    }
                }
            }
        }
        if (countByCoaches != null) Collections.sort(countByCoaches);
        return countByCoaches;
    }

    // Очищаем расписание
    public void clearTimeTable() {
        timeTable.clear();
    }
}
