package com.unrn.redm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.unrn.redm.web.rest.TestUtil;

public class SubGeneroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubGenero.class);
        SubGenero subGenero1 = new SubGenero();
        subGenero1.setId(1L);
        SubGenero subGenero2 = new SubGenero();
        subGenero2.setId(subGenero1.getId());
        assertThat(subGenero1).isEqualTo(subGenero2);
        subGenero2.setId(2L);
        assertThat(subGenero1).isNotEqualTo(subGenero2);
        subGenero1.setId(null);
        assertThat(subGenero1).isNotEqualTo(subGenero2);
    }
}
