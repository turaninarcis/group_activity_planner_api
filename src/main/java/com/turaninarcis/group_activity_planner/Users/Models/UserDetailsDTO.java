package com.turaninarcis.group_activity_planner.Users.Models;

import java.time.LocalDateTime;

public record UserDetailsDTO(
    String username,
    String email,
    Boolean isVerified,
    Boolean isDeleted,
    LocalDateTime dateOfCreation,
    LocalDateTime dateOfLastModification
) {}
