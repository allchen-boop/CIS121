import static org.junit.Assert.*;

import org.junit.Test;

public class WoodchuckTest {

    @Test
    public void testSmallValidWriteupExample() {
        long[] validLengths = {3, 5};
        assertEquals(1, Woodchuck.canCompleteFloor(validLengths));
    }
    
    @Test
    public void testMediumValidWriteupExample() {
        long[] validLengths = {90, 6, 18, 10, 10};
        assertEquals(2, Woodchuck.canCompleteFloor(validLengths));
    }
    
    @Test
    public void testlonger() {
        long[] validLengths = {30, 10};
        assertEquals(10, Woodchuck.canCompleteFloor(validLengths));
    }

    @Test
    public void testInvalidWriteupExample() {
        long[] validLengths = {363, 7, 51};
        assertEquals(-1, Woodchuck.canCompleteFloor(validLengths));
    }
    
    @Test
    public void testbad() {
        long[] validLengths = {2, 7, 11};
        assertEquals(-1, Woodchuck.canCompleteFloor(validLengths));
    }

    @Test
    public void testNotJustGcd() {
        long[] validLengths = {20, 5};
        assertEquals(-1, Woodchuck.canCompleteFloor(validLengths));
    }

    @Test
    public void testInvalid() {
        long[] validLengths = {20, 6};
        assertEquals(-1, Woodchuck.canCompleteFloor(validLengths));
    }
    
    @Test
    public void testok() {
        long[] validLengths = {20, 30};
        assertEquals(-1, Woodchuck.canCompleteFloor(validLengths));
    }
    
    @Test
    public void testAllSame() {
        long[] validLengths = {4, 4, 4};
        assertEquals(4, Woodchuck.canCompleteFloor(validLengths));
    }

    @Test
    public void testAllSamegud() {
        long[] validLengths = {5,5,5};
        assertEquals(5, Woodchuck.canCompleteFloor(validLengths));
    }
}

