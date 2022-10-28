package de.fllip.home.api;

import de.fllip.home.api.repository.HomeRepository;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:03
 */
public interface HomeAPI {

    /**
     * @return the repository of {@link Home Homes}
     */
    HomeRepository getHomeRepository();

}
