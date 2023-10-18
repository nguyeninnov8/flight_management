
package data_objects;

import business_object.CrewMember;
import java.util.List;

public interface ICrewMemberDao {
    List<CrewMember> getAll();
    List<CrewMember> getAvailableMembers();
    CrewMember getCrewMember(String id);
    CrewMember getAvailabeMember(String id);
    boolean addCrewMember(CrewMember member);
    boolean deleteCrewMember(CrewMember member);
}
