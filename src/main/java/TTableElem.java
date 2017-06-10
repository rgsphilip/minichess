/**
 * Created by rPhilip on 6/9/17.
 */
public class TTableElem {
    int value;
    int depth;
    char flag;
    TTableElem() {}

    public long getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public char getFlag() {
        return flag;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }
}
