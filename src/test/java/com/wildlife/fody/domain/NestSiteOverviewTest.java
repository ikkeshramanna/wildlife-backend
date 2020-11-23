package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class NestSiteOverviewTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NestSiteOverview.class);
        NestSiteOverview nestSiteOverview1 = new NestSiteOverview();
        nestSiteOverview1.setId(1L);
        NestSiteOverview nestSiteOverview2 = new NestSiteOverview();
        nestSiteOverview2.setId(nestSiteOverview1.getId());
        assertThat(nestSiteOverview1).isEqualTo(nestSiteOverview2);
        nestSiteOverview2.setId(2L);
        assertThat(nestSiteOverview1).isNotEqualTo(nestSiteOverview2);
        nestSiteOverview1.setId(null);
        assertThat(nestSiteOverview1).isNotEqualTo(nestSiteOverview2);
    }
}
