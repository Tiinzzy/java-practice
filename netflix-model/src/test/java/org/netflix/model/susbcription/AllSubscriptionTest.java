package org.netflix.model.susbcription;

import org.junit.jupiter.api.Test;
import org.netflix.model.subscription.EPrice;
import org.netflix.model.subscription.ESubscriptionType;
import org.netflix.model.subscription.SubscriptionDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AllSubscriptionTest {
    @Test
    void testSubscriptionDao() {
        SubscriptionDao newSubscriber = new SubscriptionDao(ESubscriptionType.MONTHLY, EPrice.SINGLE_USER, "2023-10-31", "2023-11-31");
        newSubscriber.saveToTable();
        long newSubscriberId = newSubscriber.getOid();

        assertEquals(newSubscriber.getSubscriptionType(), ESubscriptionType.MONTHLY);
        assertEquals(newSubscriber.getPrice(), EPrice.SINGLE_USER);
        assertEquals(newSubscriber.getSubscriptionDate(), "2023-10-31");
        assertEquals(newSubscriber.getExpiryDate(), "2023-11-31");
        assertEquals(newSubscriber.getOid(), newSubscriberId);

//        List<SubscriptionDao> allSubscribers = SubscriptionDao.loadAll();
//        assertFalse(allSubscribers.isEmpty());
    }

    @Test
    void testUpdateSubscriptionDao() {
        SubscriptionDao newSubscriber = new SubscriptionDao(ESubscriptionType.ANNUALLY, EPrice.MULTI_USER, "2023-10-31", "2024-10-31");
        newSubscriber.saveToTable();

        long newSubscriberId = newSubscriber.getOid();
        SubscriptionDao updateSubs = new SubscriptionDao(newSubscriberId);
        updateSubs.setSubscriptionType(ESubscriptionType.ANNUALLY);
        updateSubs.setPrice(EPrice.MULTI_4K);
        updateSubs.setSubscriptionDate("2023-11-01");
        updateSubs.setExpiryDate("2024-11-01");
        updateSubs.saveToTable();

        assertEquals(updateSubs.getSubscriptionType(), ESubscriptionType.ANNUALLY);
        assertEquals(updateSubs.getPrice(), EPrice.MULTI_4K);
        assertEquals(updateSubs.getSubscriptionDate(), "2023-11-01");
        assertEquals(updateSubs.getExpiryDate(), "2024-11-01");
        assertEquals(updateSubs.getOid(), newSubscriberId);
    }

    @Test
    void testDeleteSubscriptionDao() {
        SubscriptionDao newSubscriber = new SubscriptionDao(ESubscriptionType.MONTHLY, EPrice.MULTI_USER, "2023-10-31", "2023-11-31");
        newSubscriber.saveToTable();

        long subscriberId = newSubscriber.getOid();

        newSubscriber.deleteSubscription(subscriberId);
        assertEquals(newSubscriber.getOid(), subscriberId);
    }
}
