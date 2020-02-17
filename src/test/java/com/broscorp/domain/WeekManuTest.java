package com.broscorp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.broscorp.web.rest.TestUtil;

public class WeekManuTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeekManu.class);
        WeekManu weekManu1 = new WeekManu();
        weekManu1.setId(1L);
        WeekManu weekManu2 = new WeekManu();
        weekManu2.setId(weekManu1.getId());
        assertThat(weekManu1).isEqualTo(weekManu2);
        weekManu2.setId(2L);
        assertThat(weekManu1).isNotEqualTo(weekManu2);
        weekManu1.setId(null);
        assertThat(weekManu1).isNotEqualTo(weekManu2);
    }
}
