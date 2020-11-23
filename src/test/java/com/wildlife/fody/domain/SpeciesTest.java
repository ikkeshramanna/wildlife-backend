package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class SpeciesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Species.class);
        Species species1 = new Species();
        species1.setId(1L);
        Species species2 = new Species();
        species2.setId(species1.getId());
        assertThat(species1).isEqualTo(species2);
        species2.setId(2L);
        assertThat(species1).isNotEqualTo(species2);
        species1.setId(null);
        assertThat(species1).isNotEqualTo(species2);
    }
}
