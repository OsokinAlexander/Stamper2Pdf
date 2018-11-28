package ru.osokin.stamp;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/** Font that using for text in stamp.
 * @author Osokin Alexander
 * @since 1.0
 */
public class StampFont {
    /** itext font. */
    private Font font;

    public StampFont() {
        font = new Font();
    }

    public StampFont(final InputStream fontInputStream, final String fontName, final float fontSize)
            throws IOException, DocumentException {
            byte[] bytes = IOUtils.toByteArray(fontInputStream);
            BaseFont baseFont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED,
                    true, bytes, null);
            font = new Font(baseFont, fontSize, Font.NORMAL);
    }

    public StampFont(final InputStream fontInputStream, final String fontName, final float fontSize,
                     final int red, final int green, final int blue) throws IOException, DocumentException {
        this(fontInputStream, fontName, fontSize);
        font.setColor(new BaseColor(red, green, blue));
    }

    public StampFont(final StampFont font) {
        this.font = (font.font == null) ? new Font() : new Font(font.font);
    }

    public Font getFont() {
        return this.font;
    }
}
