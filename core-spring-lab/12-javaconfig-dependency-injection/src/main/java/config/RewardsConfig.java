package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class RewardsConfig {

    // Set this by adding a constructor.
    private final DataSource dataSource;

    RewardsConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public RewardNetwork rewardNetwork(AccountRepository accountRepository, RestaurantRepository restaurantRepository, RewardRepository rewardRepository) {
        return new RewardNetworkImpl(accountRepository, restaurantRepository, rewardRepository);
    }

    @Bean
    public AccountRepository accountRepository() {
        return new JdbcAccountRepository(dataSource);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return new JdbcRestaurantRepository(dataSource);
    }

    @Bean
    public RewardRepository rewardRepository() {
        return new JdbcRewardRepository(dataSource);
    }

}
