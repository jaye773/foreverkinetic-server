import java.util.ArrayList;

public class Exercise {
    public String title;
    public String description;
    public ArrayList<String> types;
    public ArrayList<String> steps;
    public ArrayList<String> primaryMusclGroup;
    public ArrayList<String> secondaryMuscleGroup;
    public ArrayList<String> equipment;

    public Exercise() {
        types = new ArrayList<>();
        steps = new ArrayList<>();
        primaryMusclGroup = new ArrayList<>();
        secondaryMuscleGroup = new ArrayList<>();
        equipment = new ArrayList<>();
    }

    public void setTitle(String _title) {
        title = _title;
    }

    public void setDescription(String _description) {
        description = _description;
    }

    public void addType(String _type) {
        types.add(_type);
    }

    public void addEquipment(String _equipment) {
        equipment.add(_equipment);
    }

    public void addStep(String step) {
        steps.add(step);
    }

    public void addPrimaryMuscleGroup(String muscleGroup) {
        primaryMusclGroup.add(muscleGroup);
    }

    public void addSecondaryMuscleGroup(String muscleGroup) {
        secondaryMuscleGroup.add(muscleGroup);
    }

    public void print() {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Step: " + steps);
        System.out.println("Primary Muscle Group: " + primaryMusclGroup);
        System.out.println("Secondary Muscle Group: " + secondaryMuscleGroup );
        System.out.println("Type: " + types);
        System.out.println("Equipment: " + equipment);
        System.out.println();
    }
}