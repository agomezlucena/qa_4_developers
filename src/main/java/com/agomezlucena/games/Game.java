package com.agomezlucena.games;

import java.util.Objects;
import java.util.UUID;

public class Game {
    private final UUID id;
    private final String name;

    public Game(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return Objects.equals(getId(), game.getId()) && Objects.equals(getName(), game.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
