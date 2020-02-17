package com.broscorp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A WeekManu.
 */
@Entity
@Table(name = "week_manu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WeekManu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "menu")
    private byte[] menu;

    @Column(name = "menu_content_type")
    private String menuContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getMenu() {
        return menu;
    }

    public WeekManu menu(byte[] menu) {
        this.menu = menu;
        return this;
    }

    public void setMenu(byte[] menu) {
        this.menu = menu;
    }

    public String getMenuContentType() {
        return menuContentType;
    }

    public WeekManu menuContentType(String menuContentType) {
        this.menuContentType = menuContentType;
        return this;
    }

    public void setMenuContentType(String menuContentType) {
        this.menuContentType = menuContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeekManu)) {
            return false;
        }
        return id != null && id.equals(((WeekManu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WeekManu{" +
            "id=" + getId() +
            ", menu='" + getMenu() + "'" +
            ", menuContentType='" + getMenuContentType() + "'" +
            "}";
    }
}
