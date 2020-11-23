package com.wildlife.fody.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.wildlife.fody.web.rest.TestUtil;

public class TaggedAnimalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaggedAnimal.class);
        TaggedAnimal taggedAnimal1 = new TaggedAnimal();
        taggedAnimal1.setId(1L);
        TaggedAnimal taggedAnimal2 = new TaggedAnimal();
        taggedAnimal2.setId(taggedAnimal1.getId());
        assertThat(taggedAnimal1).isEqualTo(taggedAnimal2);
        taggedAnimal2.setId(2L);
        assertThat(taggedAnimal1).isNotEqualTo(taggedAnimal2);
        taggedAnimal1.setId(null);
        assertThat(taggedAnimal1).isNotEqualTo(taggedAnimal2);
    }
}
