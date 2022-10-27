package de.fllip.home.api.repository;

import de.fllip.home.api.Home;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:03
 */
public interface HomeRepository {

    /**
     * Creates the table
     * @return {@link CompletableFuture} when the table was created or throws an exception, if an error occurs
     */
    CompletableFuture<Void> createTable();

    /**
     * Finds a {@link Home} by a name and an owner id
     *
     * @param homeName the name of the home
     * @param ownerId the id of the owner
     * @return {@link CompletableFuture} with the home or throws an exception, if no home with this name exists
     */
    CompletableFuture<Home> findHomeByHomeNameAndOwnerId(String homeName, UUID ownerId);

    /**
     * Finds all {@link Home}s by a specific owner
     *
     * @param ownerId the id of the owner
     * @return {@link CompletableFuture} with a list of {@link Home}s filtered by the owner id
     */
    CompletableFuture<List<Home>> findAllHomesByOwnerId(UUID ownerId);

    /**
     * Saves a specific home
     *
     * @param home the Home to be saved
     * @return {@link CompletableFuture} when the home was saved or throws an exception, when the home cannot be saved
     */
    CompletableFuture<Void> saveHome(Home home);


    /**
     * Deletes a specific {@link Home} by a name
     *
     * @param homeName the name of the home, that will be deleted
     * @param ownerId the id of the owner
     * @return CompletableFuture when the home was deleted or throws an exception, when the home cannot be deleted
     */
    CompletableFuture<Void> deleteHomeByNameAndOwnerId(String homeName, UUID ownerId);

}
