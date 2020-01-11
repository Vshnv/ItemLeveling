package com.rocketmail.vaishnavanil.itemlevelingattempt.util;

import java.util.concurrent.ThreadLocalRandom;

public enum Chance {
    use;
    public boolean roll(int percent){
        return ThreadLocalRandom.current().nextInt(101)<=percent;
    }

}
