package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class RingingMorphsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RingingMorphs.class);
        RingingMorphs ringingMorphs1 = new RingingMorphs();
        ringingMorphs1.setId(1L);
        RingingMorphs ringingMorphs2 = new RingingMorphs();
        ringingMorphs2.setId(ringingMorphs1.getId());
        assertThat(ringingMorphs1).isEqualTo(ringingMorphs2);
        ringingMorphs2.setId(2L);
        assertThat(ringingMorphs1).isNotEqualTo(ringingMorphs2);
        ringingMorphs1.setId(null);
        assertThat(ringingMorphs1).isNotEqualTo(ringingMorphs2);
    }
}
