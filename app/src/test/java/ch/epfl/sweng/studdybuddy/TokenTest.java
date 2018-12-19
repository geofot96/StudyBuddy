package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.services.notifications.Token;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static org.junit.Assert.assertEquals;

public class TokenTest {
    @Test
    public void createToken(){
        Token token = new Token(Messages.TEST);
        assertEquals(Messages.TEST, token.getToken());
    }
}
