package ru.osokin.stamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/** Stamp for PDF page.
 * @author Osokin Alexander
 * @since 1.0
 */
public class Stamp {
    /** Slf4j logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Stamp.class);
    /** Border of stamp. */
    private Border border;
    /** Padding from border to table of cells. */
    private Padding padding;
    /** List of cells. */
    private List<StampData> stampDataList;
    /** Fort for stamp text. */
    private StampFont font = new StampFont();
    /** Count of columns (always even). */
    private int columnCount;
    /** Columns width. */
    private float[] columnWidths;
    /** Flag of using default width. */
    private boolean isDefaultWidth = true;
    /** Visible technical grid of cells. */
    private boolean isVisibleTableGrid = true;
    /** Coordinate of angle x. */
    private float x = 0;
    /** Coordinate of angle y. */
    private float y = 0;

    public Stamp(final int columnCount) {
        this.columnCount = columnCount;
    }

    public Stamp(final int columnCount, final float keyColumnWidth, final float valueColumnWidth) {
        this(columnCount);
        isDefaultWidth = !(keyColumnWidth > 0 && valueColumnWidth > 0);
        columnWidths = new float[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnWidths[i] = (i % 2 == 0) ? keyColumnWidth : valueColumnWidth;
        }
    }

    public Stamp(final Stamp stamp) {
        this.border = new Border(stamp.border);
        this.padding = new Padding(stamp.padding);
        this.stampDataList = new ArrayList<>(stamp.stampDataList.size());
        for (StampData stampData: stamp.stampDataList) {
            this.stampDataList.add(new StampData(stampData));
        }
        this.font = new StampFont(stamp.font);
        this.columnCount = stamp.columnCount;
        this.columnWidths = stamp.columnWidths;
        this.isDefaultWidth = stamp.isDefaultWidth;
        this.isVisibleTableGrid = stamp.isVisibleTableGrid;
        this.x = stamp.x;
        this.y = stamp.y;
    }

    public Border getBorder() {
        return border;
    }

    public Stamp setBorder(final Border border) {
        this.border = border;
        return this;
    }

    public Padding getPadding() {
        return padding;
    }

    public Stamp setPadding(final Padding padding) {
        this.padding = padding;
        return this;
    }

    public List<StampData> getStampDataList() {
        return stampDataList;
    }

    public Stamp setStampDataList(final List<StampData> stampDataList) {
        this.stampDataList = stampDataList;
        return this;
    }

    public Stamp setFont(final StampFont font) {
        this.font = font;
        return this;
    }

    public Stamp setVisibleTableGrid(final boolean isVisible) {
        this.isVisibleTableGrid = isVisible;
        return this;
    }

    public StampFont getFont() {
        return font;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public float[] getColumnWidths() {
        return columnWidths;
    }

    public boolean isDefaultWidth() {
        return isDefaultWidth;
    }

    public boolean isVisibleTableGrid() {
        return isVisibleTableGrid;
    }

    public float getX() {
        return x;
    }

    public Stamp setX(final float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Stamp setY(final float y) {
        this.y = y;
        return this;
    }
}
