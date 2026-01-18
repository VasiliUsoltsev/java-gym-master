package ru.yandex.practicum.gym;

public class CounterOfTrainings extends Coach implements Comparable<CounterOfTrainings> {
    private int counter;

    public CounterOfTrainings(Coach coach) {
        super(coach.getSurname(), coach.getName(), coach.getMiddleName());
        counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public void increment() {
        counter++;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CounterOfTrainings that = (CounterOfTrainings) o;
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return -(counter - o.counter);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
