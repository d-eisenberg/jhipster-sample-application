package com.broscorp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.broscorp.web.rest.TestUtil;

public class WeekOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeekOrder.class);
        WeekOrder weekOrder1 = new WeekOrder();
        weekOrder1.setId(1L);
        WeekOrder weekOrder2 = new WeekOrder();
        weekOrder2.setId(weekOrder1.getId());
        assertThat(weekOrder1).isEqualTo(weekOrder2);
        weekOrder2.setId(2L);
        assertThat(weekOrder1).isNotEqualTo(weekOrder2);
        weekOrder1.setId(null);
        assertThat(weekOrder1).isNotEqualTo(weekOrder2);
    }
}
