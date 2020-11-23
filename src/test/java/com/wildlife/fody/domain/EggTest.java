package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class EggTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Egg.class);
        Egg egg1 = new Egg();
        egg1.setId(1L);
        Egg egg2 = new Egg();
        egg2.setId(egg1.getId());
        assertThat(egg1).isEqualTo(egg2);
        egg2.setId(2L);
        assertThat(egg1).isNotEqualTo(egg2);
        egg1.setId(null);
        assertThat(egg1).isNotEqualTo(egg2);
    }
}
