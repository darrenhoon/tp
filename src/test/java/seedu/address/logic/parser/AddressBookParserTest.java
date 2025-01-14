package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ROOM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddRoomCommand;
import seedu.address.logic.commands.CheckInCommand;
import seedu.address.logic.commands.CheckOutCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindRecordCommand;
import seedu.address.logic.commands.FindRoomCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.residency.exceptions.ResidencyContainsKeywordsPredicate;
import seedu.address.model.room.RoomNumberContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_checkin() throws Exception {
        List<Index> guestIndexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        CheckInCommand command = (CheckInCommand) parser.parseCommand(
                CheckInCommand.COMMAND_WORD
                        + " "
                        + INDEX_FIRST_ROOM.getOneBased()
                        + " g/"
                        + INDEX_FIRST_PERSON.getOneBased()
                        + " g/"
                        + INDEX_SECOND_PERSON.getOneBased());
        assertEquals(new CheckInCommand(INDEX_FIRST_ROOM, guestIndexes), command);
    }

    @Test
    public void parseCommand_checkout() throws Exception {
        CheckOutCommand command = (CheckOutCommand) parser.parseCommand(
                CheckOutCommand.COMMAND_WORD + " " + INDEX_FIRST_ROOM.getOneBased());
        assertEquals(new CheckOutCommand(INDEX_FIRST_ROOM), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    /*
    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }
    */

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command;
        command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        EditCommand otherEditCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        assertEquals(otherEditCommand, command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    /*
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
    */

    @Test
    public void parseCommand_findRoom() throws Exception {
        List<String> keywords = Arrays.asList("001", "002");
        FindRoomCommand command = (FindRoomCommand) parser.parseCommand(
                FindRoomCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindRoomCommand(new RoomNumberContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findRecord() throws Exception {
        List<String> keywords = Arrays.asList("001", "Alex");
        FindRecordCommand command = (FindRecordCommand) parser.parseCommand(
                FindRecordCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindRecordCommand(new ResidencyContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    //change test cases?
    @Test
    public void parseCommand_listGuests() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " guests") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " rooms") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " rooms occupied") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " rooms vacant") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " records") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addRooms() throws Exception {
        assertTrue(parser.parseCommand(AddRoomCommand.COMMAND_WORD + " 1 t/tag") instanceof AddRoomCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
