package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class CompetitorActionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitorAction.class);
        CompetitorAction competitorAction1 = new CompetitorAction();
        competitorAction1.setId(1L);
        CompetitorAction competitorAction2 = new CompetitorAction();
        competitorAction2.setId(competitorAction1.getId());
        assertThat(competitorAction1).isEqualTo(competitorAction2);
        competitorAction2.setId(2L);
        assertThat(competitorAction1).isNotEqualTo(competitorAction2);
        competitorAction1.setId(null);
        assertThat(competitorAction1).isNotEqualTo(competitorAction2);
    }
}
