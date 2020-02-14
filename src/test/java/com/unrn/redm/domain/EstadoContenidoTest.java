package com.unrn.redm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.unrn.redm.web.rest.TestUtil;

public class EstadoContenidoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoContenido.class);
        EstadoContenido estadoContenido1 = new EstadoContenido();
        estadoContenido1.setId(1L);
        EstadoContenido estadoContenido2 = new EstadoContenido();
        estadoContenido2.setId(estadoContenido1.getId());
        assertThat(estadoContenido1).isEqualTo(estadoContenido2);
        estadoContenido2.setId(2L);
        assertThat(estadoContenido1).isNotEqualTo(estadoContenido2);
        estadoContenido1.setId(null);
        assertThat(estadoContenido1).isNotEqualTo(estadoContenido2);
    }
}
