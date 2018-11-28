package ru.osokin.stamp;

/** Padding in stamp border.
 * @author Osokin Alexander
 * @since 1.0
 */
public class Padding {
    /** Top padding. */
    private float top;
    /** Left padding. */
    private float left;
    /** Right padding. */
    private float right;
    /** Bottom padding. */
    private float bottom;

    public Padding(final float top, final float left, final float right, final float bottom) {
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
    }

    public Padding(final Padding padding) {
        this.top = padding.top;
        this.left = padding.left;
        this.right = padding.right;
        this.bottom = padding.bottom;
    }

    public float getTop() {
        return top;
    }

    public Padding setTop(final float top) {
        this.top = top;
        return this;
    }

    public float getLeft() {
        return left;
    }

    public Padding setLeft(final float left) {
        this.left = left;
        return this;
    }

    public float getRight() {
        return right;
    }

    public Padding setRight(final float right) {
        this.right = right;
        return this;
    }

    public float getBottom() {
        return bottom;
    }

    public Padding setBottom(final float bottom) {
        this.bottom = bottom;
        return this;
    }
}
