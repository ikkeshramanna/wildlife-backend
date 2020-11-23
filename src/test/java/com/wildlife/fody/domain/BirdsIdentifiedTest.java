package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class BirdsIdentifiedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BirdsIdentified.class);
        BirdsIdentified birdsIdentified1 = new BirdsIdentified();
        birdsIdentified1.setId(1L);
        BirdsIdentified birdsIdentified2 = new BirdsIdentified();
        birdsIdentified2.setId(birdsIdentified1.getId());
        assertThat(birdsIdentified1).isEqualTo(birdsIdentified2);
        birdsIdentified2.setId(2L);
        assertThat(birdsIdentified1).isNotEqualTo(birdsIdentified2);
        birdsIdentified1.setId(null);
        assertThat(birdsIdentified1).isNotEqualTo(birdsIdentified2);
    }
}
