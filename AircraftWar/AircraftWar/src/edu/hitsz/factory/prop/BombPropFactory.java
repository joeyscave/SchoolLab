package edu.hitsz.factory.prop;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombProp;

public class BombPropFactory implements AbstractPropFactory {
    @Override
    public AbstractProp create(int x, int y) {
        return new BombProp(x, y, 0, 3);
    }
}
