package by.ibondarau.tictactoe.battleservice.util.impl;

import by.ibondarau.tictactoe.battleservice.util.RandomUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
@Scope("singleton")
public final class RandomUtilsImpl implements RandomUtils {

    private final Random random;

    public RandomUtilsImpl() {
        random = new SecureRandom();
    }

    @Override
    public int getRandomOf(int... args) {
        return args[random.nextInt(args.length)];
    }
}
