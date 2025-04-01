package com.turaninarcis.group_activity_planner.Groups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turaninarcis.group_activity_planner.Groups.Models.Group;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupCreateDTO;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupMember;
import com.turaninarcis.group_activity_planner.Groups.Models.GroupRoleEnum;
import com.turaninarcis.group_activity_planner.Users.UserService;
import com.turaninarcis.group_activity_planner.Users.Models.User;


@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupMembersRepository groupMembersRepository;

    @Autowired 
    UserService userService;

    public void createGroup(GroupCreateDTO groupCreateDTO) {
        
        Group group = new Group(groupCreateDTO.name(), groupCreateDTO.description());
        Group savedGroup = groupRepository.save(group);

        User user = userService.getUser();
        GroupMember creator = new GroupMember(user, savedGroup, GroupRoleEnum.CREATOR);

        groupMembersRepository.save(creator);
    }
}
