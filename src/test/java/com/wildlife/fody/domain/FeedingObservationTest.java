package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class FeedingObservationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedingObservation.class);
        FeedingObservation feedingObservation1 = new FeedingObservation();
        feedingObservation1.setId(1L);
        FeedingObservation feedingObservation2 = new FeedingObservation();
        feedingObservation2.setId(feedingObservation1.getId());
        assertThat(feedingObservation1).isEqualTo(feedingObservation2);
        feedingObservation2.setId(2L);
        assertThat(feedingObservation1).isNotEqualTo(feedingObservation2);
        feedingObservation1.setId(null);
        assertThat(feedingObservation1).isNotEqualTo(feedingObservation2);
    }
}
