package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.InvalidNricException;
import seedu.address.model.room.RoomNumber;

public class NricTest {

    @Test
    public void constructor_invalidNric_throwsInvalidIdException() {
        //should throw an error since NRICs should not be empty strings
        assertThrows(InvalidNricException.class, () -> Nric.of(""));
    }


    @Test
    public void isValidId() {
        assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));
        assertTrue(Nric.isValidNric("hello"));
    }

    @Test
    public void isSameId() {
        Nric nricOne = Nric.of("test1");

        // nextId parameter starts at 0. calling empty .of() created a new Id based on current nextId value and
        // automatically increments nextId by 1
        Nric nricZero = Nric.of("test0");
        Nric nricOneSame = Nric.of("test1");

        assertFalse(nricOne.equals(nricZero));
        assertTrue(nricOne.equals(nricOneSame));
    }

    @Test
    public void sameIdTest() {
        Nric nricZero = Nric.of("test0");
        Nric nricZeroSame = Nric.of("test0");
        Nric nricOne = Nric.of("test1");

        assertTrue(nricZeroSame.equals(nricZero));
        assertFalse(nricZero.equals(nricOne));
    }

    @Test
    public void getValueTest() {
        Nric idZero = Nric.of("test0");
        Nric idZeroSame = Nric.of("test0");
        Nric idOne = Nric.of("test1");

        assertTrue(idZeroSame.getValue() == idZero.getValue());
        assertFalse(idZero.getValue() == idOne.getValue());
    }

    @Test
    public void equalsTest() {
        Nric nricZero = Nric.of("test0");
        Nric nricZeroSame = Nric.of("test0");
        Nric nricOne = Nric.of("test1");
        RoomNumber roomNumber = new RoomNumber("005");

        assertTrue(nricZero.equals(nricZero));
        assertTrue(nricZeroSame.equals(nricZero));
        assertFalse(nricZero.equals(nricOne));
        assertFalse(nricZero.equals(roomNumber));
    }

    @Test
    public void hashcodeTest() {
        Nric nricZero = Nric.of("test0");
        Nric nricZeroSame = Nric.of("test0");
        Nric nricOne = Nric.of("test1");

        assertTrue(nricZeroSame.hashCode() == nricZero.hashCode());
        assertFalse(nricZero.hashCode() == nricOne.hashCode());
    }
}