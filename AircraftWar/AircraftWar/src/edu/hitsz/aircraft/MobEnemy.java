package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.settings.Settings;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.observer.Subscriber;
import edu.hitsz.prop.AbstractProp;

import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft implements Subscriber {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return null;
    }

    @Override
    public AbstractProp generateProp() {
        return null;
    }

    @Override
    public void update() {
        this.vanish();
        Main.settings.score+=10;
    }
}