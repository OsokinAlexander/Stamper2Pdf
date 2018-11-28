package ru.osokin.stamp;

/** Stamp data. 2 cells in stamp table: key + value.
 * @author Osokin Alexander
 * @since 1.0
 */
public class StampData {
    /** Key (first cell). */
    private String key;
    /** Value (second cell). */
    private String value;
    /** Width of cell. */
    private float columnWidth;

    public StampData(final String key, final String value) {
        this(key, value, 0);
    }

    public StampData(final String key, final String value, final float columnWidth) {
        this.key = key;
        this.value = value;
        this.columnWidth = columnWidth;
    }

    public StampData(final StampData stampData) {
        this.key = stampData.key;
        this.value = stampData.value;
        this.columnWidth = stampData.columnWidth;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public float getColumnWidth() {
        return columnWidth;
    }
}
