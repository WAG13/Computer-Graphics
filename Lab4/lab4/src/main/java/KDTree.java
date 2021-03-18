
public class KDTree {
    KDTree left;
    KDTree right;
    KDTree parent;
    private int value;
    private boolean verticalSplitting;

    public KDTree(){}

    public KDTree(KDTree parent, boolean verticalSplitting) {
        this.parent = parent;
        this.verticalSplitting = verticalSplitting;
    }

    public void setVerticalSplitting(boolean verticalSplitting) {
        this.verticalSplitting = verticalSplitting;
    }

    public boolean isVerticalSplitting() {
        return verticalSplitting;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
