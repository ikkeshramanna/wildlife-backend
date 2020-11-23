package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class SpeciesCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpeciesCategory.class);
        SpeciesCategory speciesCategory1 = new SpeciesCategory();
        speciesCategory1.setId(1L);
        SpeciesCategory speciesCategory2 = new SpeciesCategory();
        speciesCategory2.setId(speciesCategory1.getId());
        assertThat(speciesCategory1).isEqualTo(speciesCategory2);
        speciesCategory2.setId(2L);
        assertThat(speciesCategory1).isNotEqualTo(speciesCategory2);
        speciesCategory1.setId(null);
        assertThat(speciesCategory1).isNotEqualTo(speciesCategory2);
    }
}
