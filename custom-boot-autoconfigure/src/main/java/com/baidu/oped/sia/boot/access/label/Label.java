package com.baidu.oped.sia.boot.access.label;

import com.baidu.oped.sia.boot.common.ConfigProperties;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Black or white Label configuration.
 *
 * @author mason
 */
public class Label implements ConfigProperties {
    private static final long serialVersionUID = 1969886287494687135L;

    private List<String> blacks = new ArrayList<>();
    private List<String> whites = new ArrayList<>();

    public List<String> getBlacks() {
        return blacks;
    }

    public void setBlacks(List<String> blacks) {
        this.blacks = blacks;
    }

    public List<String> getWhites() {
        return whites;
    }

    public void setWhites(List<String> whites) {
        this.whites = whites;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("blacks", blacks)
                .add("whites", whites)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Label label = (Label) other;
        return Objects.equal(blacks, label.blacks)
               && Objects.equal(whites, label.whites);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(blacks, whites);
    }
}
