package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class CompetitorImpactOnMkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitorImpactOnMk.class);
        CompetitorImpactOnMk competitorImpactOnMk1 = new CompetitorImpactOnMk();
        competitorImpactOnMk1.setId(1L);
        CompetitorImpactOnMk competitorImpactOnMk2 = new CompetitorImpactOnMk();
        competitorImpactOnMk2.setId(competitorImpactOnMk1.getId());
        assertThat(competitorImpactOnMk1).isEqualTo(competitorImpactOnMk2);
        competitorImpactOnMk2.setId(2L);
        assertThat(competitorImpactOnMk1).isNotEqualTo(competitorImpactOnMk2);
        competitorImpactOnMk1.setId(null);
        assertThat(competitorImpactOnMk1).isNotEqualTo(competitorImpactOnMk2);
    }
}
