package com.turaninarcis.group_activity_planner.Users.Models;

import jakarta.validation.constraints.Size;

public record UserLoginDTO(
    @Size(max = 30)
    String username,
    @Size(max=30)
    String password
) {}
