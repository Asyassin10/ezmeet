package com.example.EzMeet.Enum;

public enum RoleType {
    ADMIN("ADMIN"),
    GUIDE("GUIDE"), 
    VOYAGER("VOYAGER");
    
    private final String value;
    
    RoleType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static RoleType fromString(String text) {
        for (RoleType role : RoleType.values()) {
            if (role.value.equalsIgnoreCase(text)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role type with value: " + text);
    }
}