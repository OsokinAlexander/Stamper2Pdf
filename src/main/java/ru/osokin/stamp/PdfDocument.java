package ru.osokin.stamp;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfPTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/** Pdf document.
 * @author Osokin Alexander
 * @since 1.0
 */
public class PdfDocument {
    /** Slf4j logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfDocument.class);
    /** List of stamps for first pdf page. */
    private List<Stamp> firstStamps = new ArrayList<>();
    /** List of stamps for pdf pages, not first or last pages if first or last stamps not empty. */
    private List<Stamp> middleStamps = new ArrayList<>();
    /** List of stamps for last pdf page. */
    private List<Stamp> lastStamps = new ArrayList<>();

    /** Add stamp to first page.
     * @param stamp adding stamp
     * @param x x coordinate on the page
     * @param y y coordinate on the page
     * @return PDF document with not drawing stamp
     */
    public PdfDocument addFirstStamp(final Stamp stamp, final float x, final float y) {
        this.firstStamps.add(new Stamp(stamp).setX(x).setY(y));
        return this;
    }

    /** Add stamp to middle pages.
     * @param stamp adding stamp
     * @param x x coordinate on the page
     * @param y y coordinate on the page
     * @return PDF document with not drawing stamp
     */
    public PdfDocument addMiddleStamp(final Stamp stamp, final float x, final float y) {
        this.middleStamps.add(new Stamp(stamp).setX(x).setY(y));
        return this;
    }

    /** Add stamp to last page.
     * @param stamp adding stamp
     * @param x x coordinate on the page
     * @param y y coordinate on the page
     * @return PDF document with not drawing stamp
     */
    public PdfDocument addLastStamp(final Stamp stamp, final float x, final float y) {
        this.lastStamps.add(new Stamp(stamp).setX(x).setY(y));
        return this;
    }

    /** Draw all stamps on all pages of PDF document.
     * @param inputPdfStream input PDF document for drawing
     * @param outputStream output result PDF with stamps
     * @return flag that operation is successful
     */
    public boolean drawAllStamps(final InputStream inputPdfStream, OutputStream outputStream) {
        if (firstStamps == null && middleStamps == null && lastStamps == null) {
            LOGGER.error("All stamps are null");
            return false;
        }
        try {
            PdfReader reader = new PdfReader(inputPdfStream);
            PdfStamper stamper = new PdfStamper(reader, outputStream);
            int lastPageNumber = reader.getNumberOfPages();
            for (int pageNumber = 1; pageNumber <= lastPageNumber; pageNumber++) {
                PageType pageType = getPageType(pageNumber, lastPageNumber);
                PdfContentByte pageCanvas = stamper.getOverContent(pageNumber);
                List<Stamp> currentStamps = getPageStamps(pageType);
                for (Stamp stamp: currentStamps) {
                    drawStamp2Page(pageCanvas, stamp, isPortrait(reader, pageNumber));
                }
            }
            stamper.close();
            reader.close();
        } catch (Exception e) {
            LOGGER.error("Could not drawAllStamps stamp on pdf document", e);
            return false;
        }
        return true;
    }

    /** Draw stamp to PDF page canvas.
     * @param pageCanvas binary content of PDF page
     * @param stamp stamp for drawing on the page
     * @param isPortrait flag that page is portrait or landscape
     * @throws DocumentException itext exception during work with PDF document
     * @throws IOException get instanse of image can throw exception
     */
    private void drawStamp2Page(PdfContentByte pageCanvas, final Stamp stamp, final boolean isPortrait)
            throws DocumentException, IOException {
        if (stamp == null) {
            return;
        }
        float width = stamp.getBorder().getWidth(isPortrait);
        float height = stamp.getBorder().getHeight(isPortrait);
        // Draw border
        Image image = Image.getInstance(stamp.getBorder().getImage(isPortrait));
        pageCanvas.addImage(image, width, 0, 0, height, stamp.getX(), stamp.getY());
        // Draw table
        ColumnText columnText = new ColumnText(pageCanvas);
        PdfPTable table = stamp.isDefaultWidth() ? new PdfPTable(stamp.getColumnCount())
                : new PdfPTable(stamp.getColumnWidths());
        table.setTotalWidth(width);
        for (StampData stampData : stamp.getStampDataList()) {
            addStampCell2Table(stamp, table, stampData);
        }
        columnText.addElement(table);
        Padding padding = stamp.getPadding();
        columnText.setSimpleColumn(stamp.getX() + padding.getLeft(), stamp.getY() + padding.getBottom(),
                stamp.getX() + width - padding.getRight(), stamp.getY() + height - padding.getTop());
        while (ColumnText.hasMoreText(columnText.go())) {
            break;
        }
    }

    /** Add cell to PDF itext table.
     * @param stamp stamp
     * @param table itext PDF table
     * @param stampData stamp data for 2 cells
     */
    private void addStampCell2Table(final Stamp stamp, PdfPTable table, final StampData stampData) {
        PdfPCell key = new PdfPCell(new Phrase(stampData.getKey(), stamp.getFont()));
        PdfPCell value = new PdfPCell(new Phrase(stampData.getValue(), stamp.getFont()));
        if (!stamp.isVisibleTableGrid()) {
            key.setBorder(Rectangle.NO_BORDER);
            value.setBorder(Rectangle.NO_BORDER);
        }
        table.addCell(key);
        table.addCell(value);
    }

    /** Get list of stamps for current type of page.
     * @param pageType type of page
     * @return list of stamps
     */
    private List<Stamp> getPageStamps(final PageType pageType) {
        if (pageType == PageType.FIRST && !firstStamps.isEmpty()) {
            return firstStamps;
        } else if (pageType == PageType.LAST && !lastStamps.isEmpty()) {
            return lastStamps;
        }
        return middleStamps;
    }

    /** Get flag portrait or landscape page is.
     * @param reader PDF reader
     * @param pageNumber number of page
     * @return true is portrait, false is landscape
     */
    private static boolean isPortrait(final PdfReader reader, final int pageNumber) {
        Rectangle rectangle = reader.getPageSize(pageNumber);
        if (rectangle.getWidth() > rectangle.getHeight()) {
            return false;
        }
        return true;
    }

    /** Get type of page.
     * @param currentIndex current page index
     * @param lastIndex last page index
     * @return type of page
     */
    private PageType getPageType(final int currentIndex, final int lastIndex) {
        if (currentIndex == 1) {
            return PageType.FIRST;
        } else if (currentIndex == lastIndex) {
            return PageType.LAST;
        }
        return PageType.MIDDLE;
    }

    /** Enum of page types. */
    private enum PageType {
        FIRST,
        MIDDLE,
        LAST
    }
}
