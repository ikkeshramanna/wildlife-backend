package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class EggsAndChickTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EggsAndChick.class);
        EggsAndChick eggsAndChick1 = new EggsAndChick();
        eggsAndChick1.setId(1L);
        EggsAndChick eggsAndChick2 = new EggsAndChick();
        eggsAndChick2.setId(eggsAndChick1.getId());
        assertThat(eggsAndChick1).isEqualTo(eggsAndChick2);
        eggsAndChick2.setId(2L);
        assertThat(eggsAndChick1).isNotEqualTo(eggsAndChick2);
        eggsAndChick1.setId(null);
        assertThat(eggsAndChick1).isNotEqualTo(eggsAndChick2);
    }
}
