package transferscheduler;

/** Python: df[df["going_home"] == True] */
public class GoingHomeGroup extends PriorityGroup {

    public GoingHomeGroup() {
        super("Going Home Missionaries");
    }

    @Override
    public boolean matches(Missionary missionary) {
        return missionary.isGoingHome();
    }
}
