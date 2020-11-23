package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class CompetitorsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competitors.class);
        Competitors competitors1 = new Competitors();
        competitors1.setId(1L);
        Competitors competitors2 = new Competitors();
        competitors2.setId(competitors1.getId());
        assertThat(competitors1).isEqualTo(competitors2);
        competitors2.setId(2L);
        assertThat(competitors1).isNotEqualTo(competitors2);
        competitors1.setId(null);
        assertThat(competitors1).isNotEqualTo(competitors2);
    }
}
