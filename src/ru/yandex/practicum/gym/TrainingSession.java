package ru.yandex.practicum.gym;

public class TrainingSession {

    //группа
    private Group group;
    //тренер
    private Coach coach;


    public TrainingSession(Group group, Coach coach) {
        this.group = group;
        this.coach = coach;
    }

    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

}
