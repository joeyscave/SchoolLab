package edu.hitsz.observer;

public interface Publisher {
    void notifySubscriber();
    void subscribe(Subscriber subscriber);
}
