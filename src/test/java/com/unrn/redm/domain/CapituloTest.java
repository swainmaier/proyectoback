package com.unrn.redm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.unrn.redm.web.rest.TestUtil;

public class CapituloTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Capitulo.class);
        Capitulo capitulo1 = new Capitulo();
        capitulo1.setId(1L);
        Capitulo capitulo2 = new Capitulo();
        capitulo2.setId(capitulo1.getId());
        assertThat(capitulo1).isEqualTo(capitulo2);
        capitulo2.setId(2L);
        assertThat(capitulo1).isNotEqualTo(capitulo2);
        capitulo1.setId(null);
        assertThat(capitulo1).isNotEqualTo(capitulo2);
    }
}
