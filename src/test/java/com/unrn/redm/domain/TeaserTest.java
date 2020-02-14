package com.unrn.redm.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.unrn.redm.web.rest.TestUtil;

public class TeaserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Teaser.class);
        Teaser teaser1 = new Teaser();
        teaser1.setId(1L);
        Teaser teaser2 = new Teaser();
        teaser2.setId(teaser1.getId());
        assertThat(teaser1).isEqualTo(teaser2);
        teaser2.setId(2L);
        assertThat(teaser1).isNotEqualTo(teaser2);
        teaser1.setId(null);
        assertThat(teaser1).isNotEqualTo(teaser2);
    }
}
