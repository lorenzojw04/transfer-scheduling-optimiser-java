package transferscheduler;

/** Python: df[df["new_missionary"] == True] */
public class NewMissionaryGroup extends PriorityGroup {

    public NewMissionaryGroup() {
        super("New Missionaries");
    }

    @Override
    public boolean matches(Missionary missionary) {
        return missionary.isNewMissionary();
    }
}
