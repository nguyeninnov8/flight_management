
package data_objects;

import business_object.CrewMember;
import java.util.List;

public interface ICrewMemberDao {
    List<CrewMember> getAll();
    CrewMember getCrewMember(String id);
    boolean addCrewMember(CrewMember member);
    boolean deleteCrewMember(CrewMember member);
}
