package de.fllip.home.common.repository;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariDataSource;
import de.fllip.home.api.Home;
import de.fllip.home.api.repository.HomeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:51
 */
public class DefaultHomeRepository implements HomeRepository {

    private final HikariDataSource hikariDataSource;

    public DefaultHomeRepository(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    @Override
    public CompletableFuture<Void> createTable() {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = this.hikariDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(HomeDatabaseQueries.CREATE_TABLE_QUERY)) {
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Home> findHomeByHomeNameAndOwnerId(String homeName, UUID ownerId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = this.hikariDataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(HomeDatabaseQueries.FIND_HOME_BY_NAME_AND_OWNER_ID_QUERY)) {
                preparedStatement.setString(1, homeName);
                preparedStatement.setString(2, ownerId.toString());

                ResultSet resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    throw new IllegalStateException();
                }

                return this.getHomeByResultSet(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<List<Home>> findAllHomesByOwnerId(UUID ownerId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = this.hikariDataSource.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(HomeDatabaseQueries.FIND_HOMES_BY_OWNER_QUERY)) {
                preparedStatement.setString(1, ownerId.toString());
                ResultSet resultSet = preparedStatement.executeQuery();

                List<Home> homes = Lists.newArrayList();

                while (resultSet.next()) {
                    homes.add(this.getHomeByResultSet(resultSet));
                }

                return homes;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> saveHome(Home home) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = this.hikariDataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(HomeDatabaseQueries.SAVE_HOME_QUERY)) {
                preparedStatement.setString(1, home.name());
                preparedStatement.setString(2, home.ownerId().toString());
                preparedStatement.setString(3, home.worldId().toString());

                preparedStatement.setDouble(4, home.x());
                preparedStatement.setDouble(5, home.y());
                preparedStatement.setDouble(6, home.z());

                preparedStatement.setFloat(7, home.yaw());
                preparedStatement.setFloat(8, home.pitch());

                preparedStatement.execute();
            }  catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> deleteHomeByNameAndOwnerId(String homeName, UUID ownerId) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = this.hikariDataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(HomeDatabaseQueries.DELETE_HOME_BY_NAME_AND_OWNER_ID_QUERY)) {
                preparedStatement.setString(1, homeName);
                preparedStatement.setString(2, ownerId.toString());

                int updated = preparedStatement.executeUpdate();

                if (updated == 0) {
                    throw new RuntimeException();
                }
            }  catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> deleteAllHomeByOwnerId(UUID ownerId) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = this.hikariDataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(HomeDatabaseQueries.DELETE_HOMES_OWNER_ID_QUERY)) {
                preparedStatement.setString(1, ownerId.toString());

                preparedStatement.execute();
            }  catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Home getHomeByResultSet(ResultSet resultSet) throws SQLException {
        String homeName = resultSet.getString("name");
        String ownerIdString = resultSet.getString("ownerId");
        String worldIdString = resultSet.getString("worldId");
        UUID ownerId = UUID.fromString(ownerIdString);
        UUID worldId = UUID.fromString(worldIdString);
        double x = resultSet.getDouble("x");
        double y = resultSet.getDouble("y");
        double z = resultSet.getDouble("z");
        float yaw = resultSet.getFloat("yaw");
        float pitch = resultSet.getFloat("pitch");

        return new Home(homeName, ownerId, worldId, x, y, z, yaw, pitch);
    }

}
