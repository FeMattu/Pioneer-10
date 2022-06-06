package com.pioneer10.data;

import com.pioneer10.model.Planet;

public record LevelData(
        String name,
        String levelPath,
        String background,
        int gravity,
        String music
) {

}
