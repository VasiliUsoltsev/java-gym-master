package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class TimetableTest {
    public TimeTable timetable = new TimeTable();
    public Group group = new Group("Акробатика для детей", Age.CHILD, 60);
    public Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
    public Coach coach2 = new Coach("Усольцев", "Василий", "Николаевич");
    public Coach coach3 = new Coach("Дергилев", "Олег", "Викторович");
    Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
    Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
    TrainingSession singleTrainingSession = new TrainingSession(group, coach);
    TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach);
    TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach2);
    TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach);
    TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach3);

    //Проверить, что за понедельник вернулось одно занятие
    @Test
    void testGetTrainingSessionsForDaySingleSessionTrue() {
        timetable.addNewTrainingSession(singleTrainingSession, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
    }

    //Проверить, что за вторник не вернулось занятий
    @Test
    void testGetTrainingSessionsForDaySingleSessionFalse() {
        timetable.addNewTrainingSession(singleTrainingSession, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    // Проверить, что за понедельник вернулось одно занятие
    @Test
    void testGetTrainingSessionsForDayMultipleSessionsMondayReturn1() {
        timetable.addNewTrainingSession(thursdayAdultTrainingSession,
                        DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(mondayChildTrainingSession,
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(thursdayChildTrainingSession,
                        DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(saturdayChildTrainingSession,
                        DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
    }

    // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
    @Test
    void testGetTrainingSessionsForDayMultipleSessionsThursdayReturn2() {
        timetable.addNewTrainingSession(thursdayAdultTrainingSession,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(mondayChildTrainingSession,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(thursdayChildTrainingSession,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(saturdayChildTrainingSession,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        Map<TimeOfDay, List<TrainingSession>> temp = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        // Проверяем на кол-во занятий
        Assertions.assertEquals(2, temp.size());

        //Проверяем на правильную последовательность
        TimeOfDay time1,time2;
        time1 = time2 = null;
        for (Map.Entry<TimeOfDay, List<TrainingSession>> entry: temp.entrySet()) {
            if (time1 == null) time1 = entry.getKey();
            else time2 = entry.getKey();
        }

        Assertions.assertEquals("13:00", time1.toString());
        Assertions.assertEquals("20:00", time2.toString());
    }

    // Проверить, что за вторник не вернулось занятий
    @Test
    void testGetTrainingSessionsForDayMultipleSessionsTuesdayReturn0() {
        timetable.addNewTrainingSession(thursdayAdultTrainingSession,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(mondayChildTrainingSession,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(thursdayChildTrainingSession,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(saturdayChildTrainingSession,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));



        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    //Проверить, что за понедельник в 13:00 вернулось одно занятие
    @Test
    void testGetTrainingSessionsForDayAndTimeMonday13Return1() {
        timetable.addNewTrainingSession(singleTrainingSession,
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertEquals(1,
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());
    }

    //Проверить, что за понедельник в 14:00 не вернулось занятий
    @Test
    void testGetTrainingSessionsForDayAndTimeMonday14Return0() {
        timetable.addNewTrainingSession(singleTrainingSession,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)));
    }

    // Проверить, что считается по кол-ву тренировок при пустом расписании
    @Test
    void testGetCountByCoachesEmpty() {
        Assertions.assertNull(timetable.getCountByCoaches());
    }

    // Проверить, сколько выводится тренеров если внести в расписание 3, у 2 тренеров по 1 занятию, а у третьего 3
    @Test
    void testGetCountByCoaches3_1_1_2() {
        timetable.addNewTrainingSession(thursdayAdultTrainingSession,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(mondayChildTrainingSession,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(thursdayChildTrainingSession,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(saturdayChildTrainingSession,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        Assertions.assertEquals(3, timetable.getCountByCoaches().size());
    }


    // Проверить, выводится ли в первым, тренер с большим и с меньшим кол-вом тренировок
    @Test
    void testGetCountByCoachesMaxAndMin() {
        timetable.addNewTrainingSession(thursdayAdultTrainingSession,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(mondayChildTrainingSession,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(thursdayChildTrainingSession,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(saturdayChildTrainingSession,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        List<CounterOfTrainings> listCounterOfTrainings = timetable.getCountByCoaches();

        int max = listCounterOfTrainings.get(0).getCounter();
        Assertions.assertEquals(2, max);

        int min = listCounterOfTrainings.get(2).getCounter();
        Assertions.assertEquals(1, min);
    }


























}
