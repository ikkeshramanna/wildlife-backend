package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class BirdBehaviourTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BirdBehaviour.class);
        BirdBehaviour birdBehaviour1 = new BirdBehaviour();
        birdBehaviour1.setId(1L);
        BirdBehaviour birdBehaviour2 = new BirdBehaviour();
        birdBehaviour2.setId(birdBehaviour1.getId());
        assertThat(birdBehaviour1).isEqualTo(birdBehaviour2);
        birdBehaviour2.setId(2L);
        assertThat(birdBehaviour1).isNotEqualTo(birdBehaviour2);
        birdBehaviour1.setId(null);
        assertThat(birdBehaviour1).isNotEqualTo(birdBehaviour2);
    }
}
