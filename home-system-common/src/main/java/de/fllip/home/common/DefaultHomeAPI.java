package de.fllip.home.common;

import de.fllip.home.api.HomeAPI;
import de.fllip.home.api.repository.HomeRepository;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:46
 */
public class DefaultHomeAPI implements HomeAPI {

    private final HomeRepository homeRepository;

    public DefaultHomeAPI(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    @Override
    public HomeRepository getHomeRepository() {
        return this.homeRepository;
    }

}
