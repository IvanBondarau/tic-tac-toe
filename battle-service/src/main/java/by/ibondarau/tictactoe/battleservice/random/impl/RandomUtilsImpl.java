package by.ibondarau.tictactoe.battleservice.random.impl;

import by.ibondarau.tictactoe.battleservice.random.RandomUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
@Scope("singleton")
public class RandomUtilsImpl implements RandomUtils {

    private final Random random;

    public RandomUtilsImpl() {
        random = new SecureRandom();
    }


    @SafeVarargs
    @Override
    public final <T> T getRandomOf(T... args) {
        return args[random.nextInt(args.length)];
    }
}
