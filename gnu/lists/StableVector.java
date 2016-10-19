package gnu.lists;

public class StableVector extends GapVector {
    static final int END_POSITION = 1;
    protected static final int FREE_POSITION = -2;
    static final int START_POSITION = 0;
    protected int free;
    protected int[] positions;

    protected void chainFreelist() {
        this.free = -1;
        int i = this.positions.length;
        while (true) {
            i--;
            if (i <= END_POSITION) {
                return;
            }
            if (this.positions[i] == FREE_POSITION) {
                this.positions[i] = this.free;
                this.free = i;
            }
        }
    }

    protected void unchainFreelist() {
        int i = this.free;
        while (i >= 0) {
            int next = this.positions[i];
            this.positions[i] = FREE_POSITION;
            i = next;
        }
        this.free = FREE_POSITION;
    }

    public int startPos() {
        return 0;
    }

    public int endPos() {
        return END_POSITION;
    }

    public StableVector(SimpleVector base) {
        super(base);
        this.positions = new int[16];
        this.positions[0] = 0;
        this.positions[END_POSITION] = (base.getBufferLength() << END_POSITION) | END_POSITION;
        this.free = -1;
        int i = this.positions.length;
        while (true) {
            i--;
            if (i > END_POSITION) {
                this.positions[i] = this.free;
                this.free = i;
            } else {
                return;
            }
        }
    }

    protected StableVector() {
    }

    protected int allocPositionIndex() {
        if (this.free == FREE_POSITION) {
            chainFreelist();
        }
        if (this.free < 0) {
            int oldLength = this.positions.length;
            int[] tmp = new int[(oldLength * 2)];
            System.arraycopy(this.positions, 0, tmp, 0, oldLength);
            int i = oldLength * 2;
            while (true) {
                i--;
                if (i < oldLength) {
                    break;
                }
                tmp[i] = this.free;
                this.free = i;
            }
            this.positions = tmp;
        }
        int pos = this.free;
        this.free = this.positions[this.free];
        return pos;
    }

    public int createPos(int index, boolean isAfter) {
        int i = END_POSITION;
        if (index == 0 && !isAfter) {
            return 0;
        }
        if (isAfter && index == size()) {
            return END_POSITION;
        }
        if (index > this.gapStart || (index == this.gapStart && isAfter)) {
            index += this.gapEnd - this.gapStart;
        }
        int ipos = allocPositionIndex();
        int[] iArr = this.positions;
        int i2 = index << END_POSITION;
        if (!isAfter) {
            i = 0;
        }
        iArr[ipos] = i | i2;
        return ipos;
    }

    protected boolean isAfterPos(int ipos) {
        return (this.positions[ipos] & END_POSITION) != 0;
    }

    public boolean hasNext(int ipos) {
        int index = this.positions[ipos] >>> END_POSITION;
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        return index < this.base.getBufferLength();
    }

    public int nextPos(int ipos) {
        int ppos = this.positions[ipos];
        int index = ppos >>> END_POSITION;
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if (index >= this.base.getBufferLength()) {
            releasePos(ipos);
            return 0;
        }
        if (ipos == 0) {
            ipos = createPos(0, true);
        }
        this.positions[ipos] = ppos | END_POSITION;
        return ipos;
    }

    public int nextIndex(int ipos) {
        int index = this.positions[ipos] >>> END_POSITION;
        if (index > this.gapStart) {
            return index - (this.gapEnd - this.gapStart);
        }
        return index;
    }

    public void releasePos(int ipos) {
        if (ipos >= 2) {
            if (this.free == FREE_POSITION) {
                chainFreelist();
            }
            this.positions[ipos] = this.free;
            this.free = ipos;
        }
    }

    public int copyPos(int ipos) {
        if (ipos <= END_POSITION) {
            return ipos;
        }
        int i = allocPositionIndex();
        this.positions[i] = this.positions[ipos];
        return i;
    }

    public void fillPosRange(int fromPos, int toPos, Object value) {
        fillPosRange(this.positions[fromPos], this.positions[toPos], value);
    }

    protected void shiftGap(int newGapStart) {
        int low;
        int high;
        int adjust;
        int oldGapStart = this.gapStart;
        int delta = newGapStart - oldGapStart;
        if (delta > 0) {
            low = this.gapEnd;
            high = low + delta;
            adjust = (oldGapStart - low) << END_POSITION;
            low <<= END_POSITION;
            high = (high << END_POSITION) - 1;
        } else if (newGapStart != oldGapStart) {
            low = (newGapStart << END_POSITION) + END_POSITION;
            high = oldGapStart << END_POSITION;
            adjust = (this.gapEnd - oldGapStart) << END_POSITION;
        } else {
            return;
        }
        super.shiftGap(newGapStart);
        adjustPositions(low, high, adjust);
    }

    protected void gapReserve(int where, int needed) {
        int oldGapEnd = this.gapEnd;
        int oldGapStart = this.gapStart;
        if (needed > oldGapEnd - oldGapStart) {
            int oldLength = this.base.size;
            super.gapReserve(where, needed);
            int newLength = this.base.size;
            if (where == oldGapStart) {
                adjustPositions(oldGapEnd << END_POSITION, (newLength << END_POSITION) | END_POSITION, (newLength - oldLength) << END_POSITION);
                return;
            }
            adjustPositions(oldGapEnd << END_POSITION, (oldLength << END_POSITION) | END_POSITION, (oldGapStart - oldGapEnd) << END_POSITION);
            adjustPositions(this.gapStart << END_POSITION, (newLength << END_POSITION) | END_POSITION, (this.gapEnd - this.gapStart) << END_POSITION);
        } else if (where != this.gapStart) {
            shiftGap(where);
        }
    }

    protected void adjustPositions(int low, int high, int delta) {
        if (this.free >= -1) {
            unchainFreelist();
        }
        low ^= Integer.MIN_VALUE;
        high ^= Integer.MIN_VALUE;
        int i = this.positions.length;
        while (true) {
            i--;
            if (i > 0) {
                int pos = this.positions[i];
                if (pos != FREE_POSITION) {
                    int index = pos ^ Integer.MIN_VALUE;
                    if (index >= low && index <= high) {
                        this.positions[i] = pos + delta;
                    }
                }
            } else {
                return;
            }
        }
    }

    protected int addPos(int ipos, Object value) {
        int ppos = this.positions[ipos];
        int index = ppos >>> END_POSITION;
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if ((ppos & END_POSITION) == 0) {
            if (ipos == 0) {
                ipos = createPos(0, true);
            } else {
                this.positions[ipos] = ppos | END_POSITION;
            }
        }
        add(index, value);
        return ipos;
    }

    protected void removePosRange(int ipos0, int ipos1) {
        super.removePosRange(this.positions[ipos0], this.positions[ipos1]);
        int low = this.gapStart;
        int high = this.gapEnd;
        if (this.free >= -1) {
            unchainFreelist();
        }
        int i = this.positions.length;
        while (true) {
            i--;
            if (i > 0) {
                int pos = this.positions[i];
                if (pos != FREE_POSITION) {
                    int index = pos >> END_POSITION;
                    if ((pos & END_POSITION) != 0) {
                        if (index >= low && index < high) {
                            this.positions[i] = (this.gapEnd << END_POSITION) | END_POSITION;
                        }
                    } else if (index > low && index <= high) {
                        this.positions[i] = this.gapStart << END_POSITION;
                    }
                }
            } else {
                return;
            }
        }
    }

    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        super.consumePosRange(this.positions[iposStart], this.positions[iposEnd], out);
    }
}
