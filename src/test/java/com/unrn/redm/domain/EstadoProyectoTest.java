package com.unrn.redm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.unrn.redm.web.rest.TestUtil;

public class EstadoProyectoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoProyecto.class);
        EstadoProyecto estadoProyecto1 = new EstadoProyecto();
        estadoProyecto1.setId(1L);
        EstadoProyecto estadoProyecto2 = new EstadoProyecto();
        estadoProyecto2.setId(estadoProyecto1.getId());
        assertThat(estadoProyecto1).isEqualTo(estadoProyecto2);
        estadoProyecto2.setId(2L);
        assertThat(estadoProyecto1).isNotEqualTo(estadoProyecto2);
        estadoProyecto1.setId(null);
        assertThat(estadoProyecto1).isNotEqualTo(estadoProyecto2);
    }
}
