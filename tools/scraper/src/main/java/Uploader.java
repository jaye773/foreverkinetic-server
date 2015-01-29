import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.Statement;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Uploader {

    public static void writeToDatabase() throws IOException {
        Connection conn = null;
        try {
            String db_url = "db_url";

            // Do something with the Connection
            ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
            ArrayList<Exercise> myObjects = mapper.readValue(new File("exercises.json"), new TypeReference<ArrayList<Exercise>>(){});
            System.out.println(myObjects);

            conn = DriverManager.getConnection("jdbc:" + db_url, "username", "password");

            PreparedStatement stmt = null;
            for (Exercise exercise : myObjects) {
                stmt = conn.prepareStatement("insert into exercises (title, description, positive_votes) VALUES (?, ?, 0)", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, exercise.title);
                stmt.setString(2, exercise.description);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                int exerciseId = rs.getInt(1);
                for(String primaryMusclGroup : exercise.primaryMusclGroup) {
                    int muscleGroupId = getMuscleGroupIdInsertIfNeeded(conn, primaryMusclGroup);
                    insertMuscleGroupMapping(conn, exerciseId, muscleGroupId, true);
                }
                for(String secondaryMusclGroup : exercise.secondaryMuscleGroup) {
                    int muscleGroupId = getMuscleGroupIdInsertIfNeeded(conn, secondaryMusclGroup);
                    insertMuscleGroupMapping(conn, exerciseId, muscleGroupId, false);
                }

                for(String type : exercise.types) {
                    int typeId = getTypeIdInsertIfNeeded(conn, type);
                    insertTypeMapping(conn,exerciseId, typeId);
                }

                insertSteps(conn, exerciseId, exercise.steps);

                for(String equipment : exercise.equipment) {
                    int equipmentId = getEquipmentIdInsertIfNeeded(conn, equipment);
                    insertEquipmentMapping(conn,exerciseId,equipmentId);
                }

                for(String media : exercise.images) {
                    insertMedia(conn, exerciseId,media);
                }

            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static void insertMedia(Connection conn,int exerciseId, String mediaPath) throws SQLException {
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement("insert into media (exercise_id,media_path) VALUES (?,?)");
        stmt.setInt(1, exerciseId);
        stmt.setString(2, mediaPath);
        stmt.executeUpdate();
    }


    public static Map<String, Integer> equipmentIdMap = new HashMap<>();
    public static int getEquipmentIdInsertIfNeeded(Connection conn, String equipment) throws SQLException {
        Integer i = equipmentIdMap.get(equipment);
        if (i != null) {
            return i;
        }
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement("insert into equipment (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, equipment);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int musclGroupId = rs.getInt(1);
        equipmentIdMap.put(equipment, musclGroupId);
        return musclGroupId;
    }

    public static void insertEquipmentMapping(Connection conn, int exerciseId, int equipmentId) throws SQLException {
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement("insert into equipment_exercise (exercise_id,equipment_id) VALUES (?,?)");
        stmt.setInt(1, exerciseId);
        stmt.setInt(2, equipmentId);
        stmt.executeUpdate();
    }



    public static void insertSteps(Connection conn, int exerciseId, ArrayList<String> steps) throws SQLException {
        for(int i=0;i<steps.size();i++) {
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement("insert into steps (exercise_id,step_order,step_text) VALUES (?,?,?)");
            stmt.setInt(1, exerciseId);
            stmt.setInt(2, i+1);
            stmt.setString(3, steps.get(i));
            stmt.executeUpdate();
        }
    }




    public static Map<String, Integer> typeIdMap = new HashMap<>();
    public static int getTypeIdInsertIfNeeded(Connection conn, String type) throws SQLException {
        Integer i = typeIdMap.get(type);
        if (i != null) {
            return i;
        }
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement("insert into exercise_types (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, type);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int musclGroupId = rs.getInt(1);
        muscleGroupIdMap.put(type, musclGroupId);
        return musclGroupId;
    }

    public static void insertTypeMapping(Connection conn, int exerciseId, int typeId) throws SQLException {
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement("insert into exercise_types_exercise (exercise_id,exercise_type_id) VALUES (?,?)");
        stmt.setInt(1, exerciseId);
        stmt.setInt(2, typeId);
        stmt.executeUpdate();
    }




    public static Map<String, Integer> muscleGroupIdMap = new HashMap<>();
    public static int getMuscleGroupIdInsertIfNeeded(Connection conn, String muscleGroup) throws SQLException {
        Integer i = muscleGroupIdMap.get(muscleGroup);
        if (i != null) {
            return i;
        }
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement("insert into muscle_groups (title) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, muscleGroup);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int musclGroupId = rs.getInt(1);
        muscleGroupIdMap.put(muscleGroup, musclGroupId);
        return musclGroupId;
    }

    public static void insertMuscleGroupMapping(Connection conn, int exerciseId, int musclGroupId, boolean primaryMuscleGroup) throws SQLException {
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement("insert into muscle_groups_exercise (muscle_group_id,exercise_id, primary_muscle) VALUES (?,?,?)");
        stmt.setInt(1, exerciseId);
        stmt.setInt(2, musclGroupId);
        stmt.setBoolean(3, primaryMuscleGroup);
        stmt.executeUpdate();
    }
}
