package com.gameshub.mapper.game;

public class RawgGameNameMapper {

    public static String mapGameNameToRawgSlugName(final String name) {
        return name.replaceAll(" ", "-");
    }

    public static String mapSlugNameToGameName(final String name) {
        String mappedName = name.replaceAll("-", " ");
        return mappedName.substring(0, 1).toUpperCase() + mappedName.substring(1);
    }
}
