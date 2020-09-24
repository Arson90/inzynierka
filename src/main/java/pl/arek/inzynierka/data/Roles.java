package pl.arek.inzynierka.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Roles {

    @JsonProperty("ROLE_ADMINISTRATOR")
    ROLE_ADMINISTRATOR,
    @JsonProperty("ROLE_EMPLOYEE")
    ROLE_EMPLOYEE,
    @JsonProperty("ROLE_CLIENT")
    ROLE_CLIENT;

//    @Getter
//    private String name;
//
//    Roles(String name) {
//        this.name = name;
//    }

//    @JsonCreator
//    public static Roles forValue(String vaule){
//        for (Roles role: Roles.values()) {
//            if(role.name.equals(vaule)){
//                return role;
//            }
//        }
//        throw new RuntimeException("No such role");
//    }
//
//    @JsonValue
//    public String toValue(){
//        return name;
//    }
}
