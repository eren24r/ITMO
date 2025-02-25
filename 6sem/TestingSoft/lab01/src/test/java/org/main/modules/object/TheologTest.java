package org.main.modules.object;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TheologTest {
    @Test
    void isAddedTheolian(){
        Theolog theolog = new Theolog();
        LinkedList<Theologian> list = new LinkedList<>();
        Theologian test = new Theologian("John Doe", "Believes in destiny", 30);

        list.add(test);
        theolog.addFacts(test);

        assertArrayEquals(list.toArray(), theolog.getFacts().toArray());
    }

    @Test
    void isTheolianSerious(){
        Theolog theolog = new Theolog();
        Theologian test = new Theologian("John Doe", "Believes in destiny", 30);

        theolog.addFacts(test);
        theolog.addFacts(test);
        theolog.addFacts(test);

        assertTrue(theolog.isSeriousMan());
    }

    @Test
    void isTheologSporit(){
        Theolog theolog = new Theolog();

        assertTrue(theolog.isSporite());
    }
}
