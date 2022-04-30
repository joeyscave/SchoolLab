package edu.hitsz.factory.prop;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;

public class BloodPropFactory implements AbstractPropFactory {
    @Override
    public AbstractProp create(int x, int y) {
        return new BloodProp(x, y, 0, 3, 30);
    }
}
