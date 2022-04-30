package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.aircraft.AbstractAircraftFactory;
import edu.hitsz.factory.aircraft.EliteEnemyFactory;
import edu.hitsz.factory.aircraft.MobEnemyFactory;
import edu.hitsz.observer.Subscriber;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.settings.Settings;
import edu.hitsz.thread.SimpleThreadfactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;
    ScheduledFuture<?> sf;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;

    private int enemyMaxNumber = 5;

    private boolean gameOverFlag = false;
    private int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    /**
     * 飞行器工厂，用于创建不同飞行器
     */
    AbstractAircraftFactory enemyFactory;

    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        //Scheduled 线程池，用于定时任务调度
        executorService = new ScheduledThreadPoolExecutor(10, new SimpleThreadfactory());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {


        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 增大游戏难度
            if (time % 9600 == 0) Main.settings.harder();

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    if (Math.random() < Main.settings.eliteEnemyEmergeProb) {
                        enemyFactory = new EliteEnemyFactory();
                    } else {
                        enemyFactory = new MobEnemyFactory();
                    }
                    enemyAircrafts.add(enemyFactory.create());
                }
                // boss产生
                if (Main.settings.score != 0 && Main.settings.score % Main.settings.bossEnemyEmergeScore == 0) {
                    BossEnemy bossEnemy = BossEnemy.getInstance();
                    if (bossEnemy != null) {
                        enemyAircrafts.add(bossEnemy);
                        if (Settings.audio) {
                            sf = executorService.scheduleWithFixedDelay(new MusicThread("src\\videos\\bgm_boss.wav"), 0, 1, TimeUnit.MILLISECONDS);
                        }
                    }
                }
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                gameOverFlag = true;
                // 播放停止音效，停止背景音效
                if (Settings.audio) {
                    executorService.schedule(new MusicThread("src\\videos\\game_over.wav"), 0, TimeUnit.MILLISECONDS);
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    MusicThread.gameOver = true;
                }
                executorService.shutdownNow();
                this.setVisible(false);
                synchronized (Main.Main_LOCK) {
                    Main.Main_LOCK.notify();
                }
                System.out.println("Game Over!");
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
        if (Settings.audio) {
            executorService.scheduleWithFixedDelay(new MusicThread("src\\videos\\bgm.wav"), 0, 1, TimeUnit.MILLISECONDS);
        }
    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // 敌机射击
        for (int i = 0; i < enemyAircrafts.size(); i++) {
            if (enemyAircrafts.get(i) instanceof EliteEnemy) {
                enemyBullets.addAll(enemyAircrafts.get(i).shoot());
            }
        }

        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propMoveAction() {
        for (AbstractFlyingObject prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 播放音效
                    if (Settings.audio) {
                        executorService.schedule(new MusicThread("src\\videos\\bullet_hit.wav"), 0, TimeUnit.MILLISECONDS);
                    }
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // 获得分数，精英机一定几率产生道具补给
                        Main.settings.score += 10;
                        AbstractProp prop = enemyAircraft.generateProp();
                        if (prop != null) {
                            props.add(prop);
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        //  我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (heroAircraft.crash(prop)) {
                if (Settings.audio) {
                    executorService.schedule(new MusicThread(prop.getMusicPath()), 0, TimeUnit.MILLISECONDS);
                }
                if (prop instanceof BombProp) {
                    for (AbstractAircraft subscriber : enemyAircrafts) {
                        ((BombProp) prop).subscribe((Subscriber) subscriber);
                    }
                    for (BaseBullet subscriber : enemyBullets) {
                        ((BombProp) prop).subscribe((Subscriber) subscriber);
                    }
                }
                prop.vanish();
                prop.active(heroAircraft, enemyAircrafts, enemyBullets);
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     * 4. 重置弹道数
     * 5. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        // 停止坠毁boss机音效
        if (Settings.audio) {
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft instanceof BossEnemy && enemyAircraft.notValid()) {
                    sf.cancel(true);
                }
            }
        }
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
//        System.out.println(Settings.getBackGroundIndex());
        BufferedImage bg = ImageManager.BACKGROUND_IMAGE.get(Settings.backGroundIndex);
        g.drawImage(bg, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(bg, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, props);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + Main.settings.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
