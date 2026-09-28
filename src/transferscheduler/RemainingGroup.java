package transferscheduler;

/** Python: df[(df["new_missionary"] == False) & (df["going_home"] == False)] */
public class RemainingGroup extends PriorityGroup {

    public RemainingGroup() {
        super("Remaining Missionaries");
    }

    @Override
    public boolean matches(Missionary missionary) {
        return !missionary.isNewMissionary() && !missionary.isGoingHome();
    }
}
