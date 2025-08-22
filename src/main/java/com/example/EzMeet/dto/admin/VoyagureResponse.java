package com.example.EzMeet.dto.admin;

import com.example.EzMeet.Enum.RoleType;

public record VoyagureResponse(
        String name,
        String firstName,
        String email,
        RoleType role
) {}
