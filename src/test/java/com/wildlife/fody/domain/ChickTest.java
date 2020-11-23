package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class ChickTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chick.class);
        Chick chick1 = new Chick();
        chick1.setId(1L);
        Chick chick2 = new Chick();
        chick2.setId(chick1.getId());
        assertThat(chick1).isEqualTo(chick2);
        chick2.setId(2L);
        assertThat(chick1).isNotEqualTo(chick2);
        chick1.setId(null);
        assertThat(chick1).isNotEqualTo(chick2);
    }
}
