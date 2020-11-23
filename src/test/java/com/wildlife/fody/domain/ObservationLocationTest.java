package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class ObservationLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservationLocation.class);
        ObservationLocation observationLocation1 = new ObservationLocation();
        observationLocation1.setId(1L);
        ObservationLocation observationLocation2 = new ObservationLocation();
        observationLocation2.setId(observationLocation1.getId());
        assertThat(observationLocation1).isEqualTo(observationLocation2);
        observationLocation2.setId(2L);
        assertThat(observationLocation1).isNotEqualTo(observationLocation2);
        observationLocation1.setId(null);
        assertThat(observationLocation1).isNotEqualTo(observationLocation2);
    }
}
