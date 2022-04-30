package strategy;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;

public class BulletPropScattering implements BulletPropAbstractStrategy {
    @Override
    public void active(HeroAircraft heroAircraft) {
        Thread t = new Thread(() -> {
            synchronized (Main.Bullet_LOCK) {
                heroAircraft.setIncreaseShootNum(1);
                heroAircraft.reverseScattering();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                heroAircraft.setIncreaseShootNum(0);
                heroAircraft.reverseScattering();
            }
        });
            t.start();
    }
}
