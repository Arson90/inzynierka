package pl.arek.inzynierka.data;

import lombok.Getter;

public enum Roles {

    ADMINISTRATOR("ADMINISTRATOR"),
    EMPLOYEE("EMPLOYEE"),
    CLIENT("CLIENT");

    @Getter
    private String name;

    Roles(String name) {
        this.name = name;
    }
}
