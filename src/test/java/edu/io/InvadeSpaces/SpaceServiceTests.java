package edu.io.InvadeSpaces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpaceServiceTests {

    @Mock
    private SpacesReposityory spacesReposityory;

    @InjectMocks
    private SpaceService spaceService;

    @Test
    void findAllReturnsAllSpacesFromRepository() {
        List<Space> spaces = List.of(new Space(), new Space());
        when(spacesReposityory.findAll()).thenReturn(spaces);

        assertSame(spaces, spaceService.findAll());

        verify(spacesReposityory).findAll();
        verifyNoMoreInteractions(spacesReposityory);
    }

    @Test
    void addSavesAndReturnsSpace() {
        Space space = new Space();
        when(spacesReposityory.save(space)).thenReturn(space);

        assertSame(space, spaceService.add(space));

        verify(spacesReposityory).save(space);
        verifyNoMoreInteractions(spacesReposityory);
    }

    @Test
    void findByNameReturnsRepositoryResult() {
        Optional<List<Space>> result = Optional.of(List.of(new Space()));
        when(spacesReposityory.findByName("meeting room")).thenReturn(result);

        assertSame(result, spaceService.findByName("meeting room"));

        verify(spacesReposityory).findByName("meeting room");
        verifyNoMoreInteractions(spacesReposityory);
    }

    @Test
    void saveSavesAndReturnsSpace() {
        Space space = new Space();
        when(spacesReposityory.save(space)).thenReturn(space);

        assertSame(space, spaceService.save(space));

        verify(spacesReposityory).save(space);
        verifyNoMoreInteractions(spacesReposityory);
    }

    @Test
    void updateChangesExistingSpaceAndSavesIt() {
        Space existingSpace = new Space();
        Space newSpace = new Space();
        newSpace.setName("updated room");
        newSpace.setCapacity(42);
        when(spacesReposityory.findById(1L)).thenReturn(Optional.of(existingSpace));
        when(spacesReposityory.save(existingSpace)).thenReturn(existingSpace);

        Space result = spaceService.update(1L, newSpace);

        assertSame(existingSpace, result);
        assertEquals("updated room", existingSpace.name());
        assertEquals(42, existingSpace.capacity());
        verify(spacesReposityory).findById(1L);
        verify(spacesReposityory).save(existingSpace);
        verifyNoMoreInteractions(spacesReposityory);
    }

    @Test
    void updateSavesNewSpaceWhenExistingSpaceIsNotFound() {
        Space newSpace = new Space();
        when(spacesReposityory.findById(1L)).thenReturn(Optional.empty());
        when(spacesReposityory.save(newSpace)).thenReturn(newSpace);

        assertSame(newSpace, spaceService.update(1L, newSpace));

        verify(spacesReposityory).findById(1L);
        verify(spacesReposityory).save(newSpace);
        verifyNoMoreInteractions(spacesReposityory);
    }

    @Test
    void deleteRemovesExistingSpaceAndReturnsTrue() {
        Space space = new Space();
        when(spacesReposityory.findById(1L)).thenReturn(Optional.of(space));

        assertTrue(spaceService.delete(1L));

        verify(spacesReposityory).findById(1L);
        verify(spacesReposityory).delete(space);
        verifyNoMoreInteractions(spacesReposityory);
    }

    @Test
    void deleteReturnsFalseWhenSpaceIsNotFound() {
        when(spacesReposityory.findById(1L)).thenReturn(Optional.empty());

        assertFalse(spaceService.delete(1L));

        verify(spacesReposityory).findById(1L);
        verifyNoMoreInteractions(spacesReposityory);
    }
}
