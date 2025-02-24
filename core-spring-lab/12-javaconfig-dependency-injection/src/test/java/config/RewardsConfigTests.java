package config;

import org.assertj.core.api.Fail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

import javax.sql.DataSource;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test the Spring configuration class to ensure it is creating the right
 * beans.
 */
class RewardsConfigTests {
    // Provide a mock object for testing
    private final DataSource dataSource = Mockito.mock(DataSource.class);

    private final RewardsConfig rewardsConfig = new RewardsConfig(dataSource);

    @Test
    void getBeans() {
        AccountRepository accountRepository = rewardsConfig.accountRepository();
        assertInstanceOf(JdbcAccountRepository.class, accountRepository);
        checkDataSource(accountRepository);

        RestaurantRepository restaurantRepository = rewardsConfig.restaurantRepository();
        assertInstanceOf(JdbcRestaurantRepository.class, restaurantRepository);
        checkDataSource(restaurantRepository);

        RewardRepository rewardsRepository = rewardsConfig.rewardRepository();
        assertInstanceOf(JdbcRewardRepository.class, rewardsRepository);
        checkDataSource(rewardsRepository);

        RewardNetwork rewardNetwork = rewardsConfig.rewardNetwork(accountRepository, restaurantRepository, rewardsRepository);
        assertInstanceOf(RewardNetworkImpl.class, rewardNetwork);
    }

    /**
     * Ensure the data-source is set for the repository. Uses reflection as we do
     * not wish to provide a getDataSource() method.
     *
     * @param repository One of our three repositories.
     */
    private void checkDataSource(Object repository) {
        Class<?> repositoryClass = repository.getClass();

        try {
            Field dataSourceField = repositoryClass.getDeclaredField("dataSource");
            dataSourceField.setAccessible(true);
            assertNotNull(dataSourceField.get(repository));
        } catch (Exception ex) {
            String failureMessage = "Unable to validate dataSource in " + repositoryClass.getSimpleName();
            System.out.println(failureMessage);
            System.err.println(ex.getMessage());
            Fail.fail(failureMessage);
        }
    }
}
