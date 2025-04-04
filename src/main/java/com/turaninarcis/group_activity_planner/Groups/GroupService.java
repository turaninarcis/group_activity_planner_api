package com.turaninarcis.group_activity_planner.Groups;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Exceptions.ResourceNotFoundException;
import com.turaninarcis.group_activity_planner.Groups.Models.Group;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupCreateDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMember;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupRoleEnum;
import com.turaninarcis.group_activity_planner.Users.UserService;
import com.turaninarcis.group_activity_planner.Users.Models.User;


@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMembersRepository groupMembersRepository;

    @Autowired 
    private UserService userService;

    public void createGroup(GroupCreateDTO groupCreateDTO) {
        String inviteToken = UUID.randomUUID().toString();
        Group group = new Group(groupCreateDTO.name(), groupCreateDTO.description(),inviteToken);
        Group savedGroup = groupRepository.save(group);

        User user = userService.getLoggedUser();
        GroupMember creator = new GroupMember(user, savedGroup);
        creator.setRole(GroupRoleEnum.CREATOR);

        groupMembersRepository.save(creator);
    }
    public void createGroupMember(String groupToken){
        Group group = groupRepository.findByInviteToken(groupToken);
        if(group == null)
            throw new ResourceNotFoundException(Group.class.getSimpleName());
        User user = userService.getLoggedUser();
        
        GroupMember groupMember = new GroupMember(user, group);
        groupMembersRepository.save(groupMember);
    }
}
