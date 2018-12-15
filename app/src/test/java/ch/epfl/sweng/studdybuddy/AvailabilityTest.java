package ch.epfl.sweng.studdybuddy;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;

import static org.junit.Assert.assertEquals;

public class AvailabilityTest {

    int columnTest = 0;
    int rowTest = 0;

    Availability A = new ConcreteAvailability();
    Availability Available = new ConcreteAvailability();


    @Before
    public void setUp(){
        Available.modifyAvailability(rowTest,columnTest);
    }

    @Test
    public void addAvailabilityInParticularSlot(){
        A.modifyAvailability(rowTest, columnTest);
        checkHeadTrueTailFalse(A, rowTest, columnTest);
    }

    @Test
    public void removeAvailabilityInParticularSlot(){
        Available.modifyAvailability(rowTest, columnTest);
        checkAllFalse(Available);
    }

    @Test
    public void fromAShortList(){
        List<Boolean> list = new ArrayList<>();
        Availability shortAvailabilities = new ConcreteAvailability(list);
        try{
            shortAvailabilities.modifyAvailability(ConcreteAvailability.rowsNum -1 , ConcreteAvailability.columnsNum - 1);
            assert(true);
        } catch(IndexOutOfBoundsException e){
            assert(false);
        }
     }

   @Test(expected = ArrayIndexOutOfBoundsException.class)
   public void negativeRow(){
        A.modifyAvailability(-1, 0);
   }

   @Test(expected = ArrayIndexOutOfBoundsException.class)
   public void negativeColumn(){
        A.modifyAvailability(0, -1);
   }

   @Test(expected = ArrayIndexOutOfBoundsException.class)
   public void rowOutOfBound(){
        A.modifyAvailability(11,0);
   }

   @Test(expected = ArrayIndexOutOfBoundsException.class)
   public void columnOutOfBound(){
        A.modifyAvailability(0,11);
   }

    public static void checkAllFalse(Availability list){
        int L = ConcreteAvailability.rowsNum;
        int l = ConcreteAvailability.columnsNum;
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < l; j++) {
                assertEquals(false, list.isAvailable(i, j));
            }
        }
    }
    public static void checkHeadTrueTailFalse(Availability list, int rowTest, int columnTest){
        int L = ConcreteAvailability.rowsNum;
        int l = ConcreteAvailability.columnsNum;
        assertEquals(true, list.isAvailable(rowTest,columnTest));
        for (int i = 0; i < L; i++){
            for(int j = 0; j < l; j++){
                if(i!=rowTest || j!= columnTest){
                    assertEquals(false, list.isAvailable(i, j));
                }
            }
        }
    }
}
