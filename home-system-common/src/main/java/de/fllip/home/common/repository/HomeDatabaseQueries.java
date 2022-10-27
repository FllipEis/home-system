package de.fllip.home.common.repository;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 26.10.22
 * Time: 19:47
 */
public class HomeDatabaseQueries {

    public static final String CREATE_TABLE_QUERY = """
                create table homes (
                    `name` varchar(30) not null,
                    `ownerId` char(36) not null,
                    `worldId` char(36) not null,
                    `x` double precision(8, 2) not null,
                    `y` double precision(8, 2) not null,
                    `z` double precision(8, 2) not null,
                    `yaw` float(3, 2) not null,
                    `pitch` float(3, 2) not null,
                    
                    primary key(`name`, `ownerId`)
                );
            """;

    public static final String FIND_HOME_BY_NAME_AND_OWNER_ID_QUERY = "select * from homes where `name`=? and `ownerId`=? limit 1;";

    public static final String FIND_HOMES_BY_OWNER_QUERY = "select * from homes where `ownerId`=?;";

    public static final String SAVE_HOME_QUERY = """
                insert into homes (`name`, `ownerId`, `worldId`, `x`, `y`, `z`, `yaw`, `pitch`)
                values (?, ?, ?, ?, ?, ?, ?, ?);
            """;

    public static final String DELETE_HOME_BY_NAME_QUERY = "delete from homes where `name`=?;";
    
}
