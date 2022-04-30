package edu.hitsz.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {

    private EliteEnemy eliteEnemy;

    @BeforeEach
    void setUp() {
        eliteEnemy=new EliteEnemy(0,0,0,0,100);
    }

    @AfterEach
    void tearDown() {
        eliteEnemy=null;
    }

    @Test
    void decreaseHp() {
        eliteEnemy.decreaseHp(10);
        assertEquals(90,eliteEnemy.getHp());
    }

    @Test
    void getHp() {
        assertEquals(100,eliteEnemy.getHp());
    }
}