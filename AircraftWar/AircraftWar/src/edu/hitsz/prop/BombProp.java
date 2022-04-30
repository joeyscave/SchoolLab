package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.observer.Subscriber;
import edu.hitsz.observer.Publisher;

import java.util.LinkedList;
import java.util.List;

public class BombProp extends AbstractProp implements Publisher {

    private List<Subscriber> subscribers = new LinkedList<>();

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft, List<AbstractAircraft> enemyAircraft, List<BaseBullet> enemyBullet) {
        notifySubscriber();
        System.out.println("BombPropActive!");
    }

    @Override
    public String getMusicPath() {
        return "src\\videos\\bomb_explosion.wav";
    }


    @Override
    public void notifySubscriber() {
        for (Subscriber subscriber : subscribers) {
                subscriber.update();
        }
        subscribers.clear();
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }
}
